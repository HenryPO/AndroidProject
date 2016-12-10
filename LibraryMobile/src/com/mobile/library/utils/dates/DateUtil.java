package com.mobile.library.utils.dates;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.library.utils.StringUtil;

/**
 * 
 * Description 时间处理类<br>
 * CreateDate 2014-1-10 <br>
 * 
 * @author LHY <br>
 */
public class DateUtil {
	// 0001年到1970年的毫秒差值
	private final static long offset = 62135596800000l;

	/**
	 * format date yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String toStringDate(Date date) {
		return toStringDate(date, "yyyy-MM-dd");
	}

	/**
	 * format date by format
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toStringDate(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
		return df.format(date);
	}

	/**
	 * 
	 * @Title: getDate
	 * @Description: 将服务器时间转换成为本地时间
	 * @param @param
	 *            dateString
	 * @param @return
	 * @return Date
	 * @author lihy
	 */
	public static Date getDate(String dateString) {
		return getDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	}

	/**
	 * 字符串时间转弯成 Date
	 * 
	 * @param dateString
	 *            时间
	 * @param format
	 *            格式
	 * @return
	 */
	public static Date getDate(String dateString, String format) {
		return getDate(dateString, format, "GMT+8");
	}
	/**
	 * 字符串时间转弯成 Date
	 * 
	 * @param dateString
	 *            时间
	 * @param format
	 *            格式
	 * @param timeZone
	 *            时区
	 * @return
	 */
	public static Date getDate(String dateString, String format, String timeZone) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		// 从第一个字符开始解析
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(dateString, pos);
		return date;
	}

	/**
	 * 获取 0001年到该时间的毫秒值
	 * 
	 * @param date
	 * @return time 0001年到该时间的毫秒值
	 */
	public static long getDateTimeMilliSecond(Date date) {
		if (date == null)
			return 0;

		long time = date.getTime() + offset;
		return time;
	}

	/**
	 * 通过 0001年到该时间的毫秒值获取时间
	 * 
	 * @param time
	 *            0001年到该时间的毫秒值
	 * @return date
	 */
	public static Date getDateTimeByMilliSecond(long time) {
		Date date = new Date(time - offset);
		return date;
	}

}
