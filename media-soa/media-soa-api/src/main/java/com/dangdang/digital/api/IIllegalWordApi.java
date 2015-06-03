package com.dangdang.digital.api;

import java.util.List;

import com.dangdang.digital.model.IllWord;

public interface IIllegalWordApi {
	/**
	 * 
	 * Description: 判断内容是否不包含敏感词
	 * @Version1.0 2015年2月2日 上午10:45:15 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param content
	 * @return
	 */
	public Boolean contentsIllegalWords(String content);
	/**
	 * 
	 * Description: 获取所有的敏感词实体
	 * @Version1.0 2015年1月29日 上午10:48:41 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	public List<IllWord> getAllIllWord();
	
	
	/**
	 * 
	 * Description: 获取所有的敏感词内容
	 * @Version1.0 2015年1月29日 上午10:48:41 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	
	public List<String> getAllIllWordContent();
	
}
