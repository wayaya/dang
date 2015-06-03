package com.dangdang.digital.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IBlockDao;
import com.dangdang.digital.model.Block;

/**
 * Description: 块dao 实现类
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:23:06  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Repository
@SuppressWarnings("unchecked")
public class BlockDaoImpl extends BaseDaoImpl<Block> implements IBlockDao{

	@Override
	public Block selectContentByCode(String code) {
		return (Block) super.getSqlSessionQueryTemplate().selectOne("BlockMapper.selectContentByCode",code);
	}

	@Override
	public List<Block> obtainBlockListByCodes(String codes) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codes", codes);
		return (List<Block>) getSqlSessionQueryTemplate().selectList("BlockMapper.obtainBlockListByCodes", param);
	}
}
