package com.dangdang.digital.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IAnnouncementsCategoryDao;
import com.dangdang.digital.dao.IAnnouncementsContentDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.service.IAnnouncementsCategoryService;

/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月5日 下午2:17:17  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Service
public class AnnouncementsCategoryServiceImpl extends BaseServiceImpl<AnnouncementsCategory, Long> implements IAnnouncementsCategoryService {

	@Resource
	IAnnouncementsCategoryDao dao;
	
	public IBaseDao<AnnouncementsCategory> getBaseDao() {
		return dao;
	}
	
	
	public List<AnnouncementsCategory> getAll() {
		// TODO Auto-generated method stub
		return dao.selectList("AnnouncementsCategoryMapper.getAll");
	}

	@Transactional
	public void deleteAnnouncementsCategory(AnnouncementsCategory ac) {
		//删除公告内容
		dao.delete("AnnouncementsCategoryMapper.deleteContent", ac);
		this.deleteById(ac.getCategoryId());
	}
	
}
