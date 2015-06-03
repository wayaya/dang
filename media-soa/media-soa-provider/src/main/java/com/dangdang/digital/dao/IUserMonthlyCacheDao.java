/**
 * Description: IUserMonthlyCacheDao.java
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:08:24  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao;

import com.dangdang.digital.vo.UserMonthlyCacheVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午7:08:24  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserMonthlyCacheDao {

	public UserMonthlyCacheVo getUserMonthlyCacheVoByCustId(Long custId,String redisFlag);
	
	public UserMonthlyCacheVo updateUserMonthlyCacheVo(UserMonthlyCacheVo userMonthlyCacheVo,Long custId);
	
	public void deleteUserMonthlyCacheVo(Long custId);
}
