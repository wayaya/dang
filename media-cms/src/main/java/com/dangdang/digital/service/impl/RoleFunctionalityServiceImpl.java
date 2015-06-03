package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IRoleFunctionalityDao;
import com.dangdang.digital.model.RoleFunctionality;
import com.dangdang.digital.model.UsercmsFunctionality;
import com.dangdang.digital.model.UsercmsRole;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IUsercmsFunctionalityService;
import com.dangdang.digital.service.IUsercmsRoleService;
/**
 * 
* @Description: 角色功能对应关系管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:27:04
* @version V1.0
 */
@Service
@Transactional
public class RoleFunctionalityServiceImpl extends BaseServiceImpl<RoleFunctionality,Long>  implements IRoleFunctionalityService {
	
	@Resource 
	IUsercmsRoleService usercmsRoleService;
	
	@Resource
	IUsercmsFunctionalityService usercmsFunctionalityService; 
	
	@Resource 
	IRoleFunctionalityDao roleFunctionalityDao;

	@Override
	public IBaseDao<RoleFunctionality> getBaseDao() {
		return roleFunctionalityDao;
	}
	
	@Override
	public int insertList(List<RoleFunctionality> list){
		return roleFunctionalityDao.insertList(list);
    }

	@Override
	public void addAndRemove(List<RoleFunctionality> listToAdd,
			List<Long> idsToRemove ) {
		//删除应该删除的funcIds关联
		if(idsToRemove!=null && idsToRemove.size()>0 ){
			this.deleteByIds(idsToRemove);
		}
		//添加应该添加的funcIds关联
		if(listToAdd!=null && listToAdd.size()> 0){
			this.insertList(listToAdd);
		}
	}
	
	@Override
	public void updateUserFunctionalities(List<Long> userIds, String creator){
		
		for( Long userId : userIds ){
			
			List<UsercmsRole> userRoles = usercmsRoleService.findListByParams("usercmsId", userId);
			
			Set<UsercmsFunctionality> editedUserFuncs = new HashSet<UsercmsFunctionality>();
			
			for(UsercmsRole userRole: userRoles){
				
				List<RoleFunctionality> roleFuncs = this.findListByParams("roleId", userRole.getRoleId());
				
				for(RoleFunctionality roleFunc: roleFuncs ){
					
					UsercmsFunctionality userFunc = new UsercmsFunctionality();
					userFunc.setUsercmsId(userId);
					userFunc.setFuncId(roleFunc.getFuncId());
					userFunc.setCreationDate(new Date());
					userFunc.setCreator(creator);
					editedUserFuncs.add(userFunc);
				}
			}
			
			List<UsercmsFunctionality> oldUserFuncs = usercmsFunctionalityService.findListByParams("usercmsId", userId);
			List<UsercmsFunctionality> oldClone = new ArrayList<UsercmsFunctionality>(oldUserFuncs);
			oldUserFuncs.removeAll(editedUserFuncs);

			List<Long> idsToRemove = new ArrayList<Long>();
			if( oldUserFuncs.size() > 0 ) {
				for(int i = 0; i < oldUserFuncs.size(); i++ ) {
					idsToRemove.add( oldUserFuncs.get(i).getUsercmsFunctionalityId() );
				}
			}
			if(idsToRemove.size()>0 ){
				usercmsFunctionalityService.deleteByIds(idsToRemove);
			}
			if(oldClone!=null && oldClone.size()>0 ){
				editedUserFuncs.removeAll(oldClone);
			}
			if(editedUserFuncs.size()>0){
				usercmsFunctionalityService.insertList(new ArrayList<UsercmsFunctionality>(editedUserFuncs));
			}
			
		}
	}
	
}
