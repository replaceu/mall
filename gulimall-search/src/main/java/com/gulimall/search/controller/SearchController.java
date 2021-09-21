package com.gulimall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gulimall.search.service.MallSearchService;
import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;

/**
 * @author Carter
 * @date 2021-08-22 20:26
 * @description: 用于商品检索，需要通过检索参数的传入，从而到达检索结果的输出
 * @version:
 */

@Controller
public class SearchController {

	@Autowired
	MallSearchService mallSearchService;

	@GetMapping("/list.html")
	public String searchListPage(SearchParam param) {
		SearchResult result = mallSearchService.searchProducts(param);
		return "list";
	}
}
