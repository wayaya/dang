/**
 * Description: IUserAuthorityCacheDao.java
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 上午11:55:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.vo.UserAuthorityCacheVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 上午11:55:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserAuthorityCacheDao {

	public UserAuthorityCacheVo getUserAuthorityCacheVoByCustIdAndMediaId(Long custId,Long mediaId,String redisFlag);
	
	public UserAuthorityCacheVo setUserAuthorityCacheVo(UserAuthority userAuthority,UserAuthorityCacheVo oldUserAuthorityCacheVo);
	
	public UserAuthorityCacheVo setUserAuthorityCacheVo(UserAuthority userAuthority);
	
	public void deleteUserAuthorityCacheVo(Long custId,Long mediaId);

	/**
	 * 
	 * Description: 通过custId获取权限列表
	 * @Version1.0 2014年12月23日 下午3:39:26 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<UserAuthorityCacheVo> getUserAuthorityCacheVoByCustId(Long custId);
}
