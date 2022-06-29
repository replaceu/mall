package com.gulimall.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * Long Format of Date.
	 */
	public static final String	YYYY_MM_DD			= "yyyy-MM-dd";
	/**
	 * Long Format of Time.
	 */
	public static final String	YYYY_MM_DD_HH_MM_SS	= "yyyy-MM-dd HH:mm:ss";
	/**
	 * Short Format of Date.
	 */
	public static final String	YYYYMMDD			= "yyyyMMdd";
	/**
	 * Short Format of Time.
	 */
	public static final String	YYYYMMDDHHMMSS		= "yyyyMMddHHmmss";
	/**
	 * RexExp of Time.
	 */
	public static final String	REGEXP_YYYY_MM_DD	= "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

	public static Date getTime() {
		return new Date();
	}

	public static String getTimeInFormat(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String format(Date date, String format) {
		if (date == null) return null;
		return new SimpleDateFormat(format).format(date);
	}

	public static String getTimeInLongFormat() {
		return getTimeInLongFormat(new Date());
	}

	public static String getTimeInShortFormat() {
		return getTimeInShortFormat(new Date());
	}

	public static String getTimeInLongFormat(Date time) {
		return time == null ? "" : new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(time);
	}

	public static String getTimeInShortFormat(Date time) {
		return time == null ? "" : new SimpleDateFormat(YYYYMMDDHHMMSS).format(time);
	}

	public static Date parseTimeInLongFormat(String strTime) {
		return StringUtils.isEmpty(strTime) ? null : parseTime(strTime, YYYY_MM_DD_HH_MM_SS);
	}

	public static Date parseTimeInShortFormat(String strTime) {
		return StringUtils.isEmpty(strTime) ? null : parseTime(strTime, YYYYMMDDHHMMSS);
	}

	public static Date parseTime(String strTime, String format) {
		try {
			return new SimpleDateFormat(format).parse(strTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getDate() {
		return DateUtils.truncate(new Date(), Calendar.DATE);
	}

	public static String getDateInShortFormat() {
		return getDateInShortFormat(new Date());
	}

	public static String getDateInShortFormat(Date date) {
		return date == null ? "" : new SimpleDateFormat(YYYYMMDD).format(date);
	}

	public static String getDateInLongFormat(Date date) {
		return date == null ? "" : new SimpleDateFormat(YYYY_MM_DD).format(date);
	}

	public static Date parseDateInLongFormat(String strDate) {
		return StringUtils.isEmpty(strDate) ? null : parseDate(strDate, YYYY_MM_DD);
	}

	public static Date parseDateInShortFormat(String strDate) {
		return StringUtils.isEmpty(strDate) ? null : parseDate(strDate, YYYYMMDD);
	}

	public static Date parseDate(String strDate, String format) {
		try {
			Date date = parseTime(strDate, format);
			date = DateUtils.truncate(date, Calendar.DATE);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param strDate
	 * @param days
	 * @return String
	 * @throws ParseException
	 */
	public static String addDateWithDays(String strDate, int days) {
		if (StringUtils.isEmpty(strDate)) return "";
		Date date = parseDateInLongFormat(strDate);
		date = DateUtils.addDays(date, days);
		return getDateInLongFormat(date);
	}

	/**
	 *
	 * @param date
	 * @param days
	 * @return Date
	 */
	public static Date addDateWithDays(Date date, int days) {
		if (date == null) return null;
		return DateUtils.addDays(date, days);
	}

	/**
	 * 算出两个日期间的天数，包含头尾，传入的日期顺序为从小到大。
	 *
	 * @param strDateFrom
	 * @param strDateTo
	 * @return int
	 */
	public static int daysBetweenTwoDate(String strDateFrom, String strDateTo) {
		// 异常判断处理
		if (StringUtils.isEmpty(strDateFrom) || StringUtils.isEmpty(strDateTo)) return 0;

		return daysBetweenTwoDate(parseDateInLongFormat(strDateFrom), parseDateInLongFormat(strDateTo));
	}

	/**
	 * 算出两个日期间的天数，包含头尾，传入的日期顺序为从小到大。
	 *
	 * @param dateFrom
	 * @param dateTo
	 * @return int
	 */
	public static int daysBetweenTwoDate(Date dateFrom, Date dateTo) {
		// 异常判断处理
		if (dateFrom == null || dateTo == null) return 0;

		//
		int m = (int) (dateFrom.getTime() / (24 * 60 * 60 * 1000L));
		int n = (int) (dateTo.getTime() / (24 * 60 * 60 * 1000L));
		return n - m; //
	}

	/**
	 *
	 * @param time
	 * @return String
	 */
	public static String getLongDateFromSeconds(long time) {
		return getLongDateFromMilliSeconds(time * 1000); // long 1470808850
	}

	public static String getLongDateFromSeconds(String time) {
		return DataFormatUtil.isNullOrEmpty(time) ? "" : getLongDateFromSeconds(Long.parseLong(time)); // long 1470808850
	}

	public static String getSecondsFromLongDate(String time) {
		if (DataFormatUtil.isNullOrEmpty(time)) return "";
		Date date = parseDate(time, YYYY_MM_DD_HH_MM_SS);
		return date == null ? "" : String.valueOf(date.getTime());
	}

	/**
	 *
	 * @param milliseconds
	 * @return String
	 */
	public static String getLongDateFromMilliSeconds(long milliseconds) {
		return getTimeInLongFormat(new Date(milliseconds)); // long 1470808850999
	}

	/**
	 * 日期范围判断
	 *
	 * @param theDate
	 * @param fromDate
	 * @param toDate
	 * @return boolean
	 */
	public static boolean dateBetween(String theDate, String fromDate, String toDate) {
		// 异常判断处理
		if (StringUtils.isEmpty(theDate) || StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) return false;

		return fromDate.compareTo(theDate) <= 0 && theDate.compareTo(toDate) <= 0;
	}

	/**
	 * 时间累加
	 * author yin.chen
	 *
	 * @param date
	 *            当前日期 格式必须为 yyyyMMddHHmmss
	 * @param hour
	 *            累加的时间 单位是小时
	 * @return String
	 */
	public static String dateAddHour(String date, String hour) {
		double addHour = hour == null ? 1 : Double.parseDouble(hour);
		Long seconds = (long) (addHour * 3600);
		return dateAddSecond(date, seconds);
	}

	/**
	 * 时间累加
	 * author yin.chen
	 *
	 * @param date
	 *            当前日期 格式必须为 yyyyMMddHHmmss
	 * @param hour
	 *            累加的时间 单位是小时
	 * @return String
	 */
	public static String dateAddHour(String date, Integer hour) {
		long addHour = hour == null ? 1 : hour;
		Long seconds = addHour * 3600;
		return dateAddSecond(date, seconds);
	}

	/**
	 * 时间累加
	 * author yin.chen
	 *
	 * @param date
	 *            当前日期 格式必须为 yyyyMMddHHmmss
	 * @param second
	 *            累加的时间 单位是秒
	 * @return String
	 */
	public static String dateAddSecond(String date, Long second) {
		try {
			return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date((new SimpleDateFormat("yyyyMMddHHmmss").parse(date).getTime() + second * 1000)));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static String convertTimeFormatLongToShort(String strlongtime) {
		if (StringUtils.isEmpty(strlongtime)) { return ""; }
		//
		return strlongtime.replaceAll("-|\\s|:", "");
	}

	public static String convertTimeFormatShortToLong(String strshorttime) {
		if (StringUtils.isEmpty(strshorttime)) { return ""; }
		if (strshorttime.matches("^\\d{8}$")) {
			strshorttime += "000000";
		}
		if (strshorttime.length() != 14) {
			logger.error("字符串长度有误，应该为14！" + strshorttime);
			return strshorttime;
		}
		if (!strshorttime.matches("^\\d{14}$")) {
			logger.info("时间串格式有误，必须是14位数字！" + strshorttime);
			return strshorttime;
		}
		//
		StringBuffer sb = new StringBuffer(strshorttime);
		sb.insert(12, ":").insert(10, ":").insert(8, " ").insert(6, "-").insert(4, "-");
		return sb.toString();
	}

	/**
	 * 获取最小的日期
	 * @param strDates
	 * @return String
	 */
	public static String min(String... strDates) {
		String minDate = strDates[0];
		for (String strDate : strDates) {
			if (minDate == null) {
				minDate = strDate;
				continue;
			}
			if (strDate == null) continue;
			if (minDate.compareTo(strDate) > 0) minDate = strDate;
		}
		return minDate;
	}

	/**
	 * 获取最大的日期
	 * @param strDates
	 * @return String
	 */
	public static String max(String... strDates) {
		String maxDate = strDates[0];
		for (String strDate : strDates) {
			if (maxDate == null) {
				maxDate = strDate;
				continue;
			}
			if (strDate == null) continue;
			if (maxDate.compareTo(strDate) < 0) maxDate = strDate;
		}
		return maxDate;
	}

	/**
	 *判断某时间是否在区间内
	 * @param time
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean belongCalendar(Date time, Date from, Date to) {
		Calendar date = Calendar.getInstance();
		date.setTime(time);

		Calendar after = Calendar.getInstance();
		after.setTime(from);

		Calendar before = Calendar.getInstance();
		before.setTime(to);

		if (date.after(after) && date.before(before)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getWeekMondayInShortFormat() {
		return getWeekMonday(YYYYMMDD);
	}

	public static String getWeekMonday(String format) {
		Calendar monday = Calendar.getInstance(Locale.CHINA);
		monday.setFirstDayOfWeek(Calendar.MONDAY);
		monday.setTimeInMillis(System.currentTimeMillis());
		monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
		return new SimpleDateFormat(format).format(monday.getTime());
	}

	public static String getWeekTuesdayInShortFormat() {
		return getWeekTuesday(YYYYMMDD);
	}

	public static String getWeekTuesday(String format) {
		Calendar monday = Calendar.getInstance(Locale.CHINA);
		monday.setFirstDayOfWeek(Calendar.MONDAY);
		monday.setTimeInMillis(System.currentTimeMillis());
		monday.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);//周二
		return new SimpleDateFormat(format).format(monday.getTime());
	}

	public static String getWeekWednesdayInShortFormat() {
		return getWeekWednesday(YYYYMMDD);
	}

	public static String getWeekWednesday(String format) {
		Calendar monday = Calendar.getInstance(Locale.CHINA);
		monday.setFirstDayOfWeek(Calendar.MONDAY);
		monday.setTimeInMillis(System.currentTimeMillis());
		monday.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);//周三
		return new SimpleDateFormat(format).format(monday.getTime());
	}

	public static String getWeekThursdayInShortFormat() {
		return getWeekThursday(YYYYMMDD);
	}

	public static String getWeekThursday(String format) {
		Calendar monday = Calendar.getInstance(Locale.CHINA);
		monday.setFirstDayOfWeek(Calendar.MONDAY);
		monday.setTimeInMillis(System.currentTimeMillis());
		monday.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);//周四
		return new SimpleDateFormat(format).format(monday.getTime());
	}

	public static String getWeekFridayInShortFormat() {
		return getWeekFriday(YYYYMMDD);
	}

	public static String getWeekFriday(String format) {
		Calendar monday = Calendar.getInstance(Locale.CHINA);
		monday.setFirstDayOfWeek(Calendar.MONDAY);
		monday.setTimeInMillis(System.currentTimeMillis());
		monday.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);//周五
		return new SimpleDateFormat(format).format(monday.getTime());
	}

	public static String getWeekSaturdayInShortFormat() {
		return getWeekSaturday(YYYYMMDD);
	}

	public static String getWeekSaturday(String format) {
		Calendar monday = Calendar.getInstance(Locale.CHINA);
		monday.setFirstDayOfWeek(Calendar.MONDAY);
		monday.setTimeInMillis(System.currentTimeMillis());
		monday.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);//周六
		return new SimpleDateFormat(format).format(monday.getTime());
	}

	public static String getWeekSundayInShortFormat() {
		return getWeekSunday(YYYYMMDD);
	}

	public static String getWeekSunday(String format) {
		Calendar sunday = Calendar.getInstance(Locale.CHINA);
		sunday.setFirstDayOfWeek(Calendar.MONDAY);
		sunday.setTimeInMillis(System.currentTimeMillis());
		sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//周日
		return new SimpleDateFormat(format).format(sunday.getTime());
	}

	public static void main(String[] args) {
		String monday = getWeekMondayInShortFormat();
		System.out.println(monday);
		String tuesday = getWeekTuesdayInShortFormat();
		System.out.println(tuesday);
		String wednesday = getWeekWednesdayInShortFormat();
		System.out.println(wednesday);
		String thursday = getWeekThursdayInShortFormat();
		System.out.println(thursday);
		String friday = getWeekFridayInShortFormat();
		System.out.println(friday);
		String saturday = getWeekSaturdayInShortFormat();
		System.out.println(saturday);
		String sunday = getWeekSundayInShortFormat();
		System.out.println(sunday);

	}
}
