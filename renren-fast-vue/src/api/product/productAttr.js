import request from "@/utils/httpRequest"

const BASE_URL = "/product/attr"
/**
 * 获取当前分类可以使用的销售属性
 * @param {Number} categoryId 三级分类id
 * @param {*} params  分页参数
 */
export function AttrSaleListApi(categoryId , params){
  return request({
      url:  request.adornUrl( `${BASE_URL}/sale/list/${categoryId}`) ,
      method: "get" ,
      params : request.adornParams(params , false )
  })
}