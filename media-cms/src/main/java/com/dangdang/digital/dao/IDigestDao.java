package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IDigestDao extends IBaseDao<Digest> {
	
	public List<Map<String, Object>> getSign();
	
	public List<Map<String, Object>> getSignByDigestId();
	
	public Digest getDigestContentById(Long digestId);
	
	/**
	 * 根据authorId获取文章
	 * Description: 
	 * @Version1.0 2015年2月9日 下午3:11:15 by 汪明晖（wangminghui@dangdang.com）创建
	 * @param query
	 * @param AuthorId
	 * @param isShow
	 * @return
	 */
	public PageFinder<Digest> findDigestByAuthorId(Query query, Long AuthorId, Integer isShow);
	
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds);
	
	public PageFinder<Digest> findDigestBySignId(Query query, Long signId, Integer isShow);
		
	public int saveDigestLable(DigestLable dl) ;
}
