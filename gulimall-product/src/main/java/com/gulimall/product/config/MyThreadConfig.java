package com.gulimall.product.config;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author aqiang9  2020-09-01
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class MyThreadConfig {

	/**
	 * corePoolSize:核心线程池大小
	 * maximumPoolSize:最大线程池大小
	 * keepAliveTime:空闲线程多长时间关闭
	 * unit:时间单位
	 * workQueue:阻塞队列
	 * threadFactory:线程工厂
	 * handler:拒绝策略
	 *
	 * @return
	 */
	@Bean
	public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties poolConfigProperties) {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(poolConfigProperties.getCoreSize(), poolConfigProperties.getMaxSize(), poolConfigProperties.getKeepAliveTime(), TimeUnit.SECONDS, new LinkedBlockingDeque<>(100000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
		return poolExecutor;
	}
}
