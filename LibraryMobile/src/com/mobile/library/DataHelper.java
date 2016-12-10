package com.mobile.library;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.mobile.library.storage.sharedprefrences.SharePreferenceHelper;
import com.mobile.library.storage.sqlite.DataBaseOpenHelper;
import com.mobile.library.storage.sqlite.OnSqliteUpdateListener;

/**
 * 
 * @Description:持久化数据模块外部调用入口
 * @author:lihy
 * @time:2015-2-11 下午4:55:22
 */
public class DataHelper {

	private static DataHelper self;
	private SharePreferenceHelper preferenceHelper;
	private DataBaseOpenHelper dataBaseOpenHelper;

	public static DataHelper getInstance() {
		if (self == null) {
			self = new DataHelper();
		}
		return self;
	}

	private DataHelper() {
		preferenceHelper = new SharePreferenceHelper();
	}

	/**
	 * 
	 * @Description:获取SharedPreferences版本号
	 * @return int
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-11 下午5:01:27
	 */
	public int getVersionNumber() {
		return preferenceHelper.getVersionNumber();
	}

	/**
	 * 
	 * @Description:设置SharedPreferences版本号
	 * @param versionNumber
	 *            void
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-11 下午5:01:46
	 */
	public void setVersionNumber(int versionNumber) {
		preferenceHelper.setVersionNumber(versionNumber);
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
		return preferenceHelper.getSharedPreferences(context);
	}

	/**
	 * 
	 * @Description:获取数据库实例
	 * @param context
	 * @param dbName
	 *            数据库名称
	 * @param dbVersion
	 *            版本号
	 * @param tableSqls
	 *            建表语句列表
	 * @return DataBaseOpenHelper
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-11 下午5:06:28
	 */
	public DataBaseOpenHelper getDBInstance(Context context, String dbName, int dbVersion, List<String> tableSqls) {
		dataBaseOpenHelper = DataBaseOpenHelper.getInstance(context, dbName, dbVersion, tableSqls);
		return dataBaseOpenHelper;
	}

	/**
	 * 
	 * @Title: execSQL
	 * @Description: Sql写入
	 * @param @param sql
	 * @param @param bindArgs
	 * @return void
	 * @author lihy
	 */
	public void execSQL(String sql, Object[] bindArgs) {
		dataBaseOpenHelper.execSQL(sql, bindArgs);
	}

	/**
	 * 
	 * @Title: rawQuery
	 * @Description:
	 * @param @param sql查询
	 * @param @param bindArgs
	 * @param @return
	 * @return Cursor
	 * @author lihy
	 */
	public Cursor rawQuery(String sql, String[] bindArgs) {
		return dataBaseOpenHelper.rawQuery(sql, bindArgs);
	}

	/**
	 * 
	 * @Title: insert
	 * @Description: 插入数据
	 * @param @param table
	 * @param @param contentValues 设定文件
	 * @return void 返回类型
	 * @author lihy
	 * @throws
	 */
	public void insert(String table, ContentValues contentValues) {
		dataBaseOpenHelper.insert(table, contentValues);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description:删除
	 * @param @param table
	 * @param @param whereClause
	 * @param @param whereArgs
	 * @return void
	 * @author lihy
	 */
	public void delete(String table, String whereClause, String[] whereArgs) {
		dataBaseOpenHelper.delete(table, whereClause, whereArgs);
	}

	/**
	 * 
	 * @Title: query
	 * @Description: 查
	 * @param @param table
	 * @param @param columns
	 * @param @param selection
	 * @param @param selectionArgs
	 * @param @param groupBy
	 * @param @param having
	 * @param @param orderBy
	 * @return void
	 * @author lihy
	 */
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return dataBaseOpenHelper.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	/**
	 * 
	 * @Description:查
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 * Cursor
	 * @exception:
	 * @author: lihy
	 * @time:2015-4-3 上午9:38:33
	 */
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy,
			String limit) {
		return dataBaseOpenHelper.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}

	/**
	 * 
	 * @Description 查询，方法重载,table表名，sqlString条件
	 * @param @return
	 * @return Cursor
	 * @CreateDate: 2014-11-20 上午11:30:32
	 * @author zhengxr
	 */
	public Cursor query(String tableName, String sqlString) {
		return dataBaseOpenHelper.query(tableName, sqlString);
	}

	/**
	 * 
	 * @Description: 更新
	 * @param @param table
	 * @param @param values
	 * @param @param whereClause
	 * @param @param whereArgs 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		dataBaseOpenHelper.update(table, values, whereClause, whereArgs);
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#close()
	 */
	public void clear() {
		dataBaseOpenHelper.clear();
	}

	/**
	 * 
	 * @Description:添加数据库更新监听
	 * @param onSqliteUpdateListener
	 *            void
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-11 下午5:11:56
	 */
	public void setOnSqliteUpdateListener(OnSqliteUpdateListener onSqliteUpdateListener) {
		dataBaseOpenHelper.setOnSqliteUpdateListener(onSqliteUpdateListener);
	}
}
