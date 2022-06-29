package com.gulimall.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataFormatUtil {

	private static Logger logger = LoggerFactory.getLogger(DataFormatUtil.class);

	/**
	 * author:chenyin
	 * parma:str
	 * return true if null or empty
	 */
	public static boolean isNullOrEmpty(String string) {
		if (string == null || "".equals(string)) { return true; }
		return false;
	}

	/**
	 * parma:str1,str2,str3...
	 * return true if any one is null or empty
	 */
	public static boolean isNullOrEmptyAny(String... strings) {
		for (String string : strings) {
			if (string == null || "".equals(string)) { return true; }
		}
		return false;
	}

	/**
	 * author:chenyin
	 * parma:list
	 * return true if null or empty
	 */
	public static <T> boolean isNullOrEmpty(Collection<T> list) {
		if (list == null || list.size() == 0) { return true; }
		return false;
	}

	public static <T> boolean isNullOrEmpty(Map<?, ?> map) {
		if (map == null || map.size() == 0) { return true; }
		return false;
	}

	private static Random random = new Random(new java.util.Date().getTime());

	public static Random nextRandom() {
		return random;
	}

	/**
	 * ["aa", "bb", "cc"], "&" -> "aa&bb&cc"
	 * @param collection
	 * @param separator
	 * @return String
	 */
	public static String join(Collection<?> collection, String separator) {
		return org.apache.commons.lang3.StringUtils.join(collection, separator);
	}

	/**
	 * ["aa", "bb", "cc"], "&" -> "aa&bb&cc"
	 * @param array
	 * @param separator
	 * @return String
	 */
	public static String join(Object[] array, String separator) {
		return org.apache.commons.lang3.StringUtils.join(array, separator);
	}

	/**
	 * 查看bean是否有某个属性。
	 * @param clazz
	 * @param propertyName
	 * @return boolean
	 */
	public static boolean containsProperty(Class<?> clazz, String propertyName) {
		PropertyDescriptor d = BeanUtils.getPropertyDescriptor(clazz, propertyName);
		return d != null;
	}

	/**
	 * 查看bean是否有某个属性。
	 * @param clazz
	 * @param propertyName
	 * @return boolean
	 */
	public static boolean containsPropertyIgnoreMark(Class<?> clazz, String propertyName) {
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
		boolean bln = Stream.of(pds).anyMatch(o -> o.getName().replace("_", "").equalsIgnoreCase(propertyName));
		return bln;
	}

	/**
	 * 将bean的数据，转为HashMap数据。
	 * 利用Introspector和PropertyDescriptor 将Bean --> Map。
	 * @param bean
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> transBeanToMap(Object bean, boolean nonNull) {
		if (bean == null) { return null; }
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (!key.equals("class")) {// 过滤class属性
					Method getter = property.getReadMethod();// 得到property对应的getter方法
					Object value = getter.invoke(bean);
					if (value == null && !nonNull) continue;
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			logger.info("transBean2Map Error " + e);
		}
		return map;
	}

	public static Map<String, Object> transBeanToMap(Object bean) {
		return transBeanToMap(bean, false);
	}

	/**
	 * 功能可能同transBeanToMap。
	 * @param bean
	 * @return Map<String, String>
	 */
	public static Map<String, String> describeBean(Object bean) {
		return describeBean(bean, true);
	}

	/**
	 * 功能可能同transBeanToMap。
	 * @param bean
	 * @param nonNull
	 * @return Map<String, String>
	 */
	public static Map<String, String> describeBean(Object bean, boolean nonNull) {
		if (bean == null) { return null; }
		Map<String, String> map = new HashMap<>();
		try {
			Map<String, String> map0 = org.apache.commons.beanutils.BeanUtils.describe(bean);
			map0.remove("class");
			for (Map.Entry<String, String> entry : map0.entrySet()) {
				if (entry.getValue() == null && nonNull) continue;
				map.put(entry.getKey(), entry.getValue());
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	public static Set<String> beanPropertyNames(Object bean) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
		Set<String> set = Arrays.asList(pds).stream().map(pd -> pd.getName()).collect(Collectors.toSet());
		set.remove("class");
		return set;
	}

	/**
	 * 返回第一个非DataFormatUtil.isNullOEmpty()的值。
	 * @return String
	 */
	public static String getFirstValue(String... strings) {
		for (String string : strings) {
			if (!isNullOrEmpty(string)) return string;
		}
		return null;
	}

	/**
	 * 获取gosn对象 对于转换处理Date类型数据
	 * @return
	 */
	public static Gson genGson() {
		return genGson(DateUtil.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 *
	 * @param dateFormat 传入日期格式
	 * @return
	 */
	public static Gson genGson(String dateFormat) {
		return new GsonBuilder().setDateFormat(dateFormat).create(); // new GsonBuilder().serializeNulls().create();
	}
}
