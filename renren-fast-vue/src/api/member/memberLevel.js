import request from "@/utils/httpRequest"

const BASE_URL = "/member/memberlevel"
/**
 * 获取会员列表
 * @param {*} params 分页搜索参数
 */
export function getMemberLevelList(params){
  return request({
      url:  request.adornUrl( `${BASE_URL}/list` ) ,
      method: "get" ,
      params : request.adornParams(params , false )
  })
}
/**
 * 获取会员等级详情
 * @param {Number} levelId 等级id
 */
export function getMemberLeveLInfo(levelId){
  return request({
    url:  request.adornUrl( `${BASE_URL}/info/${levelId}` ) ,
    method: "get"
  })
}