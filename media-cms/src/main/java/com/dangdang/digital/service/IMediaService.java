package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Media;
import com.dangdang.digital.vo.MediaSimpleSearchReturnVo;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IMediaService extends IBaseService<Media, Long> {

	public void saveCateSign(Media media , String cateIds);
	
	public List<MediaSimpleSearchReturnVo> findSimpleSearchReturnByIds(List<Integer> ids);
	
	public void saveCatetorys(Media media);
	
	public PageFinder<Long> queryEpubProductList(Query query);
	
	public PageFinder<Media> queryEpubMedia(Media media, Query query, Map<String, Object> param);

	
}
