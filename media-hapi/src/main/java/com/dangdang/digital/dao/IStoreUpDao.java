package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.StoreUp;


public interface IStoreUpDao extends IBaseDao<StoreUp> {
	
	/**
	 * 
	 * Description: 查询指定用户的指定记录的收藏对象Id集合
	 * @Version1.0 2014年12月23日 下午2:59:10 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	
	int getTotalCount(Map<String,Object> paramObj);
	
	/**
	 * 
	 * Description: 查询指定用户的指定记录的收藏对象Id集合
	 * @Version1.0 2014年12月23日 下午2:59:10 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @return
	 */
	public List<Long> getStoreUpObjectIdListByCustId(Map<String,Object> paramObj);

	/**
	 * 
	 * Description: 根据targetIds查询指定用户的指定记录的收藏对象Id集合
	 * 
	 * @Version1.0 2015年2月7日 下午3:01:28 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	public List<Long> getStoreUpObjectIdListByCustIdAndTargetIds(Map<String, Object> paramObj);
	/**
	 * Description: 根据custId查询收藏的翻篇ids
	 * @Version1.0 2015年2月3日 上午11:58:05 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	public List<Long> getStoreUpDigestIdsByCustId(Map<String,Object> paramObj);
	
}