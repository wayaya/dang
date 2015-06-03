package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IRoleDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Role;
import com.dangdang.digital.model.RoleFunctionality;
import com.dangdang.digital.model.UsercmsRole;
import com.dangdang.digital.service.IFunctionalityService;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IRoleService;
import com.dangdang.digital.service.IUsercmsRoleService;
/**
 * 
* @Description: 角色管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:29:01
* @version V1.0
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role,Long>  implements IRoleService {

	@Resource IRoleFunctionalityService roleFunctionalityService;
	
	@Resource IUsercmsRoleService usercmsRoleService;
	
	@Resource
	IRoleDao roleDao;

	@Override
	public IBaseDao<Role> getBaseDao() {
		return roleDao;
	}
	
	/*
	 * 此方法除了删除Role本身还会删除相关的关联数据，当有用户仍在使用ids里面的任何一个role的时候是不允许删除的
	 */
	@Override
	public void deleteRoleByIds(List<Long> ids) throws MediaException{
		
		//判断有没有用户在用此role，即查询user-role
		for(Long id :ids){
			List<UsercmsRole> userRoles = usercmsRoleService.findMasterListByParams("roleId", id);
			if(userRoles!=null && userRoles.size()>0){
				throw new MediaException("删除失败，仍有用户在使用此角色");
			}
		}
		
		//delete role
		this.deleteByIds(ids);
		
		//delete role的相关记录,即删除role-functionality
		List<Long> roleFunctionalityIds = new ArrayList<Long>(); 
		for(Long id :ids){
			
			List<RoleFunctionality> roleFuncs = roleFunctionalityService.findMasterListByParams("roleId", id);
			for(RoleFunctionality roleFunc:roleFuncs ){
				roleFunctionalityIds.add(roleFunc.getRoleFunctionalityId());
			}
		}
		
		if(roleFunctionalityIds.size()>0){
			roleFunctionalityService.deleteByIds(roleFunctionalityIds);
		}
	}
}
