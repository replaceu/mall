package com.gulimall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableRedisHttpSession
public class StatisticsApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}
}

/**
 * 当日用户首次登录（日活）分时趋势图，昨日对比
 * 1.SparkStreaming消费kafka数据，这里只需要统计启动日志即可
 * 2.利用redis过滤当日已经计入的日活设备（对一个用户的多次访问进行去重）
 * 3.将每个批次新增的当日日活信息保存到ES中
 */