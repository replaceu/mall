package com.gulimall.order.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author aqiang9  2020-09-11
 */
@EnableRabbit
@Configuration
public class MyRabbitmqConfig {

	@Autowired
	RabbitTemplate rabbitTemplate;

	//创建秒杀队列
	@Bean
	public Queue orderSecondKillQueue() {
		return new Queue("order.secondKill.order.queue", true, false, false);
	}

	//创建秒杀队列的绑定关系
	@Bean
	public Binding orderSecondKillBinding() {
		return new Binding("order.secondKill.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.secondKill.order", null);
	}

	//创建购买积分队列
	@Bean
	public Queue orderFinishIntegralQueue() {
		return new Queue("order.finish.integral.queue", true, false, false);
	}

	//创建购买积分队列的绑定关系
	@Bean
	public Binding orderFinishIntegralBinding() {
		return new Binding("order.finish.integral.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.finish.integral", null);
	}

	//创建退款积分队列
	@Bean
	public Queue orderRefundIntegralQueue() {
		return new Queue("order.refund.integral.queue", true, false, false);
	}

	//创建退款积分队列的绑定关系
	@Bean
	public Binding orderRefundIntegralBinding() {
		return new Binding("order.refund.integral.queue", Binding.DestinationType.QUEUE, "order-event-exchange", "order.refund.integral", null);
	}

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

}
