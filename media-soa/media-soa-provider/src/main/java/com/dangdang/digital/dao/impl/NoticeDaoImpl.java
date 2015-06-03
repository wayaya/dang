package com.dangdang.digital.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.INoticeDao;
import com.dangdang.digital.dao.impl.BaseDaoImpl;
import com.dangdang.digital.model.Notice;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月16日 下午6:15:26  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Repository
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements INoticeDao{

	@Override
	public List<Notice> getNoticeList(long lastRequestTime) {
		Map<String,Object> paramObj = new HashMap<String,Object>();
		paramObj.put("lastRequestTime", lastRequestTime);
		return selectList("NoticeMapper.getNoticeList", paramObj);
	}

}