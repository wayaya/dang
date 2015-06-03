package com.dangdang.digital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IDigestDao;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Repository
public class DigestDaoImpl extends BaseDaoImpl<Digest> implements IDigestDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSign() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>)this.getSqlSessionQueryTemplate().selectList("DigestMapper.getSign");
		//return this.selectList("DigestMapper.getSign"); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getSignByDigestId() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>)this.getSqlSessionQueryTemplate().selectList("DigestMapper.getSignByDigestId");
	}
	@Override
	public Digest getDigestContentById(Long digestId){
		return this.selectOne("DigestMapper.getContentByPrimaryKey", digestId);
			
	}
	
	public PageFinder<Digest> findDigestByAuthorId(Query query, Long authorId, Integer isShow){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorId", authorId);
		if(isShow != null){
			params.put("isShow", isShow);
		}
		int count = (Integer) getSqlSessionQueryTemplate().selectOne("DigestMapper.countDigestByAuthorId", params);
		List<Digest> datas = (List<Digest>) getSqlSessionQueryTemplate().selectList("DigestMapper.findDigestByAuthorId", params,new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<Digest> pageFinder = new PageFinder<Digest>(query.getPage(),query.getPageSize(), count, datas);
		return pageFinder;
	}
	public PageFinder<Digest> findDigestBySignId(Query query, Long signId, Integer isShow){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("signId", signId);
		if(isShow != null){
			params.put("isShow", isShow);
		}
		int count = (Integer) getSqlSessionQueryTemplate().selectOne("DigestMapper.countDigestBySignId", params);
		List<Digest> datas = (List<Digest>) getSqlSessionQueryTemplate().selectList("DigestMapper.findDigestBySignId", params,new RowBounds(query.getOffset(), query.getPageSize()));
		PageFinder<Digest> pageFinder = new PageFinder<Digest>(query.getPage(),query.getPageSize(), count, datas);
		return pageFinder;
	}
	
	@Override
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("chapterIds", chapterIds);
		return (List<Digest>) getSqlSessionQueryTemplate().selectList("DigestMapper.queryDigestsByChapterIds", params);
	}
	

	@Override
	public int saveDigestLable(DigestLable dl) {
		return this.getSqlSessionTemplate().insert("DigestLableMapper.insert", dl);
	}
}
