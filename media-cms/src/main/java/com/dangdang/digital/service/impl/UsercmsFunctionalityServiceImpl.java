package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUsercmsFunctionalityDao;
import com.dangdang.digital.model.UsercmsFunctionality;
import com.dangdang.digital.service.IUsercmsFunctionalityService;
/**
 * 
* @Description: 用户功能关联
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:30:26
* @version V1.0
 */
@Service("usercmsFunctionalityService")
public class UsercmsFunctionalityServiceImpl  extends BaseServiceImpl<UsercmsFunctionality,Long>  implements
		IUsercmsFunctionalityService {
	@Resource
	IUsercmsFunctionalityDao usercmsFunctionalityDao;

	@Override
	public IBaseDao<UsercmsFunctionality> getBaseDao() {
		return usercmsFunctionalityDao;
	}

	@Override
	public int insertList(List<UsercmsFunctionality> list) {
		return usercmsFunctionalityDao.insertList(list);
	}
}
