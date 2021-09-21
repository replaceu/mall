package com.gulimall.ware.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.common.to.mq.StockLockedTo;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.to.OrderTo;
import com.rabbitmq.client.Channel;

/**
 * @author Carter
 * @date 2021-09-17 10:15
 * @description:库存自动解锁的监听器
 * @version:
 */

@Service
@RabbitListener(queues = { "stock.release.stock.queue" })
public class StockReleaseListener {
	@Autowired
	private WareSkuService wareSkuService;

	@RabbitHandler
	public void handleStockLockedRelease(StockLockedTo stockLockedTo, Message message, Channel channel) throws IOException {
		//收到库存解锁的消息
		try {
			wareSkuService.unLock(stockLockedTo);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}

	@RabbitHandler
	public void handleOrderCloseRelease(OrderTo orderTo, Message message, Channel channel) throws IOException {
		//收到订单关闭的消息
		try {
			wareSkuService.unLockStockForOrder(orderTo);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
