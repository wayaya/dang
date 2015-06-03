package com.dangdang.digital.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IAnthologyDao;
import com.dangdang.digital.model.Anthology;

/**
 * Description: 精品文集dao接口实现
 * All Rights Reserved.
 * @version 1.0  2015年2月4日 下午5:07:58  by 代鹏（daipeng@dangdang.com）创建
 */
@Repository("anthologyDao")
public class AnthologyDaoImpl extends BaseDaoImpl<Anthology> implements IAnthologyDao {

	@Override
	public int insert(Anthology record) {
		return this.insert("AnthologyMapper.insert", record);
	}

	@Override
	public int update(Anthology record) {
		return this.update("AnthologyMapper.updateByPrimaryKey", record);
	}

	@Override
	public int delete(Long id) {
		return this.delete("AnthologyMapper.deleteByPrimaryKey", id);
	}

	@Override
	public Anthology findAnthologyById(Long id) {
		return this.selectOne("AnthologyMapper.selectByPrimaryKey", map("anthologyId", id));
	}

	@Override
	public Anthology findAnthologyByCustIdAndAnthologyName(Map<String, Object> paramObj) {
		return this.selectOne("AnthologyMapper.pageData", paramObj);
	}

	@Override
	public List<Anthology> queryAnthologyListByCustId(Long custId, String platform, Date lastDate, Integer pageSize) {
		return this.selectList("AnthologyMapper.queryAnthologyListByCustId", map("custId", custId, "platform", platform, "lastDate", lastDate, "pageSize", pageSize));
	}
	
}
