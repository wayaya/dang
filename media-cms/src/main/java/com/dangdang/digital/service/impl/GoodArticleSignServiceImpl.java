package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.dao.IGoodArticleSignDao;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.GoodArticleSign;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IGoodArticleSignService;

/**
 * Description: CatetoryDaoImpl
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午16:02:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
@Service
public class GoodArticleSignServiceImpl extends BaseServiceImpl<GoodArticleSign, Integer> implements IGoodArticleSignService{

	@Resource
	private IGoodArticleSignDao signDao;
	@Override
	public List<GoodArticleSign> getTreeByParentId(GoodArticleSign sign) {
		return signDao.getTreeByParentId(sign);
	}

	@Override
	public void insertOrUpdate(GoodArticleSign sign) {
		if(sign.getId() == null){
			this.signDao.insert(sign);
		}else{
			this.signDao.update(sign);
		}
	}

	@Override
	public void delete(Integer id) {
		signDao.delete(id);
	}

	@Override
	public IBaseDao<GoodArticleSign> getBaseDao() {
		// TODO Auto-generated method stub
		return signDao;
	}

}