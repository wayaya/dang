package com.dangdang.digital.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 书籍工具类
 * All Rights Reserved.
 * @version 1.0  2015年3月25日 下午4:34:12  by 代鹏（daipeng@dangdang.com）创建
 */
public class BookUtil {
	
	/**
	 * 根据单品id判断是否电子书
	 * productId电子书范围：1900000000-2000000000
	 * @param productId
	 * @return
	 */
	public static boolean isEbook(String productId){
		try{
			String regex = "19[0-9]{8}";
			Pattern p = Pattern.compile(regex); 
			Matcher m = p.matcher(productId);
			return m.matches();
		}catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * 过滤书名中“(电子书)”.
	 * @param bookName
	 * @return
	 */
	public static String filterEbookForBookName(String bookName){
		if(bookName == null || bookName.length() == 0){
			return "";
		}
		int indexNum = bookName.lastIndexOf("(电子书)");
		if (indexNum >= 0) {
			bookName = bookName.substring(0, indexNum);
		}
		return bookName;
	}
	
	
	/**
	 * 过滤书名中小括号以及括号中的内容
	 * @param inputString
	 * @return
	 */
	public static String filterParenthesesForBookName(String inputString){
		if(inputString == null || inputString.length() == 0){
			return "";
		}
		//过滤英文状态下括号
		String regex_en = "\\([^()]*\\)";
		Pattern p_en = Pattern.compile(regex_en, Pattern.CASE_INSENSITIVE);
		Matcher  m_en = p_en.matcher(inputString);
		inputString = m_en.replaceAll("");
		
		//过滤中文状态下括号
		String regex_cn = "\\（[^（）]*\\）";
		Pattern p_cn = Pattern.compile(regex_cn, Pattern.CASE_INSENSITIVE);
		Matcher  m_cn = p_cn.matcher(inputString);
		inputString = m_cn.replaceAll("");
		return inputString;
	}
	
	
	/**
	 * 过滤内容中字母
	 * @param inputString
	 * @return
	 */
	public static String filterBookReviewContentForAlphabet(String inputString){
		if(inputString == null || inputString.length() == 0){
			return "";
		}
		inputString = inputString.replaceAll("[(A-Za-z)]", "");
		return inputString;
	}
	
	/**
	 * 过滤除了汉字、数字、字母之外所有字符
	 * @param inputString
	 * @return
	 */
	public static String reservedCharacters(String inputString){
		if(inputString == null || inputString.length() == 0){
			return "";
		}
		String reg = "[^\\u4e00-\\u9fa5A-Za-z0-9]";
        return inputString.replaceAll(reg, "");
	}
	
}
