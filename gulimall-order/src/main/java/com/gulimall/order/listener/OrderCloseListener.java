package com.gulimall.order.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.service.OrderService;

/**
 * @author Carter
 * @date 2021-09-17 14:21
 * @description:关单的监听器
 * @version:
 */

@Service
@RabbitListener(queues = "order.release.order.queue")
public class OrderCloseListener {
	@Autowired
	private OrderService orderService;

	@RabbitHandler
	public void listener(OrderEntity orderEntity, com.rabbitmq.client.Channel channel, Message message) throws IOException {
		try {
			orderService.closeOrder(orderEntity);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			//修改失败，拒绝消息，使消息重新入队
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}
}
