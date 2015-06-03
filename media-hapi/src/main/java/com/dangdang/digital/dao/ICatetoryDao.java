package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Catetory;

/**
 * Description: ICatetoryDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
public interface ICatetoryDao extends IBaseDao<Catetory>{
	
	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<Catetory> getTreeByParentId(Catetory catetory);
	
	/**添加分类树节点
	 * @param catetory
	 * @return
	 */
	public void insert(Catetory catetory);
	
	/**修改树节点
	 * @param catetory
	 * @return
	 */
	public void update(Catetory catetory);
	/**
	 * 删除树节点
	 * @param catetory
	 * @return
	 */
	public void delete(Integer id);
	
	public List<Catetory> getCatetoryByMediaId(Long mediaId);
}