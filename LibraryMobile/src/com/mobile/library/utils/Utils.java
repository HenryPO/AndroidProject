package com.mobile.library.utils;

/**
 * 通用工具类
 * 
 * @author lihy
 */
public class Utils {
	private static long lastClickTime;

	/**
	 * 是否是第一次点击判断 （防止按钮短时间内多次点击）
	 * 
	 * @return 是第一次点击
	 */
	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
