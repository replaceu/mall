package com.gulimall.order.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.to.mq.SecondKillOrderTo;
import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.vo.OrderConfirmVo;
import com.gulimall.order.vo.OrderSubmitResponseVo;
import com.gulimall.order.vo.OrderSubmitVo;
import com.gulimall.service.utils.PageUtils;

/**
 * 订单
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:01:26
 */
public interface OrderService extends IService<OrderEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 返回订单确认页需要的数据
	 * @return
	 */
	OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

	/**
	 * 下单
	 * @param orderSubmit
	 * @return
	 */
	OrderSubmitResponseVo submitOrder(OrderSubmitVo orderSubmit);

	OrderEntity getOrderByOrderSn(String orderSn);

	void closeOrder(OrderEntity orderEntity);

	R getOrderPayByOrderSn(String orderSn);

	PageUtils queryPageWithItems(Map<String, Object> params);

	void updateOrderStatus(String outTradeNo, Integer code);

	void creatSecondKillOrder(SecondKillOrderTo order);

	void processOrder(Map<String, Object> map);

	void processRefund(Map<String, Object> map);
}
