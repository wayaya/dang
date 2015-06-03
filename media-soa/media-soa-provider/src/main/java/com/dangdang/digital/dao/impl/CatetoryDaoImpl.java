package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.model.Catetory;

/**
 * Description: CatetoryDaoImpl
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午16:02:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
@Repository
public class CatetoryDaoImpl extends BaseDaoImpl<Catetory> implements ICatetoryDao{

	@Override
	public List<Catetory> getTreeByParentId(Catetory catetory) {
		return this.selectList("CatetoryMapper.getTreeByParentId", catetory);
	}

	@Override
	public void insert(Catetory catetory) {
		this.insert("CatetoryMapper.insert",catetory);
	}

	@Override
	public void update(Catetory catetory) {
		this.update("CatetoryMapper.updateByPrimaryKey",catetory);
	}

	@Override
	public void delete(Integer id) {
		this.delete("CatetoryMapper.deleteByPrimaryKey", id);
	}

	@Override
	public List<Catetory> getCatetoryByMediaId(Long mediaId) {
		return selectList("CatetoryMapper.getCatetoryByMediaId", mediaId);
	}
	
	/**
	 * 
	 * Description: 通过分类标识获取media分类信息
	 * @Version1.0 2014年12月15日 上午11:59:00 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code 分类标识
	 * @return
	 */
	public Catetory getCatetoryByCode(String code){
		Map<String,Object> parmaObj = new HashMap<String,Object>(2);
		parmaObj.put("code", code);
		return this.selectOne("CatetoryMapper.getCatetoryByCode", parmaObj);
	}

	@Override
	public List<Catetory> getCatetoryListByPathPrifix(String code) {
		Map<String,Object> parmaObj = new HashMap<String,Object>(2);
		parmaObj.put("pathPrefix", code);
		return this.selectList("CatetoryMapper.getSubCatetoryByParentPath", parmaObj);
	}
	

}