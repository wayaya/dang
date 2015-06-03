/**
 * Description: DigestLableServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午3:47:04  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDigestLableDao;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.service.IDigestLableService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午3:47:04  by 代鹏（daipeng@dangdang.com）创建
 */
@Service("digestLableService")
public class DigestLableServiceImpl extends BaseServiceImpl<DigestLable, Long> implements IDigestLableService {
	
	@Resource
	private IDigestLableDao digestLableDao;
	
	@Override
	public IBaseDao<DigestLable> getBaseDao() {
		return digestLableDao;
	}

	@Override
	public int insert(DigestLable record) {
		return digestLableDao.insert(record);
	}

	@Override
	public int delete(Long id) {
		return digestLableDao.delete(id);
	}

	@Override
	public List<Long> queryDigestIdsBySignId(Long signId) {
		return digestLableDao.queryDigestIdsBySignId(signId);
	}

}
