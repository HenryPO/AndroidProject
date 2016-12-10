package com.mobile.library.utils;

import android.util.Log;

public class MLog {

	private static String tag = "MLog";

	private static boolean debug = true;

	public static boolean isDebug() {
		return debug;
	}

	public static void v(String message) {
		if (debug) {
			Log.v(tag, message);
		}
	}

	public static void d(String message) {
		if (debug) {
			Log.d(tag, message);
		}
	}

	public static void i(String message) {
		if (debug) {
			Log.i(tag, message);
		}
	}

	public static void w(String message) {
		if (debug) {
			Log.w(tag, message);
		}
	}

	public static void e(String message) {
		if (debug) {
			Log.e(tag, message);
		}
	}

	public static void v(Class<?> clazz, String message) {
		if (debug) {
			Log.v(clazz.getCanonicalName(), message);
		}
	}

	public static void d(Class<?> clazz, String message) {
		if (debug) {
			Log.d(clazz.getCanonicalName(), message);
		}
	}

	public static void i(Class<?> clazz, String message) {
		if (debug) {
			Log.i(clazz.getCanonicalName(), message);
		}
	}

	public static void w(Class<?> clazz, String message) {
		if (debug) {
			Log.w(clazz.getCanonicalName(), message);
		}
	}

	public static void e(Class<?> clazz, String message) {
		if (debug) {
			Log.e(clazz.getCanonicalName(), message);
		}
	}

}
