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


public class ChangeCoding {
	private final static String path = "/Volumes/Work/AndroidWork/AndroidMac/Work3/SpaceBuilder_Debug/src";
	private final static String finishPath = "/Volumes/Work/AndroidWork/AndroidMac/Work3/SpaceBuilder_Debug_coding";
	private final static String oldCoding = "GBK";
	private final static String newCoding = "UTF-8";

	public static void main(String[] args) {
//		changeFileCoding(path);
		String path = "/Users/lhy/Downloads/SFC/";
		File file = new File(path);
		if (file.exists()) {
			for (String string : file.list()) {
				File file2 = new File(path + "/" + string);
				if (file2.exists()) {
					System.out.println(file2.getName());
					String name = PinYinTools.getFull(file2.getName()).toLowerCase().replace(" ", "");
					System.out.println(name);
					File file3 = new File(path + "/" + name);
					file2.renameTo(file3);
				}
			}
		}
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
			for (String string : file.list()) {
				File file2 = new File(path + "/" + string);
				if (file2.exists()) {
					if (file2.isDirectory()) {
						changeFileCoding(file2.getAbsolutePath());
					} else {
						changeCoding(file2.getAbsolutePath());
					}
				}
			}
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
	public static void changeCoding(String path) {
		try {
			if (path.endsWith(".java")) {
				File file = new File(path);
				StringBuffer stringBuffer = new StringBuffer();
				BufferedReader br;

				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), oldCoding));
				String line = null;
				try {
					while ((line = br.readLine()) != null) {
						stringBuffer.append(line + "\n");
					}
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				write(stringBuffer, finishPath + "/" + file.getName());
			}
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
