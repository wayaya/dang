package com.dangdang.digital.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IAnnouncementsContentDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.AnnouncementsContent;
import com.dangdang.digital.service.IAnnouncementsContentService;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月5日 下午5:32:16  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Service
public class AnnouncementsContentServiceImpl extends BaseServiceImpl<AnnouncementsContent, Long> implements IAnnouncementsContentService {
	
	@Resource
	IAnnouncementsContentDao dao;
	
	public IBaseDao<AnnouncementsContent> getBaseDao() {
		return dao;
	}
	
	@Override
	public List<AnnouncementsContent> getAll() {
		return dao.selectList("AnnouncementsContentMapper.getAll");
	}
	
}
