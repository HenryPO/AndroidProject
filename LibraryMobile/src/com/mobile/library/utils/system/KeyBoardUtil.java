package com.mobile.library.utils.system;

import com.mobile.library.utils.MLog;
import com.mobile.library.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @ClassName: KeyBoardUtil
 * @Description: 软键盘设置
 * @author lihy
 * @date 2014-7-13 下午5:27:51
 * 
 */
public class KeyBoardUtil {
	private KeyBoardUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 避免输入法面板遮挡
	 * <p>
	 * 在manifest.xml中activity中设置
	 * </p>
	 * <p>
	 * android:windowSoftInputMode="adjustPan"
	 * </p>
	 */

	/**
	 * 动态隐藏软键盘
	 *
	 * @param activity
	 *            activity
	 */
	public static void hideSoftInput(Activity activity) {
		View view = activity.getCurrentFocus();
		if (view == null)
			view = new View(activity);
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 点击屏幕空白区域隐藏软键盘
	 * <p>
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
	 * </p>
	 * <p>
	 * 需重写dispatchTouchEvent
	 * </p>
	 * <p>
	 * 参照以下注释代码
	 * </p>
	 */
	public static void clickBlankArea2HideSoftInput() {
		MLog.d("tips", "U should copy the following code.");
		/*
		 * @Override public boolean dispatchTouchEvent(MotionEvent ev) { if
		 * (ev.getAction() == MotionEvent.ACTION_DOWN) { View v =
		 * getCurrentFocus(); if (isShouldHideKeyboard(v, ev)) {
		 * InputMethodManager imm = (InputMethodManager)
		 * getSystemService(Context.INPUT_METHOD_SERVICE);
		 * imm.hideSoftInputFromWindow(v.getWindowToken(),
		 * InputMethodManager.HIDE_NOT_ALWAYS); } } return
		 * super.dispatchTouchEvent(ev); }
		 * 
		 * // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘 private boolean
		 * isShouldHideKeyboard(View v, MotionEvent event) { if (v != null && (v
		 * instanceof EditText)) { int[] l = {0, 0}; v.getLocationInWindow(l);
		 * int left = l[0], top = l[1], bottom = top + v.getHeight(), right =
		 * left + v.getWidth(); return !(event.getX() > left && event.getX() <
		 * right && event.getY() > top && event.getY() < bottom); } return
		 * false; }
		 */
	}

	/**
	 * 动态显示软键盘
	 *
	 * @param edit
	 *            输入框
	 */
	public static void showSoftInput(EditText edit) {
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit, 0);
	}

	/**
	 * 切换键盘显示与否状态
	 */
	public static void toggleSoftInput() {
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * 
	 * @Title: setKeyBoardInvisible
	 * @Description: 隐藏键盘
	 * @param @param
	 *            activity
	 * @param @param
	 *            mEditText
	 * @return void
	 * @author lihy
	 */
	public static void setKeyBoardInvisible(Activity activity, EditText mEditText) {
		// 关闭软键盘
		// 获取负责系统输入的InputMethodManager
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 关闭软键盘，第一个参数为EditText的token，第二个参数表示一般性的隐藏
		inputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 
	 * @Title: setKeyBoardVisible
	 * @Description: 显示键盘
	 * @param @param
	 *            activity
	 * @param @param
	 *            mEditText
	 * @return void
	 * @author lihy
	 */
	public static void setKeyBoardVisible(Activity activity, EditText mEditText) {
		// 显示软键盘
		// 获取负责系统输入的InputMethodManager
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 打开软键盘，第一个参数为输入焦点的位置，一般为某EditText，第二个参数表示显式地调用
		inputManager.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);

	}

}
