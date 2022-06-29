package com.gulimall.service.impl;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gulimall.service.LoggerService;
import com.gulimall.service.StatisticsKafkaService;

@Service
public class StatisticsKafkaServiceImpl implements StatisticsKafkaService {

	@Autowired
	LoggerService loggerService;

	@Override
	public void getKafkaStream() {
		//读取配置参数
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
		properties.put("group.id", "group-start");
		properties.put("enable.auto.commit", "true");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("max.poll.records", 1000);
		properties.put("auto.offset.reset", "earliest");
		properties.put("key.deserializer", StringDeserializer.class.getName());
		properties.put("value.deserializer", StringDeserializer.class.getName());
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);

		//loggerService.sendLogToKafka()

	}

}
