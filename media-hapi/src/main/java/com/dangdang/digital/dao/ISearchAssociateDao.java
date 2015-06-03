package com.dangdang.digital.dao;

import com.dangdang.digital.model.SearchAssociate;

public interface ISearchAssociateDao extends IBaseDao<SearchAssociate> {
	
	/**
	 * Description: 保持搜索词
	 * @Version1.0 2014年12月27日 下午4:53:37 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param activityInfo
	 */
	public void insert(SearchAssociate searchAssociate);
	
	/**
	 * Description: 更新搜索词记录
	 * @Version1.0 2014年12月27日 下午4:54:19 by wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param activityInfo
	 */
	public void update(SearchAssociate searchAssociate);
}
