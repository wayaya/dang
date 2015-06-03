/**
 * Description: DigestLableDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月21日 下午7:31:04  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDigestLableDao;
import com.dangdang.digital.model.DigestLable;

/**
 * Description: 章节标签关联关系表Dao实现
 * All Rights Reserved.
 * @version 1.0  2015年1月21日 下午7:31:04  by 代鹏（daipeng@dangdang.com）创建
 */
@Repository
public class DigestLableDaoImpl extends BaseDaoImpl<DigestLable> implements IDigestLableDao {

	@Override
	public int insert(DigestLable record) {
		return this.insert("DigestLableMapper.insert", record);
	}

	@Override
	public int delete(Long id) {
		return this.delete("DigestLableMapper.deleteByPrimaryKey", id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> queryDigestIdsBySignId(Long signId) {
		return (List<Long>)getSqlSessionQueryTemplate().selectList("DigestLableMapper.queryDigestIdsBySignId", map("signId",signId));
	}
	
}
