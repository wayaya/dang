/**
 * Description: IUserMonthlyDao.java
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:21:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.vo.UserMonthlyCacheVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:21:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserMonthlyDao extends IBaseDao<UserMonthly>{

	/**
	 * 
	 * Description: 查询当前有效的用户包月信息
	 * @Version1.0 2014年12月15日 下午7:41:36 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<UserMonthly> findUserMonthlyNowByCustId(Long custId);
	
	public UserMonthlyCacheVo findUserMonthlyCacheVoByCustId(Long custId);
	
	public int updateUserMonthly(Map<String, Object> map);
	
	public int insertUserMonthly(Map<String, Object> map);
	
	public UserMonthly findMasterUserMonthly(Map<String, Object> map);
}
