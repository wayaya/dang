package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDigestDao;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Repository
public class DigestDaoImpl extends BaseDaoImpl<Digest> implements IDigestDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSign() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) this.getSqlSessionQueryTemplate()
				.selectList("DigestMapper.getSign");
		// return this.selectList("DigestMapper.getSign");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSignByDigestId() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) this.getSqlSessionQueryTemplate()
				.selectList("DigestMapper.getSignByDigestId");
	}

	public PageFinder<Digest> findDigestByAuthorId(Query query, Long authorId,
			Integer isShow) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorId", authorId);
		if (isShow != null) {
			params.put("isShow", isShow);
		}
		int count = (Integer) getSqlSessionQueryTemplate().selectOne(
				"DigestMapper.countDigestByAuthorId", params);
		List<Digest> datas = (List<Digest>) getSqlSessionQueryTemplate()
				.selectList("DigestMapper.findDigestByAuthorId", params,
						new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<Digest> pageFinder = new PageFinder<Digest>(query.getPage(),
				query.getPageSize(), count, datas);
		return pageFinder;
	}

	public PageFinder<Digest> findDigestBySignId(Query query, Long signId,
			Integer isShow) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("signId", signId);
		if (isShow != null) {
			params.put("isShow", isShow);
		}
		int count = (Integer) getSqlSessionQueryTemplate().selectOne(
				"DigestMapper.countDigestBySignId", params);
		List<Digest> datas = (List<Digest>) getSqlSessionQueryTemplate()
				.selectList("DigestMapper.findDigestBySignId", params,
						new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<Digest> pageFinder = new PageFinder<Digest>(query.getPage(),
				query.getPageSize(), count, datas);
		return pageFinder;
	}

	@Override
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("chapterIds", chapterIds);
		return (List<Digest>) getSqlSessionQueryTemplate().selectList(
				"DigestMapper.queryDigestsByChapterIds", params);
	}

	@Override
	public int saveDigestLable(DigestLable dl) {
		return this.getSqlSessionTemplate().insert("DigestLableMapper.insert",
				dl);
	}

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
	public Digest findDigestContentById(Long id) {
		return this.selectOne("DigestMapper.getContentByPrimaryKey", id);
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
		return this
				.selectList("DigestMapper.queryDigestsForHomePage", paramObj);
	}

	@Override
	public Digest getDigestByShowEndDateLimitOne(Map<String, Object> paramObj) {
		return this.selectOne("DigestMapper.getDigestByShowEndDateLimitOne",
				paramObj);
	}

	@Override
	public Digest getDigestContentById(Long id) {
		return this.selectOne("DigestMapper.getDigestContentById", id);
	}

	@Override
	public List<Digest> queryDigestsByIdsAndSortByIds(
			Map<String, Object> paramObj) {
		return this.selectList("DigestMapper.queryDigestsByIdsAndSortByIds",
				paramObj);
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
	public List<Digest> queryDigestsByStoreUpAndCustId(
			Map<String, Object> paramMap) {
		return this.selectList("DigestMapper.queryDigestsByStoreUpAndCustId",
				paramMap);
	}

	@Override
	public List<Digest> queryDigestsHomePageNew(Map<String, Object> paramMap,
			String version) {
		if ("V1".equals(version)) {
			return this.selectList("DigestMapper.queryDigestsHomePageOld",
					paramMap);
		} else if ("V2".equals(version)) {
			return this.selectList("DigestMapper.queryDigestsHomePageNew",
					paramMap);
		}
		return null;
	}

	@Override
	public List<Digest> queryDigestsHomePageOld(Map<String, Object> paramMap) {
		return this
				.selectList("DigestMapper.queryDigestsHomePageOld", paramMap);
	}

	@Override
	public Long getDigestIdtWithOne(Map<String, Object> paramMap) {
		return (Long) super.getSqlSessionQueryTemplate().selectOne(
				"DigestMapper.getDigestIdtWithOne", paramMap);
	}

	@Override
	public List<Digest> queryChannelDigests(Map<String, Object> paramMap) {
		return this
				.selectList("DigestMapper.queryChannelDigests", paramMap);
	}
	
	@Override
	public List<Digest> queryBarDigests(Map<String, Object> paramMap) {
		return this
				.selectList("DigestMapper.queryBarDigests", paramMap);
	}
}
