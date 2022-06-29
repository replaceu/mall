package com.gulimall.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.order.entity.OrderEntity;

/**
 * 订单
 * 
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	void updateOrderStatus(@Param("outTradeNo") String outTradeNo, @Param("code") Integer code);
}
