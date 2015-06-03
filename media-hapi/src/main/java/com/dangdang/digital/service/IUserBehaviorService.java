package com.dangdang.digital.service;

import com.dangdang.digital.constant.BehaviorTypeEnum;
import com.dangdang.digital.model.UserBehavior;

/**
 * 
 * Description: 用户行为service All Rights Reserved.
 * 
 * @version 1.0 2015年1月30日 上午10:07:03 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IUserBehaviorService extends IBaseService<UserBehavior, Long> {

	
	/**
	 * 
	 * Description: 添加用户行为
	 * @Version1.0 2015年1月30日 下午1:43:34 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param behaviorType 行为类型
	 * @param mediaId 资源id
	 * @param saled 销售主体id
	 * @param times 次数，默认为1
	 * @param custId 用户id，可以为空
	 */
	public void addUserBehavior(BehaviorTypeEnum behaviorType, Long mediaId, Long saleId, Integer times, Long custId,
			String platform);
}
