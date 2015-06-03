package com.dangdang.digital.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.IIllegalWordApi;
import com.dangdang.digital.dao.IIllWordCacheDao;
import com.dangdang.digital.model.IllWord;
@Component("illegalWordApi")
public class IllegalWordApiImpl implements IIllegalWordApi {
	//敏感词
	@Resource
	private IIllWordCacheDao  illWordCacheDao;
	
	
	@Override
	public List<IllWord> getAllIllWord() {
		return illWordCacheDao.getAll();
	}

	@Override
	public List<String> getAllIllWordContent() {
		// TODO Auto-generated method stub
		return illWordCacheDao.getAllIllWordContent();
	}
	/**
	 * 
	 * Description: 判断内容是否不包含敏感词
	 * @Version1.0 2015年2月2日 上午10:45:15 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param content
	 * @return
	 */
	public Boolean contentsIllegalWords(String content){
		if(StringUtils.isBlank(content)){
			return new Boolean(false);
		}
		List<String> listIllWords = getAllIllWordContent();
		for(String word:listIllWords){
			if(content.indexOf(word)> -1){
				 return new Boolean(true);
			}
		}
		
		return new Boolean(false);
	}

}
