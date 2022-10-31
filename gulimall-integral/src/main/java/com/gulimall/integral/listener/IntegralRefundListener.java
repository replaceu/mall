package com.gulimall.integral.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.integral.service.IntegralFeeGradeService;
import com.gulimall.integral.service.IntegralUserPointService;
import com.gulimall.integral.to.OrderTo;
import com.rabbitmq.client.Channel;

@Service
@RabbitListener(queues = { "order.refund.integral.queue" })
public class IntegralRefundListener {
	@Autowired
	IntegralFeeGradeService		integralFeeGradeService;
	@Autowired
	IntegralUserPointService	integralUserPointService;

	@RabbitListener
	public void handleIntegralRefund(OrderTo orderTo, Message message, Channel channel) throws IOException {
		//收到订单退货的消息
		try {
			integralFeeGradeService.reduceUserFreeGrade(orderTo);
			integralUserPointService.reduceUserPoint(orderTo);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
