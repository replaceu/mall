package com.gulimall.order.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gulimall.order.entity.OrderEntity;
import com.rabbitmq.client.Channel;

/**
 * @author aqiang9  2020-09-11
 */
@EnableRabbit
@Configuration
public class MyRabbitmqConfig {

	@Autowired
	RabbitTemplate rabbitTemplate;

	//1.创建队列
	@Bean
	public Queue orderDelayQueue() {

		//这是一个死信队列，需要设置其他属性
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "order-event-exchange");
		arguments.put("x-dead-letter-routing-key", "order.release.order");
		arguments.put("x-message-ttl", 60000);

		Queue queue = new Queue("order.delay.queue", true, false, false, arguments);
		return queue;

	}

	@Bean
	public Queue orderReleaseQueue() {
		Queue queue = new Queue("order.release.queue", true, false, false);
		return queue;
	}

	//2.创建交换机
	@Bean
	public Exchange orderEventExchange() {
		TopicExchange topicExchange = new TopicExchange("order-event-exchange", true, false);
		return topicExchange;
	}

	//3.创建绑定关系
	@Bean
	public Binding orderCreateOrderBinding() {
		Binding binding = new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.create.order", null);
		return binding;
	}

	@Bean
	public Binding orderReleaseOrderBinding() {

		Binding binding = new Binding("order.release.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.release.order", null);
		return binding;
	}

	//4.设置监听器
	@RabbitListener
	public void listener(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
		System.out.println("收到过期的消息，准备关闭订单" + orderEntity.getOrderSn());
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	/**
	 * 定制 rabbitTemplate
	 * 1、服务收到消息就回调
	 * 1) spring.rabbitmq.publisher-confirms=true / spring.rabbitmq.publisher-confirm-type=correlated
	 * 2) 设置回调  setConfirmCallback
	 * 2、消息真确抵达队列
	 * 1）spring.rabbitmq：
	 * publisher-returns: true
	 * template:
	 * mandatory: true
	 * 2） 设置回调 setReturnCallback
	 * 3、 消费端确认 （保证消息被消费 ） 可以手动ack
	 */
	@PostConstruct
	public void init() {
		// 只要抵达broker 就会 ack=true
		/**
		 * correlationData:当前消息唯一关联数据 （唯一id） ,
		 * ack： 是否收到 ,
		 * cause ： 失败原因
		 */
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			System.out.println("------------------------------------------");
			System.out.println(correlationData);
			System.out.println(ack);
			System.out.println(cause);
		});

		/**
		 * 只要消息没有投递给指定队列 ， 就触发这个失败回调
		 * message : 投递失败的消息详细信息
		 * replyCode ： 恢复状态码
		 * replyText ： 回复文本内容
		 * exchange ： 发给的那个交换机
		 * routingKey ：  用的那个路由键
		 */
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

			System.out.println(message);
			System.out.println(replyCode);
			System.out.println(replyText);
			System.out.println(exchange);
			System.out.println(routingKey);
		});

		//        rabbitTemplate.containerAckMode(AcknowledgeMode.MANUAL);
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
