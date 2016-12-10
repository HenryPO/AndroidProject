

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinTools {
	/**
	 * ��ȡ����ĸƴ��
	 * 
	 * @param str
	 *            ԭ�ַ���
	 * @return
	 */
	public static String getFirst(String str) {
		if (str.isEmpty())
			return "";
		String result = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				result += pinyinArray[0].charAt(0);
			} else {
				result += word;
			}
		}
		return result;
	}

	/**
	 * ��ȡȫ��ƴ��
	 * 
	 * @param src
	 *            ԭ�ַ���
	 * @return
	 */
	public static String getFull(String src) {

		char[] srcChar = src.toCharArray();
		String[] srcArry = new String[srcChar.length];
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

		// ���ø�ʽ
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		String result = "";
		try {
			for (int i = 0; i < srcChar.length; i++) {
				// �ж��Ƿ�Ϊ�����ַ�
				if (Character.toString(srcChar[i])
						.matches("[\\u4E00-\\u9FA5]+")) {
					srcArry = PinyinHelper.toHanyuPinyinStringArray(srcChar[i],
							format);
					result += srcArry[0];
				} else
					result += Character.toString(srcChar[i]);
			}
			return result;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return result;
	}
}
