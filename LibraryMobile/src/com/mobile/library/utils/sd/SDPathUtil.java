package com.mobile.library.utils.sd;

import java.io.File;

import android.content.Context;

import com.mobile.library.utils.IdGeneratorUtil;

/**
 * 
 * @ClassName: SDPathUtil
 * @Description: sd卡路径管理
 * @author lhy
 * @date 2014-10-10 下午3:33:25
 * 
 */
public class SDPathUtil {

	/**
	 * 项目根目录名称
	 */
	private String rootName = "Mobile";

	public SDPathUtil(String rootName) {
		this.rootName = rootName;
	}


	/**
	 * 
	 * @Title: getRootPath
	 * @Description: 获取项目文件根目录
	 * @param @param mContext
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getRootPath(Context mContext) {
		return SDDataUtil.getFileDir(mContext) + "/" + rootName + "/";
	}

	/**
	 * 
	 * @Description 获取web下载路径
	 * @param @param mContext
	 * @param @return
	 * @return String
	 * @CreateDate: 2014-9-3 下午3:38:21
	 * @author zhengxr
	 */
	public String getDownloadsPath(Context mContext) {
		String path = getRootPath(mContext) + "/downloads/";
		File file = new File(path);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * 
	 * @author LHY <br>
	 *         Description 获取图片缓存路径<br>
	 *         LastModified 2014-1-7 Content modify by dingcl <br>
	 */
	public String getPictureCachePath(Context mContext) {
		String path = getRootPath(mContext) + "/cache/";
		File file = new File(path);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * 获取图片的绝对路径
	 * 
	 * @param mContext
	 * @return 随机生成的图片路径
	 */
	public String getPicturePath(Context mContext) {
		String path = null;
		try {
			path = getPictureCachePath(mContext) + IdGeneratorUtil.Instance().NextLong();

			File file = new File(path);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getAudioCachePath
	 * @Description: 获取音频文件缓存目录
	 * @param @param mContext
	 * @param @return
	 * @return String
	 * @author lihy
	 */
	public String getAudioCachePath(Context mContext) {
		String path = getRootPath(mContext) + "/audio/";
		File file = new File(path);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * 
	 * @Title: getVideoCachePath
	 * @Description: 获取视频文件缓存目录
	 * @param @param mContext
	 * @param @return
	 * @return String
	 * @author lihy
	 */
	public String getVideoCachePath(Context mContext) {
		String path = getRootPath(mContext) + "/video/";
		File file = new File(path);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * 
	 * @Title: getAudioPath
	 * @Description: 随机生成音频文件路径
	 * @param @param mContext
	 * @param @return
	 * @return String
	 * @author lihy
	 */
	public String getAudioPath(Context mContext) {
		String path = null;
		try {
			path = getAudioCachePath(mContext) + "m_voice_" + IdGeneratorUtil.Instance().NextLong() ;
			File file = new File(path);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getVideoPath
	 * @Description: 随机生成视频文件路径
	 * @param @param mContext
	 * @param @return
	 * @return String
	 * @author lihy
	 */
	public String getVideoPath(Context mContext) {
		String path = null;
		try {
			path = getVideoCachePath(mContext) + IdGeneratorUtil.Instance().NextLong();
			File file = new File(path);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author Dingcl <br>
	 *         Description 获取聊天缩略图路径<br>
	 *         LastModified 2014-1-21 Content <br>
	 */
	public String getChatSmallPicPath(Context mContext) {
		String path = getRootPath(mContext) + "/smallpic/";
		File file = new File(path);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * 
	 * @Title: getJSPath
	 * @Description: 获取JS本地文件路径
	 * @param @param mContext
	 * @param @return
	 * @return String
	 * @author lihy
	 */
	public String getJSPath(Context mContext) {
		String path = getRootPath(mContext) + "/ionic";
		File file = new File(path);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}
}
