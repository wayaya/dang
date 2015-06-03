package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.dao.IGoodArticleSignDao;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.GoodArticleSign;

/**
 * Description: CatetoryDaoImpl
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午16:02:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
@Repository
public class GoodArticleSignDaoImpl extends BaseDaoImpl<GoodArticleSign> 
implements IGoodArticleSignDao{

	@Override
	public List<GoodArticleSign> getTreeByParentId(GoodArticleSign sign) {
		return this.selectList("GoodArticleSignMapper.getTreeByParentId", sign);
	}

	@Override
	public void insert(GoodArticleSign sign) {
		this.insert("GoodArticleSignMapper.insert",sign);
	}

	@Override
	public void update(GoodArticleSign sign) {
		this.update("GoodArticleSignMapper.updateByPrimaryKey",sign);
	}

	@Override
	public void delete(Integer id) {
		this.delete("GoodArticleSignMapper.deleteByPrimaryKey", id);
	}

}