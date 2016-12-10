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

public class ChangeDimens {
	private final static String rootPath = "D:\\Android Studio\\Work\\Java\\ChangeCoding\\";
	private final static String path = rootPath+"dimens.xml";
	
	private final static String xhdpi_path = rootPath+"values-xhdpi\\dimens.xml";
	private final static String hdpi_path = rootPath+"values-hdpi\\dimens.xml";
	private final static String mdpi_path = rootPath+"values-mdpi\\dimens.xml";

	private final static String xhpdi_nomal_path = rootPath+"values-xhdpi-480x320\\dimens.xml";
	private final static String xhpdi_large_path = rootPath+"values-xhdpi-800x480\\dimens.xml";
	private final static String xhpdi_xlarge_path = rootPath+"values-xhdpi-960x540\\dimens.xml";

	private final static String hpdi_nomal_path = rootPath+"values-hdpi-480x320\\dimens.xml";
	private final static String hpdi_large_path = rootPath+"values-hdpi-800x480\\dimens.xml";
	private final static String hpdi_xlarge_path = rootPath+"values-hdpi-960x540\\dimens.xml";

	private final static String mpdi_nomal_path = rootPath+"values-mdpi-480x320\\dimens.xml";
	private final static String mpdi_large_path = rootPath+"values-mdpi-800x480\\dimens.xml";
	private final static String mpdi_xlarge_path = rootPath+"values-mdpi-960x540\\dimens.xml";
	
	
	/**
	 * 不规则尺寸
	 */
	private final static String mpdi_xxlarge_path = rootPath+"values-mdpi-960x540\\dimens.xml";
	private final static String mpdi_xxxlarge_path = rootPath+"values-mdpi-1024x600\\dimens.xml";
	
	private final static String lpdi_path = rootPath+"values-ldpi\\dimens.xml";
	private final static String lpdi_large_path = rootPath+"values-ldpi-800x480\\dimens.xml";
	private final static String oldCoding = "GBK";
	private final static String newCoding = "UTF-8";

	public static void main(String[] args) {
		changeFileCoding(path);
	}

	/**
	 * 
	 * @Description: 开始遍历文件改变编码
	 * @param path
	 *            void
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-25 下午2:11:16
	 */
	public static void changeFileCoding(String path) {
		File file = new File(path);
		if (file.exists()) {
			changeCoding(path, xhdpi_path, 2, 2);
			changeCoding(path, hdpi_path, 2, 1.5f);
			changeCoding(path, mdpi_path, 2, 1);
			
			changeCoding(path, xhpdi_nomal_path, 2,4);
			changeCoding(path, xhpdi_large_path, 2, 3);
			changeCoding(path, xhpdi_xlarge_path, 2, 2);
			
			changeCoding(path, hpdi_nomal_path, 2, 3);
			changeCoding(path, hpdi_large_path, 2, 2.25f);
			changeCoding(path, hpdi_xlarge_path, 2, 1.5f);
			
			changeCoding(path, mpdi_nomal_path, 2, 2);
			changeCoding(path, mpdi_large_path, 2, 1.5f);
			changeCoding(path, mpdi_xlarge_path, 2, 1);
			
			
//			changeCoding(path, lpdi_path, 1, 0.5f);
//			changeCoding(path, lpdi_large_path, 1, 0.75f);

//			changeCoding(path, xhpdi_nomal_path, 2,4);
//			changeCoding(path, xhpdi_large_path, 2, 3);
//			changeCoding(path, xhpdi_xlarge_path, 2, 2);
//			
//			changeCoding(path, hpdi_nomal_path, 2, 3);
//			changeCoding(path, hpdi_large_path, 2, 2.25f);
//			changeCoding(path, hpdi_xlarge_path, 2, 1.5f);
//			
//			changeCoding(path, mpdi_nomal_path, 2, 2);
//			changeCoding(path, mpdi_large_path, 2, 1.5f);
//			changeCoding(path, mpdi_xlarge_path, 2, 1);
//			changeCoding(path, mpdi_xxlarge_path, 2, 1.25f);
//			changeCoding(path, mpdi_xxxlarge_path, 2, 1f);
			
		}
	}

	/**
	 * 
	 * @Description:改变编码
	 * @param path
	 *            void
	 * @exception:
	 * @author: lihy
	 * @time:2015-2-25 下午2:10:35
	 */
	public static void changeCoding(String path, String path2, float oldIndex, float newIndexs) {
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
							float newIndex = index * oldIndex / newIndexs;
							if (newIndex <= 1) {
								newIndex = index;
							}
							line = line.replace(oldNumber, (((int) newIndex) == 0 ? 0.5 : (int) newIndex) + "sp");
						} else {
							String number = oldNumber.replace("dp", "");
							float index = Float.parseFloat(number);
							float newIndex = index * oldIndex / newIndexs;
							if (newIndex <= 1) {
								newIndex = index;
							}
							line = line.replace(oldNumber, (((int) newIndex) == 0 ? 0.5 : (int) newIndex) + "dp");
						}
					}

					stringBuffer.append(line + "\n");
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
	public static void write(StringBuffer buffer, String path) {
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
