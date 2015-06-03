package com.dangdang.digital.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.restlet.engine.util.DateUtils;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDigestDao;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class DigestServiceImpl extends BaseServiceImpl<Digest, Long> implements IDigestService{
	@Resource
	private IDigestDao digestDao;
	@Override
	public IBaseDao<Digest> getBaseDao() {
		return digestDao;
	}
	@Override
	public List<Map<String, Object>> getSign() {
		// TODO Auto-generated method stub
		return digestDao.getSign();
	}
	@Override
	public List<Map<String, Object>> getSignByDigestId() {
		// TODO Auto-generated method stub
		return digestDao.getSign();
	}
	
	public Digest getDigestContentById(Long digestId){
		return digestDao.getDigestContentById(digestId);
		
	}
	
	@Override
	public PageFinder<Digest> findDigestByAuthorId(Query query, Long authorId,
			Integer isShow) {
		return digestDao.findDigestByAuthorId(query, authorId, isShow);
	}
	@Override
	public PageFinder<Digest> findDigestBySignId(Query query, Long signId,
			Integer isShow) {
		return digestDao.findDigestBySignId(query, signId, isShow);
	}
	
	@Override
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds) {
		
		return digestDao.queryDigestsByChapterIds(chapterIds);
	}
	
	@Override
	public int saveDigestLable(DigestLable dl) {
		return digestDao.saveDigestLable(dl);
	}
	
	@Override
	public int save(Digest digest) {
		Date showTime = digest.getShowStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuilder sortPageStr = new StringBuilder();
		sortPageStr.append(sdf.format(showTime));
		sortPageStr.append(digest.getWeight().toString());
		sortPageStr.append(String.format("%04d",(int)(Math.random()*10000)));
		Long sortPage = Long.parseLong(sortPageStr.toString());
		digest.setSortPage(sortPage);
		return super.save(digest);
	}
	
	@Override
	public int update(Digest digest) {
		Date showTime = digest.getShowStartDate();
		Digest old = get(digest.getId());
		if(!DateUtils.equals(showTime, old.getShowStartDate())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			StringBuilder sortPageStr = new StringBuilder();
			sortPageStr.append(sdf.format(showTime));
			sortPageStr.append(digest.getWeight().toString());
			sortPageStr.append(String.format("%04d",(int)(Math.random()*10000)));
			Long sortPage = Long.parseLong(sortPageStr.toString());
			digest.setSortPage(sortPage);
		} else {
			digest.setSortPage(old.getSortPage());
		}
		return super.update(digest);
	}
}
