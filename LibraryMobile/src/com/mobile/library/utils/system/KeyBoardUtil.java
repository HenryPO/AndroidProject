package com.mobile.library.utils.system;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @ClassName: KeyBoardUtil
 * @Description:  软键盘设置
 * @author lihy
 * @date 2014-7-13 下午5:27:51
 * 
 */
public class KeyBoardUtil {
	/**
	 * 
	 * @Title: setKeyBoardInvisible
	 * @Description:  隐藏键盘
	 * @param @param activity
	 * @param @param mEditText
	 * @return void
	 * @author lihy
	 */
	public static void setKeyBoardInvisible(Activity activity,
			EditText mEditText) {
		// 关闭软键盘
		// 获取负责系统输入的InputMethodManager
		InputMethodManager inputManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 关闭软键盘，第一个参数为EditText的token，第二个参数表示一般性的隐藏
		inputManager.hideSoftInputFromWindow(mEditText.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 
	 * @Title: setKeyBoardVisible
	 * @Description:  显示键盘
	 * @param @param activity
	 * @param @param mEditText
	 * @return void
	 * @author lihy
	 */
	public static void setKeyBoardVisible(Activity activity, EditText mEditText) {
		// 显示软键盘
		// 获取负责系统输入的InputMethodManager
		InputMethodManager inputManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 打开软键盘，第一个参数为输入焦点的位置，一般为某EditText，第二个参数表示显式地调用
		inputManager.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);

	}

}
