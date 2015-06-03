package com.dangdang.digital.service;


import java.util.List;

import com.dangdang.digital.model.StoreUp;


/**
 * 
 * Description: 收藏
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午2:38:38  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IStoreUpService extends IBaseService<StoreUp, Long> {
	
	/**
	 * 
	 * Description: 查询指定用户所有的收总数
	 * @Version1.0 2014年12月23日 下午2:57:58 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param custId	用户编号
	 * @param type		收藏类型,media,发现
	 * @return
	 */
	public int getTotalCount(Long custId,String type,String platform);
	
	/**
	 * 
	 * Description: 判断是否已收藏
	 * @Version1.0 2014年12月25日 上午11:22:41 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param custId
	 * @param targetId
	 * @param type media 书目,discover现
	 * @return
	 */
	public boolean isStoreUp(Long custId,Long targetId,String type);
	
	/**
	 * 
	 * Description: 查询指定用户的指定记录的收藏对象Id集合
	 * @Version1.0 2014年12月23日 下午2:59:10 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param offset    记录开始值
	 * @param count		查询条数
	 * @param custId	用户编号
	 * @param type		收藏类型,media,发现
	 * @return
	 */
	public List<Long> getStoreUpObjectIdListByCustId(int offset, int count, Long custId, String type, String platform);
	
	/**
	 * Description: 根据custId查询收藏的翻篇ids
	 * @Version1.0 2015年2月3日 上午11:00:26 by 代鹏（daipeng@dangdang.com）创建
	 * @param custId
	 * @param type
	 * @param platform
	 * @return
	 */
	public List<Long> getStoreUpDigestIdsByCustId(Long custId, String type, String platform);

	/**
	 * 	
	 * Description: 根据targetIds查询指定用户的指定记录的收藏对象Id集合
	 * @Version1.0 2015年2月7日 下午3:03:49 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param type
	 * @param platform
	 * @param targetIds
	 * @return
	 */
	public List<Long> getStoreUpObjectIdListByCustIdAndTargetIds(Long custId, String type, String platform,
			List<Long> targetIds);
}
