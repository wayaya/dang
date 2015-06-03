package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IRankConsToBookDao;
import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.service.IRankConsToBookService;

/**
 * Description: 壕赏榜单service实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月15日 下午3:11:11  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Service
public class RankConsToBookServiceImpl  extends BaseServiceImpl<RankConsToBook,Long> implements IRankConsToBookService {
	
	@Resource
	IRankConsToBookDao rankConsToBookDao; 
	
	@Override
	public IBaseDao<RankConsToBook> getBaseDao() {
		return this.rankConsToBookDao;
	}
}
