package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.IMediaService;


@Service(value="mediaService")
public class MediaServiceImpl extends BaseServiceImpl<Media, Long> implements
		IMediaService {
	@Resource(name="chapterService")
	private IChapterService chapterService;
	@Resource(name="mediaDao")
	private IMediaDao mediaDao;
	@Override
	public IBaseDao<Media> getBaseDao() {
		return mediaDao;
	}
	@Override
	public void saveCateSign(Media media, String cateIds) {
		this.update(media);
		if(cateIds != null && !"".equals(cateIds)){
			String arr[] = cateIds.split(",");
			Media m = new Media();
			for(String str : arr){
				m.setMediaId(media.getMediaId());
				m.setCatetoryId(Integer.valueOf(str));
				mediaDao.saveCatetorys(m);
			}
		}
	}
	@Override
	public Media findMediaWithDetail(Long mediaId,List<Long> chapterIds) {
		Media media = null;
		media = this.findUniqueByParams("mediaId",mediaId);
		if(media != null){
			media.setChapters(chapterService.findByIds(chapterIds));
		}
		return media;
	}
	
}
