package com.dangdang.digital.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinYinUtils {

	static HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();

	static {
		// 无声调
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// 小写
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

	}

	/**
	 * 
	 * Description: 通过string获取汉语拼音
	 * 
	 * @Version1.0 2015年2月3日 下午1:53:54 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param str
	 * @return
	 */
	public static String getStringPinYin(String str) {
		try {
			StringBuilder sb = new StringBuilder();
			String tempPinyin = null;
			for (int i = 0; i < str.length(); ++i) {
				tempPinyin = getCharacterPinYin(str.charAt(i));
				sb.append(tempPinyin);
			}
			return sb.toString();
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 
	 * Description: 通过char获取汉语拼音
	 * 
	 * @Version1.0 2015年2月3日 下午1:54:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param c
	 * @return
	 */
	public static String getCharacterPinYin(char c) {
		try {
			String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
			if (pinyin == null)
				return String.valueOf(c);
			return pinyin[0];
		} catch (Exception e) {
			return String.valueOf(c);
		}

	}

}
