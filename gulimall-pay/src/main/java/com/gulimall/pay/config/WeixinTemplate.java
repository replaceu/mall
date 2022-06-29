package com.gulimall.pay.config;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.gulimall.common.constant.IPayConstants;
import com.gulimall.common.utils.EncryptUtil;

import lombok.Data;

@ConfigurationProperties(prefix = "weixin")
@Component
@Data
public class WeixinTemplate implements WXPayConfig {
	String	appId;
	String	mchId;
	String	key;
	byte[]	certData;
	String	connectTimeOut;
	String	readTimeOut;

	public WeixinTemplate(Map<String, String> param) throws Exception {
		this.appId = param.get(IPayConstants.WxAppPayMap.appId);
		this.mchId = param.get(IPayConstants.WxAppPayMap.mchId);
		this.key = param.get(IPayConstants.WxAppPayMap.key);
		String certPath = param.get(IPayConstants.WxAppPayMap.certPath);
		File file = new File(certPath);
		FileInputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();
		this.connectTimeOut = param.get(IPayConstants.WxAppPayMap.connectTimeOut);
		this.readTimeOut = param.get(IPayConstants.WxAppPayMap.readTimeOut);
	}

	@Override
	public String getAppID() {
		return this.appId;
	}

	@Override
	public String getMchID() {
		return this.mchId;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return Integer.valueOf(this.connectTimeOut);
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return Integer.valueOf(this.readTimeOut);
	}

	public String getSign(Map<String, String> data) throws Exception {
		Set<String> keySet = data.keySet();
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		//StringBuilder:可变字符串对象
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : keyArray) {
			if (key.equals(WXPayConstants.FIELD_SIGN)) {
				continue;
			}
			//参数值为空，则不参与签名
			if (data.get(key).trim().length() > 0) {
				stringBuilder.append(key).append("=").append(data.get(key).trim()).append("&");
			}
		}
		stringBuilder.append("key=").append(this.key);
		MessageDigest messageDigest = null;
		try {
			//生成实现MD5指定摘要算法的MessageDigest对象
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] array = new byte[0];
		try {
			array = messageDigest.digest(stringBuilder.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		//将字符串转化为大写
		return sb.toString().toUpperCase();

	}

	//对于数据的解密
	public static String decryptData(String base64Data, String configKey) throws Exception {
		//EncryptUtil：加密的工具类
		SecretKeySpec key = new SecretKeySpec(EncryptUtil.md5DigestLowerCase(configKey).getBytes(), "AES");
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64Utils.decodeFromString(base64Data)));
	}

}
