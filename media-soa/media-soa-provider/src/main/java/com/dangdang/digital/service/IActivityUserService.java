package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ActivityUser;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.vo.ActivityVo;

/**
 * Description: 活动模块的用户表Service接口
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午2:17:48  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public interface IActivityUserService extends IBaseService<ActivityUser, Long>{
		

	/**
	 * Description:分享
	 * @Version1.0 2014年12月3日 上午10:12:36 by 周伟洋（zhouweiyang@dangdang.com）创建
	 */
	Map<String,String> share(ActivityVo activityVo);
	
	
	/**
	 * Description: 获取用户的活动用户信息（壕赏榜的信息，以及头像信息）
	 * @Version1.0 2014年12月3日 上午10:13:48 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @return
	 */
	Map<String, Object> selectActivityUserInfo(Long custId);
	
	/**
	 * Description: 膜拜
	 * @Version1.0 2014年12月3日 上午11:34:57 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @throws Exception 
	 */
	Map<String, String> worship(ActivityVo worshipVo) throws Exception;
	
	/**
	 * Description: 获取用户打赏的书的信息
	 * @Version1.0 2014年12月5日 下午7:16:12 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<Media> selectUserconsumeBooks(Long custId);
	
}
