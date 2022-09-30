package com.gulimall.ware.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Carter
 * @date 2021-09-16 23:38
 * @description:
 * @version:
 */

@Configuration
public class MyRabbitMQConfig {

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Exchange stockEventExchange() {
		TopicExchange topicExchange = new TopicExchange("stock-event-exchange", true, false);
		return topicExchange;
	}

	@Bean
	public Queue stockReleaseStockQueue() {
		Queue queue = new Queue("stock.release.stock.queue", true, false, false);
		return queue;
	}

	@Bean
	public Queue stockDelayQueue() {
		//这是一个死信队列，需要设置其他属性
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "stock-event-exchange");
		arguments.put("x-dead-letter-routing-key", "stock.release");
		arguments.put("x-message-ttl", 120000);

		Queue queue = new Queue("stock.delay.queue", true, false, false, arguments);
		return queue;
	}

	//3.创建绑定关系
	@Bean
	public Binding stockReleaseBinding() {
		Binding binding = new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.release.#", null);
		return binding;
	}

	@Bean
	public Binding stockLockedBinding() {
		Binding binding = new Binding("stock.delay.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.locked", null);
		return binding;
	}

}
