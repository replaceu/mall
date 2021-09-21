package com.gulimall.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.search.config.MyElasticsearchConfig;
import com.gulimall.search.constant.EsConstant;
import com.gulimall.search.service.MallSearchService;
import com.gulimall.search.vo.*;

/**
 * @authoraqiang92020-08-19
 */
@Service
public class MallSearchServiceImpl implements MallSearchService {
	@Autowired
	RestHighLevelClient restHighLevelClient;

	@Override
	public SearchResult search(SearchParam searchParam) {
		SearchResult searchResult = null;
		//1 动态构建DSL语句
		SearchRequest searchRequest = buildESRequest(searchParam);

		try {
			//执行
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, MyElasticsearchConfig.COMMON_OPTIONS);
			//2 分析响应数据,封装数据
			searchResult = buildESResult(searchResponse, searchParam);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchResult;
	}

	/**
	 * 依据传递过来的页面参数去ES中检索商品,使用ES进行检索分为三步
	 * <p>
	 * 1.动态构建检索请求DSL语句
	 * 2.使用ES客户端执行检索请求
	 * 3.封装检索出来的结果
	 *
	 * @param param 所有检索参数
	 * @return SearchResult 检索结果
	 */
	@Override
	public SearchResult searchProducts(SearchParam param) {
		SearchResult result = new SearchResult();
		//1.动态构建检索请求DSL语句
		SearchRequest searchRequest = buildESRequest(param);
		//2.使用ES客户端执行检索请求
		try {
			SearchResponse response = restHighLevelClient.search(searchRequest, MyElasticsearchConfig.COMMON_OPTIONS);

			//3.封装检索出来的结果
			result = buildESResult(response, param);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 构建检索结果,在做的时候可以Debug，打断点的方式得到SearchResponse对象
	 * @param response
	 * @return
	 */
	private SearchResult buildESResult(SearchResponse response, SearchParam param) {
		SearchResult result = new SearchResult();

		//1.返回所有查询到的商品
		SearchHits hits = response.getHits();
		List<SkuEsModel> esModels = new ArrayList<>();
		if (hits.getHits().length > 0) {
			for (SearchHit hit : hits.getHits()) {
				String sourceAsString = hit.getSourceAsString();
				SkuEsModel skuEsModel = new SkuEsModel();
				SkuEsModel esModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
				esModels.add(esModel);
			}
		}
		result.setProducts(esModels);
		//2.当前商品所涉及到的属性的信息
		ParsedNested attrAgg = response.getAggregations().get("attr_agg");
		ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
		List<AttrVo> attrList = new ArrayList<>();
		List<? extends Terms.Bucket> buckets = attrIdAgg.getBuckets();
		for (Terms.Bucket bucket : buckets) {
			AttrVo attr = new AttrVo();
			long attrId = bucket.getKeyAsNumber().longValue();
			String attrName = ((ParsedStringTerms) bucket.getAggregations().get("attr_name_agg")).getBuckets().get(0).getKeyAsString();
			List<String> attrValue = ((ParsedStringTerms) bucket.getAggregations().get("attr_value_agg")).getBuckets().stream().map(item -> {
				String keyAsString = item.getKeyAsString();
				return keyAsString;
			}).collect(Collectors.toList());

			attr.setAttrId(attrId);
			attr.setAttrName(attrName);
			attr.setAttrValue(attrValue);
			attrList.add(attr);
		}
		result.setAttrs(attrList);

		//3.当前商品涉及到的品牌的信息
		ParsedStringTerms brandAgg = response.getAggregations().get("brand_agg");
		List<BrandVo> brandList = new ArrayList<>();
		for (Terms.Bucket bucket : brandAgg.getBuckets()) {
			BrandVo brand = new BrandVo();
			brand.setBrandId(Long.parseLong(bucket.getKeyAsString()));
			ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brand_img_agg");
			brand.setBrandImg(brandImgAgg.getBuckets().get(0).toString());
			ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brand_name_agg");
			brand.setBrandName(brandNameAgg.getBuckets().get(0).toString());
			brandList.add(brand);
		}
		result.setBrands(brandList);

		//4.当前商品涉及到的分类的信息
		ParsedLongTerms catalogAgg = response.getAggregations().get("catalog_agg");
		List<CategoryVo> categoryList = new ArrayList<>();
		for (Terms.Bucket bucket : catalogAgg.getBuckets()) {
			CategoryVo category = new CategoryVo();
			//得到分类Id
			category.setCategoryId(Long.parseLong(bucket.getKeyAsString()));
			//得到分类名
			ParsedStringTerms catalogNameAgg = bucket.getAggregations().get("catalog_name_agg");
			category.setCategoryName(catalogNameAgg.getBuckets().get(0).toString());
			categoryList.add(category);
		}
		result.setCategories(categoryList);

		//5.分页信息
		long total = hits.getTotalHits().value;
		result.setTotal(total);
		long totalPages = total % (EsConstant.PRODUCT_PAGE_SIZE) == 0 ? total / (EsConstant.PRODUCT_PAGE_SIZE) : total / (EsConstant.PRODUCT_PAGE_SIZE) + 1;
		result.setTotalPages((int) totalPages);
		result.setPageNum(param.getPageNum());

		return result;
	}

	/**
	 * 构建检所请求
	 * 模糊匹配/过滤（属性/分类/品牌/价格区间/库存）排序,分页,高亮,聚合分析
	 *
	 * @return
	 */
	private SearchRequest buildESRequest(SearchParam param) {
		//帮助我们构建DSL语句
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		//模糊匹配/过滤（属性/分类/品牌/价格区间/库存）
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		//1.1 must模糊匹配

		if (!StringUtils.isEmpty(param.getKeyword())) {
			boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
		}
		//1.2 filter过滤
		if (!StringUtils.isEmpty(param.getCategory3Id())) {
			//按照三级分类进行过滤
			boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCategory3Id()));
		}
		if (!StringUtils.isEmpty(param.getBrandId()) && param.getBrandId().size() > 0) {
			//按照品牌Id列表查询的，所以使用terms
			boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
		}
		if (!StringUtils.isEmpty(param.getHasStock())) {
			//按照是否有库存进行过滤
			boolQuery.filter(QueryBuilders.termsQuery("hasStock", param.getHasStock() == 1));
		}
		if (!StringUtils.isEmpty(param.getAttrs()) && param.getAttrs().size() > 0) {

			for (String attrStr : param.getAttrs()) {
				//按照属性进行过滤,这是嵌入式
				BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
				//attrs=1_5寸:8寸&attrs=2_16G:8G
				String[] split = attrStr.split("_");
				String attrId = split[0];//检索属性的Id
				String[] attrValue = split[1].split(":");//这个属性的检索用的值
				nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrId", attrId));
				nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValue));

				//每一个必须都得生成一个nested查询
				NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
				boolQuery.filter(nestedQuery);
			}
		}
		//按照价格区间过滤
		if (StringUtils.isEmpty(param.getSkuPrice())) {
			RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
			String[] split = param.getSkuPrice().split("_");
			if (split.length == 2) {
				rangeQuery.gte(split[0]).lte(split[1]);
			} else if (split.length == 1) {
				if (param.getSkuPrice().startsWith("_")) {
					rangeQuery.lte(split[0]);
				}

				if (param.getSkuPrice().endsWith("_")) {
					rangeQuery.gte(split[0]);
				}
			}
			boolQuery.filter(rangeQuery);
		}

		sourceBuilder.query(boolQuery);
		//排序
		if (!StringUtils.isEmpty(param.getSort())) {
			//sort = hotScore_desc/asc
			String sort = param.getSort();
			String[] split = sort.split("_");
			sourceBuilder.sort(split[0], split[1].equals("asc") ? SortOrder.ASC : SortOrder.DESC);
		}
		//分页
		sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGE_SIZE);
		sourceBuilder.size(EsConstant.PRODUCT_PAGE_SIZE);
		//高亮
		if (StringUtils.isEmpty(param.getKeyword())) {
			HighlightBuilder highlightBuilder = new HighlightBuilder();
			highlightBuilder.field("skuTitle");
			highlightBuilder.preTags("<b style='color:red'>");
			highlightBuilder.postTags("</b>");
			sourceBuilder.highlighter(highlightBuilder);
		}
		//聚合分析
		TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg");
		brandAgg.field("brandId").size(50);
		//品牌聚合的子聚合
		brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
		brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
		sourceBuilder.aggregation(brandAgg);
		//分类的子聚合
		TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
		catalogAgg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
		sourceBuilder.aggregation(catalogAgg);

		//属性聚合的嵌入式聚合
		NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
		/**
		 NestedAggregationBuilder subAttrAgg = attrAgg.subAggregation(AggregationBuilders.terms("attr_id_agg").field("attrs.attrId").size(20));
		 subAttrAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
		 subAttrAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(10));
		 */
		TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId").size(20);
		attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
		attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(10));
		attrAgg.subAggregation(attrIdAgg);
		sourceBuilder.aggregation(attrAgg);

		SearchRequest request = new SearchRequest(new String[] { EsConstant.PRODUCT_INDEX }, sourceBuilder);
		return request;
	}

}
