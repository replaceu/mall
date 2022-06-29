package com.gulimall.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtil {
	private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	private static final String UTF8 = "utf-8";

	/**
	 *  MD5数字签名
	 * @param src
	 * @return String
	 */
	public static String md5DigestUpperCase(String src) {
		return md5Digest(src, false);
	}

	public static String md5DigestLowerCase(String src) {
		return md5Digest(src, true);
	}

	public static String md5Digest(String src) {
		return md5Digest(src, true);
	}

	private static String md5Digest(String src, boolean toLowerCase) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(src.getBytes(UTF8));
			String result = hexEncode(b, toLowerCase);
			logger.debug(result + " < md5 : " + src);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}
	}

	public static String getSha1(String str) {
		if (null == str || 0 == str.length()) { return null; }
		String retStr = "";
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			retStr = new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retStr.toLowerCase();
	}

	/**
	 * BASE64编码
	 * @param bytes
	 * @return String
	 */
	public static String base64Encoder(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static String base64Encoder(String src) throws UnsupportedEncodingException {
		return Base64.encodeBase64String(src.getBytes(UTF8));
	}

	/**
	 *  BASE64解码
	 * @param dest
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String base64Decoder(String dest) throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(dest), UTF8);
	}

	public static byte[] base64DecoderToBytes(String dest) {
		return Base64.decodeBase64(dest);
	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * @param str
	 * @return byte[]
	 */

	public static byte[] hexDecode(String str) throws DecoderException {
		return org.apache.commons.codec.binary.Hex.decodeHex(str.toCharArray());
	}

	/**
	 * URL Encode.
	 * 异常情况，返回原串，以简化业务代码的复杂性。
	 * @param bytes
	 * @param toLowerCase
	 * @return String
	 * @throws DecoderException
	 */
	public static String hexEncode(byte[] bytes, boolean toLowerCase) throws DecoderException {
		return new String(org.apache.commons.codec.binary.Hex.encodeHex(bytes, toLowerCase));
	}

	public static String hexEncode(byte[] bytes) throws DecoderException {
		return hexEncode(bytes, true);
	}

	public static String urlEncode(String string, String enc) {
		try {
			return URLEncoder.encode(string, enc);
		} catch (UnsupportedEncodingException e) {
			return string; // 异常情况，返回原串，以简化业务代码的复杂性。
		}
	}

	/**
	 * URL Decode.
	 * 异常情况，返回原串，以简化业务代码的复杂性。
	 * @param string
	 * @param enc
	 * @return String
	 */
	public static String urlDecode(String string, String enc) {
		try {
			return URLDecoder.decode(string, enc);
		} catch (UnsupportedEncodingException e) {
			return string; // 异常情况，返回原串，以简化业务代码的复杂性。
		}
	}

	public static String escapeEncode(String src) {
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			char j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) sb.append(j);
			else if (j < 256) {
				sb.append("%");
				if (j < 16) sb.append("0");
				sb.append(Integer.toString(j, 16));
			} else {
				sb.append("%u");
				sb.append(Integer.toString(j, 16));
			}
		}
		return sb.toString();
	}

	public static String escapeDecode(String src) {
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length());

		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					sb.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					sb.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					sb.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					sb.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return sb.toString();
	}

	public static String cStringEncode(String string) {
		if (string == null) return null;
		string = string.replaceAll("\\\\", "\\\\\\\\");
		string = string.replaceAll("\\'", "\\\\'");
		string = string.replaceAll("\\\"", "\\\\\"");
		string = string.replaceAll("\r", "\\\\r");
		string = string.replaceAll("\n", "\\\\n");
		string = string.replaceAll("\t", "\\\\t");
		return string;
	}

	public static String cStringDecode(String string) {
		if (string == null) return null;
		string = string.replaceAll("\t", "\\\\t");
		string = string.replaceAll("\n", "\\\\n");
		string = string.replaceAll("\r", "\\\\r");
		string = string.replaceAll("\\\"", "\\\\\"");
		string = string.replaceAll("\\'", "\\\\'");
		string = string.replaceAll("\\\\", "\\\\\\\\");
		return string;
	}
}
