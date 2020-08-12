import request from "@/utils/httpRequest"

const BASE_URL = "/product/attrgroup"
/**
 * 17、获取分类下所有分组&关联属性
 * @param {Number} categoryId  三级分类id
 */
export function AttrGroupCategoryWithAttrApi(categoryId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/${categoryId}/withattr`),
    method: "get",
    params: request.adornParams({}, true)
  })
}
