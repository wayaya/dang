/**
 * Description: UserMonthlyDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:21:54  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.dao.IUserMonthlyDao;
import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.vo.UserMonthlyCacheVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:21:54  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class UserMonthlyDaoImpl extends BaseDaoImpl<UserMonthly> implements IUserMonthlyDao {

	@Override
	public List<UserMonthly> findUserMonthlyNowByCustId(Long custId) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("custId", custId);
		parameter.put("withinPeriodValidity", "1");
		return this.selectMasterList("UserMonthlyMapper.getAll", parameter);
	}

	@Override
	public UserMonthlyCacheVo findUserMonthlyCacheVoByCustId(Long custId) {
		List<UserMonthly> userMonthlys = this.findUserMonthlyNowByCustId(custId);
		if(CollectionUtils.isEmpty(userMonthlys)){
			return null;
		}
		UserMonthlyCacheVo userMonthlyCacheVo = new UserMonthlyCacheVo();
		for(UserMonthly userMonthly : userMonthlys){
			userMonthlyCacheVo.getUserMonthlys().put(userMonthly.getMonthlyPaymentRelation(), userMonthly);
		}
		return userMonthlyCacheVo;
	}

	@Override
	public int updateUserMonthly(Map<String, Object> map) {
		return this.update("UserMonthlyMapper.updateUserMonthly", map);
	}

	@Override
	public int insertUserMonthly(Map<String, Object> map) {
		return this.update("UserMonthlyMapper.insertUserMonthly", map);
	}

	@Override
	public UserMonthly findMasterUserMonthly(Map<String, Object> map) {
		return this.selectMasterOne("UserMonthlyMapper.findMasterUserMonthly", map);
	}

}
