package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IFunctionalityDao;
import com.dangdang.digital.model.Functionality;
import com.dangdang.digital.service.IFunctionalityService;
import com.dangdang.digital.service.IRoleFunctionalityService;
import com.dangdang.digital.service.IUsercmsFunctionalityService;
/**
 * 
* @Description: 功能管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:26:08
* @version V1.0
 */
@Service("functionalityService")
@Transactional
public class FunctionalityServiceImpl extends BaseServiceImpl<Functionality,Long>  implements IFunctionalityService {

	@Resource 
	IFunctionalityDao functionalityDao;
	
	@Resource 
	IRoleFunctionalityService roleFunctionalityService;
	
	@Resource 
	IUsercmsFunctionalityService usercmsFunctionalityService;

	@Override
	public IBaseDao<Functionality> getBaseDao() {
		return functionalityDao;
	}

	@Override
	public void deleteFunctionById(Long functionalityId) {
		
		this.deleteById(functionalityId);
//		//删除与之相关联的Role的关联记录
		roleFunctionalityService.deleteByByParams("funcId",functionalityId);
//		//删除与之相关联的User的关联记录
		usercmsFunctionalityService.deleteByByParams("funcId", functionalityId);
		
	}
	
}
