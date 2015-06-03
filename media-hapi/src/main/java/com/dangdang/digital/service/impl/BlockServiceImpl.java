package com.dangdang.digital.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBlockDao;
import com.dangdang.digital.model.Block;
import com.dangdang.digital.service.IBlockService;

/**
 * Description: 块Service 实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:23:06  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class BlockServiceImpl extends BaseServiceImpl<Block,Long> implements IBlockService{

	@Resource IBlockDao blockDao;
	
	
	@Override
	public IBaseDao<Block> getBaseDao() {
		return blockDao;
	}


	@Override
	public Block selectContentByCode(String code) {
		return blockDao.selectContentByCode(code);
	}

	@Override
	public List<Block> obtainBlockListByCodes(String codes) {
		return blockDao.obtainBlockListByCodes(codes);
	}
}
