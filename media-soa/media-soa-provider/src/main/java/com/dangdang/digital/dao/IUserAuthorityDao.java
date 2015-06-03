/**
 * Description: IUserAuthorityDao.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:24:58  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.UserAuthority;

/**
 * Description: 用户阅读权限dao
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:24:58  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserAuthorityDao extends IBaseDao<UserAuthority> {

	public UserAuthority findWithDetailByCustIdAndMediaId(Long custId, Long mediaId);

	/**
	 * 
	 * Description: 通过用户Id获取用户权限列表
	 * @Version1.0 2014年12月23日 下午3:13:30 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<UserAuthority> findWithDetailByCustId(Long custId);
}
