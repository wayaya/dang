package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;


public interface IDigestService extends IBaseService<Digest, Long> {
	
	public List<Map<String, Object>> getSign();
	
	public List<Map<String, Object>> getSignByDigestId();
	
	public Digest getDigestContentById(Long digestId);
	
	public PageFinder<Digest> findDigestByAuthorId(Query query, Long authorId, Integer isShow);
	
	public PageFinder<Digest> findDigestBySignId(Query query, Long signId, Integer isShow);
	
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds) ;
	
	public int saveDigestLable(DigestLable dl) ;
 }
