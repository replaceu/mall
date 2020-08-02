import request from "@/utils/httpRequest"

const BASE_URL = "/product/spuinfo"
/**
 * 保存 spu info
 * @param {*} data spu 信息
 */
export function SaveInfo(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}
