package com.mobile.library.storage.sharedprefrences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @ClassName: SharePreferenceHelper
 * @Description: SharePreference工具类(通用数据储存)
 * @author lhy
 * @date 2014-9-11 上午10:02:09
 * 
 */
public class SharePreferenceHelper {
	private int versionNumber;

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * 
	 * @Title: getSharedPreferences
	 * @Description: 获取通用的SharedPreferences
	 * @param @param context
	 * @param @return 设定文件
	 * @return SharedPreferences 返回类型
	 * @throws
	 */
	public SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("common", Activity.MODE_PRIVATE);
		int version = preferences.getInt("version", 0);
		if (version < versionNumber) {
			Editor editor = preferences.edit();
			editor.clear();
			editor.commit();
			editor.putInt("version", versionNumber);
			editor.commit();
		}
		return preferences;
	}
}
