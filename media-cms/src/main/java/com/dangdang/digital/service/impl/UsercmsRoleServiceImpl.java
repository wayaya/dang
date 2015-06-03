package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUsercmsRoleDao;
import com.dangdang.digital.model.RoleFunctionality;
import com.dangdang.digital.model.UsercmsRole;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IUsercmsRoleService;
/**
 * 
* @Description: 用户角色对应关系
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:31:52
* @version V1.0
 */
@Service
@Transactional
public class UsercmsRoleServiceImpl extends BaseServiceImpl<UsercmsRole,Long> implements IUsercmsRoleService {

	@Resource
	IUsercmsRoleDao usercmsRoleDao;
	
	@Resource
	IRoleFunctionalityService roleFunctionalityService;

	@Override
	public IBaseDao<UsercmsRole> getBaseDao() {
		return usercmsRoleDao;
	}

	@Override
	public int insertList(List<UsercmsRole> list) {
		return usercmsRoleDao.insertList(list);
	}
	
	@Override
	public void addAndRemove(List<UsercmsRole> listToAdd,
			List<Long> idsToRemove ) {
		
		boolean needUpdateUsers = false;
		
		//删除应该删除的user-role关联
		if(idsToRemove!=null && idsToRemove.size()>0 ){
			this.deleteByIds(idsToRemove);
		}
		//添加应该添加的user-role关联
		if(listToAdd!=null && listToAdd.size()> 0){
			this.insertList(listToAdd);
		}
		
	}
	
}
