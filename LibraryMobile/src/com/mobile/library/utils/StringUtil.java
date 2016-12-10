package com.mobile.library.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 
	 * @Title: getSdFileName
	 * @Description: 将网络文件转换成本地文件名
	 * @param @param
	 *            url
	 * @param @return
	 * @return String
	 * @author lihy
	 */
	public static String getSdFileName(String url) {
		return url.replace("/", "").replace(":", "").replace("?", "");
	}

	/**
	 * 剔除字符串尾的特定字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String trimEnd(String str, char trimChar) {
		if (StringUtil.isNullOrEmpty(str))
			return str;
		char[] charArray = str.toCharArray();
		int index = -1;
		for (int i = charArray.length; i >= 0; i--) {
			if (charArray[i - 1] != trimChar) {
				index = i;
				break;
			}
		}
		if (index < 0)
			return str;
		return String.valueOf(charArray, 0, index);
	}

	/**
	 * 剔除字符串头上的特定字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String trimStart(String str, char trimChar) {
		if (StringUtil.isNullOrEmpty(str))
			return str;
		char[] charArray = str.toCharArray();
		int index = -1;
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] != trimChar) {
				index = i;
				break;
			}
		}
		if (index < 0)
			return str;
		return String.valueOf(charArray, index, charArray.length - index);
	}

	/**
	 * 是否是Null或者为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.equals(""));
	}

	/**
	 * 处理 Null 字符串
	 * 
	 * @return
	 */
	public static String checkNull(String str) {
		return checkNull(str, "");
	}

	/**
	 * 处理 Null 字符串,返回默认字段
	 * 
	 * @param str
	 *            数据
	 * @param defaultStr
	 *            默认展示
	 * @return
	 */
	public static String checkNull(String str, String defaultStr) {
		if (isNullOrEmpty(str)) {
			return defaultStr;
		}
		return str;
	}

	/**
	 * 是否为Null或者为空
	 * 
	 * @param sb
	 *            被判定的字符串
	 * @return 是否为Null或者空
	 */
	public static boolean IsNullOrEmpty(StringBuffer sb) {
		return sb == null || isNullOrEmpty(sb.toString());
	}

	/**
	 * 连接字符串
	 * 
	 * @param con
	 *            连接符
	 * @param items
	 *            连接的对象
	 * @return
	 */
	public static String Join(String con, List<String> items) {
		if (items.size() == 0)
			return "";

		if (items.size() == 1)
			return items.get(0);

		StringBuffer sb = new StringBuffer(items.get(0));
		for (int i = 1; i < items.size(); i++) {
			sb.append(con + items.get(i));
		}
		return sb.toString();
	}

	/**
	 * arg0是否是以arg1开头的
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean IsStartWith(String arg0, String arg1) {
		if (isNullOrEmpty(arg1) || isNullOrEmpty(arg0))
			return false;

		if (arg1.length() >= arg0.length())
			return false;

		String str = arg0.substring(0, arg1.length());
		return arg1.equals(str);
	}

	/**
	 * arg0是否是以arg1结尾的
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean IsEndWith(String arg0, String arg1) {
		if (isNullOrEmpty(arg1) || isNullOrEmpty(arg0))
			return false;

		if (arg1.length() > arg0.length())
			return false;

		String str = arg0.substring(arg0.length() - arg1.length());
		return arg1.equals(str);
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String FirstLetterToUpperCase(String str) {
		if (isNullOrEmpty(str))
			return str;
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String FirstLetterToLowerCase(String str) {
		if (isNullOrEmpty(str))
			return str;
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}

	/**
	 * 获取字符串长度（区分单双字节）
	 * 
	 * @param s
	 * @return
	 */
	public static int getLength(CharSequence s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;
		}
		return length / 2 + (length % 2 == 0 ? 0 : 1);
	}

	public static double StringToDouble(String str) {
		return Double.valueOf(str);
	}

	/**
	 * 
	 * @Description 是否为手机号，web端提供的
	 * @param @param
	 *            str
	 * @param @return
	 * @return boolean
	 * @CreateDate: 2014-11-24 下午1:29:52
	 * @author zhengxr
	 */
	public static boolean isPhoneNo(String str) {
		return str.matches("^(13|14|15|18)[0-9]{9}$");
	}

	/**
	 * 
	 * @Description 是否为数字
	 * @param @param
	 *            str
	 * @param @return
	 * @return boolean
	 * @CreateDate: 2014-11-24 下午1:30:17
	 * @author zhengxr
	 */
	public static boolean isNumber(String str) {
		return str.matches("^\\d+$");
	}

	/**
	 * 
	 * @Description 是否为email
	 * @param @param
	 *            str
	 * @param @return
	 * @return boolean
	 * @CreateDate: 2014-11-24 下午1:30:17
	 * @author zhengxr
	 */
	public static boolean isEmail(String str) {
		return str.matches(
				"^[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\\.[a-z A-Z 0-9 _]+)+(\\,[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\\.[a-z A-Z 0-9 _]+)+)*$");
	}

	/**
	 * 
	 * @Description 是否整个字符串为网址
	 * @param @param
	 *            str
	 * @param @return
	 * @return boolean
	 * @CreateDate: 2014-11-24 下午1:30:17
	 * @author zhengxr
	 */
	public static boolean isWebSiteAll(String str) {
		return str.matches("http[s]?://[a-zA-z0-9]+.([a-zA-z0-9]+)+");
	}

	/**
	 * @Description 是否字符串中包含网址
	 * @param @param
	 *            str
	 * @param @return
	 * @return boolean
	 * @CreateDate: 2014-11-24 下午1:30:17
	 * @author zhengxr
	 */
	public static boolean isWebSitePart(String str) {
		String patternString = "http[s]?://[a-zA-z0-9]+.([a-zA-z0-9]+)+";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		return matcher.find();
	}

	/**
	 * 
	 * @Description:数组拼接
	 * @param first
	 * @param second
	 * @return T[]
	 * @exception:
	 * @author: lihy
	 * @time:2015-4-1 下午3:13:01
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * 转换网络路径
	 * 
	 * @param s
	 * @return 无中文的 Url
	 */
	public static String toURLString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				try {
					sb.append(URLEncoder.encode(String.valueOf(c), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
