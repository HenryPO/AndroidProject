package com.henry.coding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChangeDimens {
	public final static String rootPath = "D:\\Android Studio\\Work\\git\\AndroidProject\\ChangeCoding\\";
	public final static String path = rootPath + "dimens.xml";
	private final static String newCoding = "UTF-8";

	public static DimensConfigBean ldpiDimensConfigBean;
	public static DimensConfigBean mdpiDimensConfigBean;
	public static DimensConfigBean hdpiDimensConfigBean;
	public static DimensConfigBean xhdpiDimensConfigBean;
	public static DimensConfigBean xxhdpiDimensConfigBean;
	public static DimensConfigBean xxxhdpiDimensConfigBean;
	static {
		ldpiDimensConfigBean = new DimensConfigBean("ldpi", 240, 320, 0.75f, false);
		mdpiDimensConfigBean = new DimensConfigBean("mdpi", 320, 480, 1f, false);
		hdpiDimensConfigBean = new DimensConfigBean("hdpi", 480, 800, 1.5f, false);
		xhdpiDimensConfigBean = new DimensConfigBean("xhdpi", 720, 1280, 2f, false);
		xxhdpiDimensConfigBean = new DimensConfigBean("xxhdpi", 1080, 1920, 3f, false);
		xxxhdpiDimensConfigBean = new DimensConfigBean("xxxhdpi", 1440, 2560, 4f, false);

	}

	public DimensConfigBean rootDimensConfigBean;

	public static void main(String[] args) {
		ChangeDimens bean = new ChangeDimens();
		bean.rootDimensConfigBean = xhdpiDimensConfigBean;
		// 通用尺寸粗适配
		bean.makeDimens(ldpiDimensConfigBean);
		bean.makeDimens(mdpiDimensConfigBean);
		bean.makeDimens(hdpiDimensConfigBean);
		bean.makeDimens(xhdpiDimensConfigBean);
		bean.makeDimens(xxhdpiDimensConfigBean);
		bean.makeDimens(xxxhdpiDimensConfigBean);


		// 小米平板2
		bean.makeDimens("xhdpi", 1536, 2048, 2, true);
		//pos机
		bean.makeDimens("mdpi", 768, 1024, 1f, true);
		bean.makeDimens("hdpi", 1080, 1920, 1.5f, true);

	}

	public void makeDimens(String dpiName, int width, int height, float weight, boolean isShowDetailName) {
		DimensConfigBean toCongfigBean = new DimensConfigBean(dpiName, width, height, weight, isShowDetailName);
		makeDimens(toCongfigBean);
	}

	public void makeDimens(DimensConfigBean toCongfigBean) {
		String fileName = "";
		if (toCongfigBean.isShowDetailName()) {
			fileName = "-" + toCongfigBean.getHeight() + "x" + toCongfigBean.getWidth();
		}
		String toPath = rootPath + "values-" + toCongfigBean.getDpiName() + fileName + "\\dimens.xml";
		float oldIndex = rootDimensConfigBean.getWeight();
		float newIndexs = toCongfigBean.getWeight();
		float offset = (float) toCongfigBean.getWidth() / (float) rootDimensConfigBean.getWidth();
		changeCoding(path, toPath, oldIndex, newIndexs, offset);
		// String testFilePath = toPath = rootPath + "values-" +
		// toCongfigBean.getDpiName() + fileName + "\\strings.xml";
		// test(testFilePath, toCongfigBean.getDpiName() + fileName);
	}

	/**
	 * 改变编码
	 * 
	 * @param path
	 * @param path2
	 * @param oldDpi
	 *            旧dpi
	 * @param newDpi
	 *            新dpi
	 * @param offset
	 *            now/old 屏宽比
	 */
	public void changeCoding(String path, String path2, float oldDpi, float newDpi, float offset) {
		try {
			File file = new File(path);
			StringBuffer stringBuffer = new StringBuffer();
			BufferedReader br;

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), newCoding));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if (line.endsWith("</dimen>")) {
						String oldNumber = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
						if (oldNumber.endsWith("sp")) {
							String number = oldNumber.replace("sp", "");
							float index = Float.parseFloat(number);
							float newIndex = index * oldDpi * offset / newDpi;
							if (newIndex <= 1) {
								newIndex = index;
							}
							line = line.replace(oldNumber, (((int) newIndex) == 0 ? 0.5 : (int) newIndex) + "sp");
						} else {
							String number = oldNumber.replace("dp", "");
							float index = Float.parseFloat(number);
							float newIndex = index * oldDpi * offset / newDpi;
							if (newIndex <= 1) {
								newIndex = index;
							}
							line = line.replace(oldNumber, (((int) newIndex) == 0 ? 0.5 : (int) newIndex) + "dp");
						}
					}

					stringBuffer.append(line + "\n");
					if (line.endsWith("<resources>")) {
						stringBuffer.append("<string name=\"test_name\">"
								+ path2.substring(path2.lastIndexOf("values"),path2.lastIndexOf("\\")) + "</string>" + "\n");
					}

				}

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			write(stringBuffer, path2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void test(String path2, String fileName) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
		stringBuffer.append("<resources>" + "\n");
		stringBuffer.append("<string name=\"test_name\">" + fileName + "</string>" + "\n");
		stringBuffer.append("</resources>" + "\n");

		write(stringBuffer, path2);
	}

	/**
	 * 
	 * @Description: 写入文件
	 * @param buffer
	 * @param path
	 *            void
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-25 下午2:10:53
	 */
	public void write(StringBuffer buffer, String path) {
		File filename = new File(path);
		if (!filename.getParentFile().exists()) {
			filename.getParentFile().mkdirs();
		}
		if (!filename.exists()) {
			try {
				filename.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filename), newCoding);
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
