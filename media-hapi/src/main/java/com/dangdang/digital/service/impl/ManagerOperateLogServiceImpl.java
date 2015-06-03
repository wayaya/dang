/**
 * Description: ManagerOperateLogServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:28:38  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IManagerOperateLogDao;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.service.IManagerOperateLogService;

/**
 * Description: 后台用户操作日志实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:28:38  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class ManagerOperateLogServiceImpl extends BaseServiceImpl<ManagerOperateLog,Long> implements
		IManagerOperateLogService {

	@Resource
	IManagerOperateLogDao managerOperateLogDao;
	
	@Override
	public IBaseDao<ManagerOperateLog> getBaseDao() {
		return this.managerOperateLogDao;
	}

}
