/**
 * Description: MediaRelationServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午5:41:55  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaRelationDao;
import com.dangdang.digital.model.MediaRelation;
import com.dangdang.digital.service.IMediaRelationService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午5:41:55  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class MediaRelationServiceImpl extends BaseServiceImpl<MediaRelation, Long> implements
		IMediaRelationService {

	@Resource
	private IMediaRelationDao mediaRelationDao;
	@Override
	public IBaseDao<MediaRelation> getBaseDao() {
		return mediaRelationDao;
	}

}
