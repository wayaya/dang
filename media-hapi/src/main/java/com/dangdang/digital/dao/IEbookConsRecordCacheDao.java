package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.vo.ReturnEbookConsVo;

/**
 * Description: 电子书金币消费壕记录表的cache  dao接口
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:03:29  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IEbookConsRecordCacheDao{
	
	/**
	 * Description: 查询，打赏该书的用户列表
	 * @Version1.0 2014年12月15日 下午7:30:14 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId
	 * @param start
	 * @param count
	 * @return
	 */
	List<ReturnEbookConsVo> getEbookRewardedUsersCache(Long mediaId,String channel);
	
	
	/**
	 * Description: 打赏该书的用户列表放入到cache中
	 * @Version1.0 2014年12月25日 下午4:07:53 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param mediaId
	 * @param channel
	 * @param count
	 */
	void setEbookRewardedUsersCache(Long mediaId,String channel,List<ReturnEbookConsVo>list)throws Exception ;
	
	/**
	 * Description: 删除cache中的打赏该书的用户列表 
	 * @Version1.0 2014年12月25日 上午11:56:31 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param code
	 */
	void deleteEbookRewardedUsersCache(Long mediaId,String channel);
	
	
	/**
	 * Description: 获取用户打赏的书的id cache 列表
	 * @Version1.0 2014年12月6日 上午11:15:30 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<Long> getUserRewardBooksIdsCache(Long custId);
	
	/**
	 * Description: 存入用户打赏的书的id 列表  到cache
	 * @Version1.0 2014年12月26日 下午5:55:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @param mediaIds
	 */
	void setUserRewardBooksIdsCache(Long custId,List<Long>mediaIds);
	
	/**
	 * Description: 删除  存入用户打赏的书的id 列表的cache
	 * @Version1.0 2014年12月26日 下午5:55:32 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 */
	void deleteUserRewardBooksIdsCache(Long custId);
	
}
