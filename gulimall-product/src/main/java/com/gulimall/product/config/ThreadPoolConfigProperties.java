package com.gulimall.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-09-01
 */
@ConfigurationProperties(prefix = "gulimall.thread")
@Getter
@Setter
public class ThreadPoolConfigProperties {
	private Integer	coreSize		= 10;
	private Integer	maxSize			= 200;
	private Integer	keepAliveTime	= 15;

	public Integer getCoreSize() {
		return coreSize;
	}

	public void setCoreSize(Integer coreSize) {
		this.coreSize = coreSize;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(Integer keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
}
