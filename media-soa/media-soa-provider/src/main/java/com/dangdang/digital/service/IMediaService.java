package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Media;

public interface IMediaService extends IBaseService<Media, Long> {

	public void saveCateSign(Media media , String cateIds);
	
	public Media findMediaWithDetail(Long mediaId,List<Long> chapterIds);
}
