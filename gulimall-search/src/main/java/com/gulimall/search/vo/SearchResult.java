package com.gulimall.search.vo;

import java.util.List;

import com.gulimall.common.to.es.SkuEsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
*@author aqiang92020-08-19
*/
@Getter
@Setter
@ToString
public class SearchResult {
	//查询到的所有商品
	private List<SkuEsModel>	products;
	/**
	*分页信息
	*/
	private Integer				pageNum;	//当前页码
	private Long				total;		//总记录数
	private Integer				totalPages;	//总页码数
	private List<BrandVo>		brands;		//当前查询结果所涉及到的品牌
	private List<CategoryVo>	categories;	//所有查询结果涉及到的分类
	private List<AttrVo>		attrs;		//所有查询结果涉及到的属性

	public List<SkuEsModel> getProducts() {
		return products;
	}

	public void setProducts(List<SkuEsModel> products) {
		this.products = products;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public List<BrandVo> getBrands() {
		return brands;
	}

	public void setBrands(List<BrandVo> brands) {
		this.brands = brands;
	}

	public List<CategoryVo> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryVo> categories) {
		this.categories = categories;
	}

	public List<AttrVo> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<AttrVo> attrs) {
		this.attrs = attrs;
	}
}
