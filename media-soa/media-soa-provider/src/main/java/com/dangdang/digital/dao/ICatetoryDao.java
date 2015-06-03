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
	
	/**
	 * 
	 * Description: 通过分类标识获取media分类信息
	 * @Version1.0 2014年12月15日 上午11:59:00 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code 分类标识
	 * @return
	 */
	public Catetory getCatetoryByCode(String code);
	/**
	 * 
	 * Description: 通过分类标识前缀获取该分类下所有所有分类信息(包含自身节点)
	 * @Version1.0 2014年12月15日 上午11:59:00 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param code 分类标识
	 * @return
	 */
	public List<Catetory> getCatetoryListByPathPrifix(String code);
}