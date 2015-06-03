package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.GoodArticleSign;

/**
 * Description: ICatetoryDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
public interface IGoodArticleSignDao extends IBaseDao<GoodArticleSign>{
	
	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<GoodArticleSign> getTreeByParentId(GoodArticleSign sign);
	
	/**添加分类树节点
	 * @param catetory
	 * @return
	 */
	public void insert(GoodArticleSign sign);
	
	/**修改树节点
	 * @param catetory
	 * @return
	 */
	public void update(GoodArticleSign sign);
	/**
	 * 删除树节点
	 * @param catetory
	 * @return
	 */
	public void delete(Integer id);
	
}