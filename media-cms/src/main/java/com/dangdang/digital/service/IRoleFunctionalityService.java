package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.RoleFunctionality;
/**
 * 
* @Description: 角色功能对应关系管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:19:27
* @version V1.0
 */
public interface IRoleFunctionalityService extends IBaseService<RoleFunctionality,Long>{

	int insertList(List<RoleFunctionality> list);

	void addAndRemove(List<RoleFunctionality> listToAdd,
			List<Long> idsToRemove);

	void updateUserFunctionalities(List<Long> userIds, String creator);

}
