package com.gulimall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gulimall.service.LoggerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LogUploadController {

	@Autowired
	LoggerService loggerService;

	@RequestMapping("/applog")
	public String applog(@RequestBody String jsonLog) {
		//将日志落盘
		log.info(jsonLog);
		//将不同类型日志发送到kafka主题中
		return loggerService.sendLogToKafka(jsonLog);

	}
}
