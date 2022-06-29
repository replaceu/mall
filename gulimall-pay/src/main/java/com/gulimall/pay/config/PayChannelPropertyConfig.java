package com.gulimall.pay.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pay.channel.property.config")
@PropertySource(value = "payChannelPropertyConfig.properties")
public class PayChannelPropertyConfig {
	public Map<String, String>	execute							= new HashMap<>();
	public Map<String, String>	wxAppPayMap						= new HashMap<>();
	public Map<String, String>	wxH5PayMap						= new HashMap<>();
	public Map<String, String>	wxJsapiPayMap					= new HashMap<>();
	public Map<String, String>	aliAppPayMap					= new HashMap<>();
	public Map<String, String>	wxspJsapiPayMap					= new HashMap<>();
	public Map<String, String>	aliAppPredictInstallmentPayMap	= new HashMap<>();

	public Map<String, String> getWxspJsapiPayMap() {
		return wxspJsapiPayMap;
	}

	public void setWxspJsapiPayMap(Map<String, String> wxspJsapiPayMap) {
		this.wxspJsapiPayMap = wxspJsapiPayMap;
	}

	public Map<String, String> getExecute() {
		return execute;
	}

	public void setExecute(Map<String, String> execute) {
		this.execute = execute;
	}

	public Map<String, String> getWxAppPayMap() {
		return wxAppPayMap;
	}

	public void setWxAppPayMap(Map<String, String> wxAppPayMap) {
		this.wxAppPayMap = wxAppPayMap;
	}

	public Map<String, String> getWxH5PayMap() {
		return wxH5PayMap;
	}

	public void setWxH5PayMap(Map<String, String> wxH5PayMap) {
		this.wxH5PayMap = wxH5PayMap;
	}

	public Map<String, String> getWxJsapiPayMap() {
		return wxJsapiPayMap;
	}

	public void setWxJsapiPayMap(Map<String, String> wxJsapiPayMap) {
		this.wxJsapiPayMap = wxJsapiPayMap;
	}

	public Map<String, String> getAliAppPayMap() {
		return aliAppPayMap;
	}

	public void setAliAppPayMap(Map<String, String> aliAppPayMap) {
		this.aliAppPayMap = aliAppPayMap;
	}

	public Map<String, String> getAliAppPredictInstallmentPayMap() {
		return aliAppPredictInstallmentPayMap;
	}

	public void setAliAppPredictInstallmentPayMap(Map<String, String> aliAppPredictInstallmentPayMap) {
		this.aliAppPredictInstallmentPayMap = aliAppPredictInstallmentPayMap;
	}
}
