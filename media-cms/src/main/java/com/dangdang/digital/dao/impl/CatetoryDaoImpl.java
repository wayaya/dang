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

	
	public List<Catetory> getCatetoryByParentCode(String parentCode){
		Map<String,Object> parmaObj = new HashMap<String,Object>(2);
		parmaObj.put("code", parentCode);
		return this.selectList("CatetoryMapper.getTreeByParentCode", parmaObj);
		
	}

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

	@Override
	public void delCatetoryByMediaId(Long mediaId) {
		delete("CatetoryMapper.delCatetoryByMediaId", mediaId);
	}

	@Override
	public void saveOrder(Map map) {
		update("CatetoryMapper.updateOrder",map);
	}

	@Override
	public Integer getMediaCountByCatetoryId(Catetory catetory) {
		return (Integer)getSqlSessionTemplate().selectOne("CatetoryMapper.getMediaCountByCatetoryId", catetory);
	}
}