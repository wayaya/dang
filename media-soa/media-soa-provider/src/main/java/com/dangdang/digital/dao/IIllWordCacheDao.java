package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.IllWord;


/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月29日 上午11:02:22  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IIllWordCacheDao {
	List<IllWord> getAll();
	
	List<String> getAllIllWordContent();
}
