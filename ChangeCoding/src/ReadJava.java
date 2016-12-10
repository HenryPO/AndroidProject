import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class ReadJava {

	private final static String path = "/Users/lhy/Downloads/WebDemoT";
	private final static String finishPath = "/Users/lhy/Desktop/0086Code.txt";
	private final static String oldCoding = "UTF-8";
	private final static String newCoding = "GBK";

	public static void main(String[] args) {
		changeCoding(path);
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
			File file = new File(path);
			for (int i = 0; i < file.listFiles().length; i++) {
				File cFile = file.listFiles()[i];
				if(cFile.isDirectory()){
					changeCoding(cFile.getAbsolutePath());
				}else{
					if (cFile.getAbsolutePath().endsWith(".h")||cFile.getAbsolutePath().endsWith(".m")) {
						StringBuffer stringBuffer = new StringBuffer();
						BufferedReader br;
						
						br = new BufferedReader(new InputStreamReader(new FileInputStream(cFile), oldCoding));
						String line = null;
						try {
							while ((line = br.readLine()) != null) {
								stringBuffer.append(line + "\n");
							}
							br.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						write(stringBuffer, finishPath);
					}
				}
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
			// 声明字符输出流
			Writer out = null;
			// 通过子类实例化，表示可以追加
			out = new FileWriter(filename, true);
			// 写入数据
			out.write(buffer.toString());
			out.close();
			// OutputStreamWriter write = new OutputStreamWriter(new
			// FileOutputStream(filename), newCoding);
			// BufferedWriter writer = new BufferedWriter(write);
			// writer.write(buffer.toString());
//			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
