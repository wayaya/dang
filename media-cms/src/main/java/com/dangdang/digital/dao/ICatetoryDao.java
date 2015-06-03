package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Catetory;

/**
 * Description: ICatetoryDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
public interface ICatetoryDao extends IBaseDao<Catetory>{
	
	
	/**
	 * 
	 * Description: 根据分类编号获取分类下所有子分类信息
	 * @Version1.0 2014年12月18日 下午3:29:39 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param parentCode
	 * @return
	 */
	public List<Catetory> getCatetoryByParentCode(String parentCode);

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
	
	public void delCatetoryByMediaId(Long mediaId);
	
	public void saveOrder(Map map);
	
	public Integer getMediaCountByCatetoryId(Catetory catetory);
}