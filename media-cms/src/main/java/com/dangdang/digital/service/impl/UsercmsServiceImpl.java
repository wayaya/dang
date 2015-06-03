package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUsercmsDao;
import com.dangdang.digital.model.Role;
import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.model.UsercmsFunctionality;
import com.dangdang.digital.model.UsercmsRole;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IRoleService;
import com.dangdang.digital.service.IUsercmsFunctionalityService;
import com.dangdang.digital.service.IUsercmsRoleService;
import com.dangdang.digital.service.IUsercmsService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 
* @Description: 用户管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:33:38
* @version V1.0
 */
/**
 * 
* @Description: 用户管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:33:38
* @version V1.0
 */
@Service("usercmsService")
@Transactional
public class UsercmsServiceImpl extends BaseServiceImpl<Usercms,Long> implements IUsercmsService {
	@Resource
	IUsercmsDao usercmsDao;
	
	@Resource
	IRoleFunctionalityService roleFunctionalityService;
	
	@Resource
	IUsercmsFunctionalityService usercmsFunctionalityService;
	
	@Resource
	IUsercmsRoleService usercmsRoleService;
	
	@Resource
	IRoleService roleService;

	@Override
	public IBaseDao<Usercms> getBaseDao() {
		return usercmsDao;
	}

	@Override
	public void saveOrUpdateUsercms(Usercms usercms, List<Long> roleIds, String creator) {
		
		boolean isEdit = (usercms.getUsercmsId()!=null && usercms.getUsercmsId()!=0L);
		//管理员不和任何Role,functionality有关联
		
		usercms = this.findMasterById(usercms.getUsercmsId());
		if(usercms.getPreviledge() == 0){
			return;
		}
		
		List<UsercmsRole> currentRolelist = new ArrayList<UsercmsRole>();
		List<Role> roleList = roleService.findMasterByIds(roleIds);
		
		for(Role role: roleList){
			UsercmsRole ur = new UsercmsRole();
			ur.setRoleId(role.getRoleId());
			ur.setRoleName(role.getName());
			ur.setUsercmsId(usercms.getUsercmsId());
			ur.setCreationDate(new Date());
			ur.setCreator(creator);
			currentRolelist.add(ur);
		}
		
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(usercms.getUsercmsId());
		
		if(!isEdit){
			usercmsRoleService.insertList(currentRolelist);
		}else{
			
			List<UsercmsRole> existedRoleList = usercmsRoleService.findMasterListByParams("usercmsId",usercms.getUsercmsId());
			List<UsercmsRole> currentRoleListClone = new ArrayList<UsercmsRole>(currentRolelist);
			currentRolelist.removeAll(existedRoleList);
			existedRoleList.removeAll(currentRoleListClone);
			List<Long> idsToRemove = new ArrayList<Long>();
			for(UsercmsRole existedRole : existedRoleList){
				idsToRemove.add(existedRole.getUsercmsRoleId());
			}
			usercmsRoleService.addAndRemove(currentRolelist, idsToRemove);			
		}
	}

	@Override
	public void deleteUsercmsByIds(List<Long> idsToRemove) {
		this.deleteByIds(idsToRemove);
		for(Long usercmsId : idsToRemove){
			
			UsercmsRole urExample = new UsercmsRole();
			urExample.setUsercmsId(usercmsId);
			usercmsRoleService.deleteByParamsObjs(urExample);
			
			UsercmsFunctionality ufExample = new UsercmsFunctionality();
			ufExample.setUsercmsId(usercmsId);
			usercmsFunctionalityService.deleteByParamsObjs(ufExample);
		}
	}

	@Override
	public PageFinder<Usercms> findMasterPageFinderObjs(Usercms usercms,
			Query query, List<Long> userIds) {
		
		Map<String, Object> params = convertBeanToMap(usercms);
		if(userIds.size()>0){
			params.put("ids", userIds);
		}
		return super.findMasterPageFinderObjs(params, query) ;
	}

	
}
