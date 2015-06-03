/**
 * Description: PropServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午11:03:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IPropDao;
import com.dangdang.digital.model.Prop;
import com.dangdang.digital.service.IPropService;

/**
 * Description: 道具实体service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午11:03:03  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class PropServiceImpl extends BaseServiceImpl<Prop,Integer> implements IPropService {
	
	@Resource
	IPropDao propDao;
	
	@Override
	public IBaseDao<Prop> getBaseDao() {
		return this.propDao;
	}

}
