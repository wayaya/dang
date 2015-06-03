package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.UsercmsRole;
/**
 * 
* @Description: 用户角色对应关系管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:20:31
* @version V1.0
 */
public interface IUsercmsRoleService extends IBaseService<UsercmsRole,Long>{

	public int insertList(List<UsercmsRole> list);

	public void addAndRemove(List<UsercmsRole> listToAdd, List<Long> idsToRemove );

}
