package com.dangdang.digital.listenbook.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.listenbook.model.ListenBookChapter;
import com.dangdang.digital.listenbook.model.ListenBookMedia;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.model.CpPullChapter;
import com.dangdang.digital.model.CpPullMedia;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;

public class ListenBookMediaConvertUtil {
	
	public static MediaSale lbMedia2MediaSale(ListenBookMedia lbMedia){
		MediaSale mediaSale = new MediaSale();
		mediaSale.setName(lbMedia.getName());
		mediaSale.setType((short) 0);
		mediaSale.setPrice(0l);
		mediaSale.setCreationDate(Calendar.getInstance().getTime());
		mediaSale.setLastModifiedDate(Calendar.getInstance().getTime());
		mediaSale.setIsSupportSubscribe((short) 0);
//		TODO coverPic 换成  本地URL地址
		mediaSale.setCoverPic(lbMedia.getImageUrl());
		mediaSale.setShelfStatus(1);
		String status = null;
		status = lbMedia.getStatus();
		if(status!=null&&!status.equals("")){
			mediaSale.setIsFull(Integer.parseInt(status));
		}
		return mediaSale;
	}
	
	public static Author lbMedia2Author(ListenBookMedia lbMedia){
		Author author = new Author();
		author.setPseudonym(lbMedia.getAuthorsStr());
//		author.setName(lbMedia.getAuthorsStr());
		return author;
	}
	
	public static List<Chapter> lbMedia2Chapter(ListenBookMedia lbMedia){
		List<Chapter> chapterList = new ArrayList<Chapter>();
		for(ListenBookChapter lbChapter : lbMedia.getChapters()){
			Chapter chapter = new Chapter();

			chapter.setMediaId(lbMedia.getDdMediaId());
			chapter.setTitle(lbChapter.getName());
			chapter.setIndex(lbChapter.getOrder());
//			TODO chapter需要增加一个外网字段，存放lbChapter.getStreams()
			List<String> streamsList = lbChapter.getStreams();
			StringBuffer streamsSB = new StringBuffer();
			for(String str : streamsList){
				streamsSB.append(str).append(",");
			}
			int streamsEndIdx = (streamsSB.length()>0) ? (streamsSB.length()-1) : 0;
			chapter.setFilePath(streamsSB.substring(0, streamsEndIdx).toString());
			chapter.setWordCnt(Long.parseLong(lbChapter.getSize()+""));
			chapter.setCreationDate(Calendar.getInstance().getTime());
			chapter.setLastModifyedDate(Calendar.getInstance().getTime());
			chapter.setIsShow(1);
			
			chapterList.add(chapter);
		}
		return chapterList;
	}
	
	public static Media lbMedia2Media(ListenBookMedia lbMedia){
		Media media = new Media();
		media.setSaleId(lbMedia.getSaleId());
		media.setTitle(lbMedia.getName());
		media.setDescs(lbMedia.getBrief());
//		TODO imageUrl 和 imageContent 需后续处理（content上传CDN）;coverPic存本地路径;imageUrl需确定存哪个字段
		media.setCoverPic(lbMedia.getImageUrl());
		media.setChapterCnt(lbMedia.getEpisodesSize());
		String status = null;
		status = lbMedia.getStatus();
		if(status!=null&&!status.equals("")){
			media.setIsFull(Integer.parseInt(status));
		}
		media.setCreationDate(Calendar.getInstance().getTime());
		media.setLastModifyDate(Calendar.getInstance().getTime());
		media.setAuthorName(lbMedia.getAuthorsStr());
		media.setAuthorPenname(lbMedia.getAuthorsStr());
		media.setSpeaker(lbMedia.getSpeakersStr());
		media.setCpCode(lbMedia.getCpCode());
		media.setShelfStatus(1);//上架
		media.setLastIndexOrder(lbMedia.getEpisodesSize()-1);
		if(lbMedia.getAuthorId()!=null&&lbMedia.getAuthorId()>0){
			media.setAuthorId(lbMedia.getAuthorId());
		}
		media.setIsShow(1);
		return media;
	}
	
