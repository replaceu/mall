package com.gulimall.search.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.search.config.MyElasticsearchConfig;
import com.gulimall.search.constant.EsConstant;
import com.gulimall.search.service.ProductSaveService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aqiang9  2020-08-15
 */
@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {

	//操作ES的工具
	@Autowired
	RestHighLevelClient restHighLevelClient;

	@Override
	public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

		//1、建立索引
		BulkRequest bulkRequest = new BulkRequest();
		for (SkuEsModel skuEsModel : skuEsModels) {
			IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
			indexRequest.id(skuEsModel.getSkuId().toString());
			indexRequest.source(JSON.toJSONString(skuEsModel), XContentType.JSON);
			bulkRequest.add(indexRequest);
		}
		//2、批量保存
		BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, MyElasticsearchConfig.COMMON_OPTIONS);
		boolean b = bulk.hasFailures();

		if (b) {
			List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
			return false;
		}
		return true;
	}

	/**
	 * 将要上架的数据保存到ES中
	 * @param skuEsModels
	 * @return
	 * @throws IOException
	 */
	public Boolean productByCarterStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

		//1.给ES中建立索引
		BulkRequest bulkRequest = new BulkRequest();
		for (SkuEsModel skuEsModel : skuEsModels) {
			//构造保存请求
			IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
			indexRequest.id(skuEsModel.getSkuId().toString());
			indexRequest.source(JSON.toJSONString(skuEsModel));
			bulkRequest.add(indexRequest);
		}

		//2.给ES中保存数据(批量保存)
		BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, MyElasticsearchConfig.COMMON_OPTIONS);
		//3.判断是不是保存成功了
		boolean b = bulk.hasFailures();
		List<String> bulkList = Arrays.stream(bulk.getItems()).map(item -> {
			return item.getId();
		}).collect(Collectors.toList());
		return b;
	}
}
