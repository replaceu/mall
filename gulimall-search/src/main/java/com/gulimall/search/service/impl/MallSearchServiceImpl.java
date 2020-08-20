package com.gulimall.search.service.impl;

import com.gulimall.search.config.MyElasticsearchConfig;
import com.gulimall.search.constant.EsConstant;
import com.gulimall.search.service.MallSearchService;
import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author aqiang9  2020-08-19
 */
@Service
public class MallSearchServiceImpl implements MallSearchService {
    @Autowired
    RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResult searchResult = null;
//        1、动态构建DSL 语句
        SearchRequest searchRequest = buildSearchRequest(searchParam);

        try {
            // 执行
            SearchResponse searchResponse = client.search(searchRequest, MyElasticsearchConfig.COMMON_OPTIONS);
//            2、分析响应数据 、封装数据
            searchResult = buildSearchResult(searchResponse , searchParam);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return searchResult;
    }


    /**
     * 准备检索请求
     * 模糊匹配、过滤（属性、分类、品牌、价格区间、库存）排序、分页、高亮、聚合分析
     *
     * @param searchParam 检索参数
     * @return SearchRequest
     */
    private SearchRequest buildSearchRequest(SearchParam searchParam) {
//        构建DSL 对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        模糊匹配、过滤（属性、分类、品牌、价格区间、库存）
//        bool-query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//              must:
        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));

            // 高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }
//              filter:
//        List<QueryBuilder> filter = boolQuery.filter();
//            2.1 三级分类id
        if (searchParam.getCategory3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("categoryId", searchParam.getCategory3Id()));
        }
//          2.2
        if (searchParam.getBrandId() != null && searchParam.getBrandId().size() > 0) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", searchParam.getBrandId()));
        }
//        2.3 按属性查询
        if (searchParam.getAttrs() != null && searchParam.getAttrs().size() > 0) {
            // attrs=1_5寸:8寸
            for (String attrStr : searchParam.getAttrs()) {
                BoolQueryBuilder nestBoolQuery = QueryBuilders.boolQuery();

                String[] s = attrStr.split("_");
                if (s.length == 2) {
                    String[] attrValues = s[1].split(":");
                    nestBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", s[0])); // 属性id
                    nestBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues)); // 属性值
                }
                // 每一个都 要生成一个嵌入式的查询
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }
//        TODO 默认全部   需要修改 按库存
        boolQuery.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 1));

//        价格区间
        if (!StringUtils.isEmpty(searchParam.getSkuPrice())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");

            String price = searchParam.getSkuPrice();
            String[] s = price.split("_");

            if (s.length == 2) {
                rangeQuery.gte(s[0]);
                rangeQuery.lte(s[1]);
            } else if (s.length == 1) {
                if (price.startsWith("_")) {
                    rangeQuery.lte(s[0]);
                } else {
                    rangeQuery.gte(s[0]);
                }
            }
            boolQuery.filter(rangeQuery);
        }
        searchSourceBuilder.query(boolQuery);
//        排序、分页、高亮、
//        默认第一页
        searchSourceBuilder.from((searchParam.getPageNum() - 1) * EsConstant.PRODUCT_PAGE_SIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGE_SIZE);
//      高亮 关键字的位置写 。。

        // 聚合分析
//        品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg").field("brandId").size(50);
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        searchSourceBuilder.aggregation(brandAgg);
//        分类聚合
        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category_agg").field("categoryId").size(20);
        categoryAgg.subAggregation(AggregationBuilders.terms("category_name_agg").field("categoryName").size(1));
        searchSourceBuilder.aggregation(categoryAgg);
//     属性聚合

        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");

        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(30));

        attrAgg.subAggregation(attrIdAgg);
        searchSourceBuilder.aggregation(attrAgg);

        System.out.println("DSL 语句:");
        System.out.println(searchSourceBuilder.toString());

        return new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
    }

    /**
     * 封装检索 结果
     *
     * @param searchResponse 查询结果
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse searchResponse ,SearchParam searchParam ) {


        return null;
    }


}