	public static List<CpPullChapter> lbMedia2CpPullChapter(ListenBookMedia lbMedia,Map<String,String> cp2DdChapterId){
		List<CpPullChapter> cpPullChapterList = new ArrayList<CpPullChapter>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(ListenBookChapter lbChapter : lbMedia.getChapters()){
			CpPullChapter cpPullChapter = new CpPullChapter();
			
			cpPullChapter.setCpChapterId(lbChapter.getChapterId().toString());
			cpPullChapter.setDdChapterId(Long.parseLong(cp2DdChapterId.get(lbChapter.getChapterId().toString())));
			cpPullChapter.setDdMediaId(lbMedia.getDdMediaId());
			cpPullChapter.setOrderNum(lbChapter.getOrder());
			cpPullChapter.setCpMediaId(lbMedia.getAlbumId().toString());
			cpPullChapter.setChapterName(lbChapter.getName());
			cpPullChapter.setCreationDate(Calendar.getInstance().getTime());
			cpPullChapter.setLastModifyDate(Calendar.getInstance().getTime());
			cpPullChapter.setCpCode(lbMedia.getCpCode());
			
			Map mapContent = new HashMap();
			mapContent.put("chapterId", lbChapter.getChapterId());
			mapContent.put("chapterName", lbChapter.getName());
			mapContent.put("ordernum", lbChapter.getOrder());
			mapContent.put("wordNum", lbChapter.getSize());
			mapContent.put("createTimeStr", sdf.format(Calendar.getInstance().getTime()));
			mapContent.put("updateTimeStr", sdf.format(Calendar.getInstance().getTime()));
			cpPullChapter.setContent(JSON.toJSONString(mapContent));
			
			cpPullChapterList.add(cpPullChapter);
		}
		
		return cpPullChapterList;
	}
	
	public static CpPullMedia lbMedia2CpPullMedia(ListenBookMedia lbMedia){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CpPullMedia cpPullMedia = new CpPullMedia();
		cpPullMedia.setCpMediaId(lbMedia.getAlbumId().toString());
		cpPullMedia.setDdMediaId(lbMedia.getDdMediaId());
		String status = null;
		
		status = lbMedia.getStatus();
		if(status!=null&&!status.equals("")){
			cpPullMedia.setIsFull(Integer.parseInt(status));
		}
		cpPullMedia.setMediaName(lbMedia.getName());
		cpPullMedia.setCreationDate(Calendar.getInstance().getTime());
		cpPullMedia.setLastModifyDate(Calendar.getInstance().getTime());
		cpPullMedia.setCpCode(lbMedia.getCpCode());
		
		//init column:content
		Map mapContent = new HashMap();
		if (lbMedia.getAuthorId()!=null&&lbMedia.getAuthorId()>0) {
			mapContent.put("authorId", lbMedia.getAuthorId());
		}
		mapContent.put("authorName", lbMedia.getAuthorsStr());
		mapContent.put("bookId", lbMedia.getDdMediaId());
		mapContent.put("bookName", lbMedia.getName());
		List<String> selfCateoryList = lbMedia.getSelfCategorys();
		StringBuffer selfCategorySB = new StringBuffer();
		for(String str : selfCateoryList){
			selfCategorySB.append(str).append(",");
		}
		int selfCategoryEndIdx = (selfCategorySB.length()>0) ? (selfCategorySB.length()-1) : 0;
		mapContent.put("categoryId", selfCategorySB.substring(0, selfCategoryEndIdx).toString());
		mapContent.put("coverUrl", lbMedia.getImageUrl());
		mapContent.put("description", lbMedia.getBrief());
		mapContent.put("updateTime", sdf.format(Calendar.getInstance().getTime()));
		cpPullMedia.setContent(JSON.toJSONString(mapContent));
		cpPullMedia.setStatus(1);
		
		return cpPullMedia;
	}
}
