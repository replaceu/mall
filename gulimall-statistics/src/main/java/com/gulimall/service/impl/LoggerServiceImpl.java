package com.gulimall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gulimall.service.LoggerService;

public class LoggerServiceImpl implements LoggerService {

	@Autowired
	KafkaTemplate kafkaTemplate;

	/**
	 * 将日志发送到kafka中
	 * @param jsonLog
	 * @return
	 */
	@Override
	public String sendLogToKafka(String jsonLog) {

		JSONObject jsonObject = JSON.parseObject(jsonLog);
		if (jsonObject.getJSONObject("start") != null) {
			//启动类型日志
			kafkaTemplate.send("guliMall_start-bak", jsonLog);
		} else {
			//事件日志
			kafkaTemplate.send("guliMall_event-bak", jsonLog);
		}
		return "SUCCESS";
	}
}
