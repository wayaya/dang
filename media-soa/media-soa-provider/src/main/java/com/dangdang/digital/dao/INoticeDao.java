package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Notice;

/**
 * 
 * Description: 公告
 * All Rights Reserved.
 * @version 1.0  2015年1月16日 下午6:15:44  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface INoticeDao extends IBaseDao<Notice> {
	
	public List<Notice> getNoticeList(long lastRequestTime);
}