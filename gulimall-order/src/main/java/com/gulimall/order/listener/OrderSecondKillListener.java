package com.gulimall.order.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.common.to.mq.SecondKillOrderTo;
import com.gulimall.order.service.OrderService;
import com.rabbitmq.client.Channel;

//用于监听秒杀信息的队列
@Service
@RabbitListener(queues = "order.secondKill.order.queue")
public class OrderSecondKillListener {
	@Autowired
	private OrderService orderService;

	@RabbitListener
	public void listener(SecondKillOrderTo secondKillOrderTo, Channel channel, Message message) throws IOException {
		try {
			orderService.creatSecondKillOrder(secondKillOrderTo);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

		} catch (Exception e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
