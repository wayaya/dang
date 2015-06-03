package com.dangdang.digital.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Hanzi_Pinyin {

	public static String hanziToPinyin(String hanzi)
			throws BadHanyuPinyinOutputFormatCombination {
		return hanziToPinyin(hanzi, " ");
	}

	/**
	 * 将汉字转换成拼音
	 * 
	 * @param hanzi
	 * @param separator
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	@SuppressWarnings("deprecation")
	public static String hanziToPinyin(String hanzi, String separator)
			throws BadHanyuPinyinOutputFormatCombination {
		// 创建汉语拼音处理类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		String pinyingStr = "";
		pinyingStr = PinyinHelper.toHanyuPinyinString(hanzi, defaultFormat,
				separator);
		return pinyingStr;
	}

	/**
	 * 取汉字的首字母
	 * 
	 * @param src
	 * @param isCapital
	 *            是否是大写
	 * @return
	 */
	public static char[] getHeadByChar(char src, boolean isCapital) {
		// 如果不是汉字直接返回
		if (src <= 128) {
			return new char[] { src };
		}
		// 获取所有的拼音
		String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);
		// 创建返回对象
		int polyphoneSize = pinyingStr.length;
		char[] headChars = new char[polyphoneSize];
		int i = 0;
		// 截取首字符
		for (String s : pinyingStr) {
			char headChar = s.charAt(0);
			// 首字母是否大写，默认是小写
			if (isCapital) {
				headChars[i] = Character.toUpperCase(headChar);
			} else {
				headChars[i] = headChar;
			}
			i++;
		}

		return headChars;
	}

	/**
	 * 取汉字串各汉字的首字母
	 * 
	 * @param str
	 * @param isCapital
	 *            是否是大写
	 * @return
	 */
	public static String getHeaderByChar(String str, boolean isCapital) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (!Pattern.matches("[\u4E00-\u9FA5]",
					String.valueOf(str.charAt(i)))) {
				stringBuffer.append(str.charAt(i));
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				StringBuffer sb = new StringBuffer();
				char[] repeat = getHeadByChar(str.charAt(i), isCapital);
				for (int j = 0; j < repeat.length; j++) {
					map.put(repeat[j] + "", null);
				}
				Iterator<String> it = map.keySet().iterator();
				while (it.hasNext()) {
					sb.append((String) it.next());
				}
				stringBuffer.append(sb.toString().charAt(0) + "");
			}
		}
		return stringBuffer.toString();

	}

	public static void main(String[] s) {
		System.out.println(getHeaderByChar("重大", true));
	}
}
