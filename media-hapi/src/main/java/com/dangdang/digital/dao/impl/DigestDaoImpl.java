/**
 * Description: DigestDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月21日 上午10:48:33  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDigestDao;
import com.dangdang.digital.model.Digest;

/**
 * Description: 精品章节Dao层实现
 * All Rights Reserved.
 * @version 1.0  2015年1月21日 上午10:48:33  by 代鹏（daipeng@dangdang.com）创建
 */
@Repository("digestDao")
public class DigestDaoImpl extends BaseDaoImpl<Digest> implements IDigestDao {

	@Override
	public int insert(Digest record) {
		return this.insert("DigestMapper.insert", record);
	}

	@Override
	public int update(Digest record) {
		return this.update("DigestMapper.updateByPrimaryKey", record);
	}

	@Override
	public int delete(Long id) {
		return this.delete("DigestMapper.deleteByPrimaryKey", id);
	}
	
	@Override
	public Digest findDigestById(Long id) {
		return this.selectOne("DigestMapper.selectByPrimaryKey", id);
	}

	@Override
	public List<Digest> queryDigestsByIds(Map<String, Object> paramObj) {
		return this.selectList("DigestMapper.queryDigestsByIds", paramObj);
	}

	@Override
	public List<Digest> queryDigestsByMood(Map<String, Object> paramObj) {
		return this.selectList("DigestMapper.queryDigestsByMood", paramObj);
	}

	@Override
	public List<Digest> queryDigestsForHomePage(Map<String, Object> paramObj) {
		return this.selectList("DigestMapper.queryDigestsForHomePage", paramObj);
	}

	@Override
	public Digest getDigestByShowEndDateLimitOne(Map<String, Object> paramObj) {
		return this.selectOne("DigestMapper.getDigestByShowEndDateLimitOne", paramObj);
	}

	@Override
	public Digest getDigestContentById(Long id) {
		return this.selectOne("DigestMapper.getDigestContentById", id);
	}

	@Override
	public List<Digest> queryDigestsByIdsAndSortByIds(Map<String, Object> paramObj) {
		return this.selectList("DigestMapper.queryDigestsByIdsAndSortByIds", paramObj);
	}

	@Override
	public List<Digest> queryDigestsBySignId(Map<String, Object> paramMap) {
		return this.selectList("DigestMapper.queryDigestsBySignId", paramMap);
	}

	@Override
	public List<Digest> queryDigestsByAuthorId(Map<String, Object> paramMap) {
		return this.selectList("DigestMapper.queryDigestsByAuthorId", paramMap);
	}

	@Override
	public List<Digest> queryDigestsByStoreUpAndCustId(Map<String, Object> paramMap) {
		return this.selectList("DigestMapper.queryDigestsByStoreUpAndCustId", paramMap);
	}

	@Override
	public List<Digest> queryDigestsHomePageNew(Map<String, Object> paramMap) {
		return this.selectList("DigestMapper.queryDigestsHomePageNew", paramMap);
	}

	@Override
	public List<Digest> queryDigestsHomePageOld(Map<String, Object> paramMap) {
		return this.selectList("DigestMapper.queryDigestsHomePageOld", paramMap);
	}

	@Override
	public Long getDigestIdtWithOne(Map<String, Object> paramMap) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne("DigestMapper.getDigestIdtWithOne", paramMap);
	}
	
}
