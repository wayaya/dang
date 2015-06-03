package com.dangdang.digital.listenbook.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.listenbook.model.ListenBookChapter;
import com.dangdang.digital.listenbook.model.ListenBookMedia;
import com.dangdang.digital.listenbook.model.PicUpCdnObj;
import com.dangdang.digital.listenbook.service.PicUpCdnThreadPoolExecutorService;
import com.dangdang.digital.listenbook.task.PicUpCdnSychTask;
import com.dangdang.digital.listenbook.util.ListenBookMediaConvertUtil;
import com.dangdang.digital.model.Author;
import com.dangdang.digital.model.CpPullMedia;
import com.dangdang.digital.model.DDCpCatetoryMap;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IAuthorService;
import com.dangdang.digital.service.ICatetoryService;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.service.ICpPullChapterService;
import com.dangdang.digital.service.ICpPullMediaService;
import com.dangdang.digital.service.IDDCpCatetoryMapService;
import com.dangdang.digital.service.IListenBookDataSyncService;
import com.dangdang.digital.service.IMediaRelationService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.ConfigPropertieUtils;

/**
 * 一、media_id处理
 * 	1. 根据规则，media_id生成自有dd_media_id,数据存入表:media_cp_pull_media
 * 	2. chapter数据，存入表：media_cp_pull_chapter
 * 二、分类处理
 *	1. name|url竖线分隔匹配media_dd_cp_catetory_map.cp_catetory_id，查询所属catetory_id  findListByParamsObjs(Object params) params=cp_catetory_id
 * 	2. 将dd_media_id和catetory_id 插入表：media_book_catetory ----saveCatetorys
 * 三、media、chapter数据入库
 * 	1.dd_media_id 对应 media_id,media、chapter数据分别存入表：media和media_chapter
 * 	2.media子集存入media_sale表
 * insert Media_Sale、media_Author、media、media_book_catetory、media_relation、media_chapter、media_cp_pull_media、media_cp_pull_chapter
 */
@Controller
@RequestMapping("audioSource")
public class AudioSourceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AudioSourceController.class);

	@Resource
	private IMediaSaleService mediaSaleService;
	@Resource
	private IAuthorService authorService;
	@Resource
	private IMediaService mediaService;
	@Resource
	private IMediaRelationService mediaRelationService;
	
	@Resource
	private IChapterService chapterService;

	@Resource
	private ICatetoryService catetoryService;
	
	@Resource
	private ICpPullMediaService cpPullMediaService;
	@Resource
	private ICpPullChapterService cpPullChapterService;
	@Resource
	private IDDCpCatetoryMapService dDCpCatetoryMapService;
	
	@Resource
	private PicUpCdnThreadPoolExecutorService picUpCdnThreadPoolExecutorService;

	@Resource
	private IListenBookDataSyncService listenBookDataSyncService;

	
	@RequestMapping(value="add", method =RequestMethod.POST,produces="text/xml")
	public @ResponseBody Object addAudioSource(@RequestParam("content") String jsonStr)  {
		ListenBookMedia lbMedia = resolveSyncData(jsonStr);
		if (StringUtils.isBlank(lbMedia.getName())) {
			return "media's name is null";
		}

		//select dd_media_id from media_cp_pull_media by cp_media_id and cp_code；判断是否能插入该专辑
		CpPullMedia cpPullMedia1 = new CpPullMedia();
		cpPullMedia1.setCpMediaId(lbMedia.getAlbumId().toString());
		cpPullMedia1.setCpCode(lbMedia.getCpCode());
		CpPullMedia cpPullMedia = null;
		cpPullMedia = cpPullMediaService.findMasterUniqueByParamsObjs(cpPullMedia1);
		if (cpPullMedia==null) {
			//insert MediaSale
			MediaSale mediaSale = ListenBookMediaConvertUtil
					.lbMedia2MediaSale(lbMedia);
			int isSuccess = mediaSaleService.save(mediaSale);
			if (isSuccess == 0) {
				return "please post this item again ! AlbumId="
						+ lbMedia.getAlbumId();
			}
			lbMedia.setSaleId(mediaSale.getSaleId());
			//insert mediaAuthor
			Author author = ListenBookMediaConvertUtil.lbMedia2Author(lbMedia);
			if (author != null && !author.getPseudonym().equals("")) {
				List<Author> authorList = authorService
						.selectByAuthorName(author);
				if (authorList != null && authorList.size() > 0) {
					lbMedia.setAuthorId(authorList.get(0).getAuthorId());
				} else {
					authorService.save(author);
					lbMedia.setAuthorId(author.getAuthorId());
				}
			}
			int isMediaSuc = listenBookDataSyncService.saveMedia(lbMedia);
			if (isMediaSuc == 0) {
				return "please post this item again !AlbumId="
						+ lbMedia.getAlbumId();
			}
			int isChapterSuc = listenBookDataSyncService
					.saveMediaOfChapter(lbMedia);
			if (isChapterSuc == 0) {
				return "please invoke addChapter interface !AlbumId="
						+ lbMedia.getAlbumId();
			}
		}else{
			return "please invoke addChapter接口 !该专辑已经插入过，只能添加章节。AlbumId="
					+ lbMedia.getAlbumId();
		}
		return "success";
	}
	
	/**
	 * 更新章节
	 * 1.select dd_media_id from media_cp_pull_chapter by cp_chapter_id and cp_media_id
	 * 
	 * @return
	 */
	@RequestMapping(value="updateChapter", method =RequestMethod.POST,produces="text/xml")
	public @ResponseBody Object updateAudioChapter(@RequestParam("content") String jsonStr)  {
		ListenBookMedia lbMedia = resolveSyncData(jsonStr);
		
		int isUpdateChapterSuc = listenBookDataSyncService.updateChapter(lbMedia);
		if(isUpdateChapterSuc==0){
			return "update Chapter's relation table has error .please invoke updateChapter interface again ! cpMediaId|albumId="+lbMedia.getAlbumId();
		}
		return "success";
	}
	
	/**
	 * 添加章节 同时更新 专辑表的章节数量
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="addChapter", method =RequestMethod.POST,produces="text/xml")
	public @ResponseBody Object addAudioChapter(@RequestParam("content") String jsonStr)  {
		ListenBookMedia lbMedia = resolveSyncData(jsonStr);
		
		int isAddChapterSuc = listenBookDataSyncService.saveChapter(lbMedia);
		if(isAddChapterSuc==0){
			return "添加章节错误。 -> CpPullMedia'ddMediaId cann't find . Cann't insert table: media_chapter and media_cp_pull_chapter ! cpMediaId|albumId="+lbMedia.getAlbumId();
		}else if(isAddChapterSuc==2){
			return "添加章节错误。 请先添加专辑，否则没有专辑id-> CpPullMedia'ddMediaId cann't find . Cann't insert table: media_chapter and media_cp_pull_chapter ! cpMediaId|albumId="+lbMedia.getAlbumId();
		}
		
		return "success";
	}
	
	
	@RequestMapping(value="/update", method = RequestMethod.POST,produces="text/xml")
	public  @ResponseBody String upateAudioSource(@RequestParam("content") String jsonStr ) {
		System.out.println("update come in ...");
		return "success";
	}
	
	
	/**
	 * 删除整个专辑，所有数据
	 */
	@RequestMapping(value="/delete", method = RequestMethod.POST,produces = "text/xml")
	public  @ResponseBody String deleteAudioSource(@RequestParam("content") String jsonStr) {
		JSONObject audioObject = null;
		String originalStr = JSON.parse(jsonStr).toString();
		audioObject = (JSONObject)JSON.parse(originalStr);
		
		ListenBookMedia lbMedia = new ListenBookMedia();
		BigInteger albumId = audioObject.getBigInteger("album_id");
		lbMedia.setAlbumId(albumId);
		
		int isDelMediaSuc = listenBookDataSyncService.deleteMedia(lbMedia);
		if(isDelMediaSuc==0){
			return "delete media error ! please post this item again !AlbumId="+lbMedia.getAlbumId();
		}
		
		return "success";
	}
	
	/**
	 * 仅仅 上传专辑封面图到 CDN
	 */
	@RequestMapping(value="upPic", method =RequestMethod.POST,produces="text/xml")
	public @ResponseBody Object upPicToCdn(@RequestParam("content") String jsonStr)  {
		JSONObject audioObject = null;
		String originalStr = JSON.parse(jsonStr).toString();
		audioObject = (JSONObject)JSON.parse(originalStr);
		
		ListenBookMedia lbMedia = new ListenBookMedia();
		BigInteger albumId = audioObject.getBigInteger("album_id");
		lbMedia.setAlbumId(albumId);
		
		//image resolve
		String image = audioObject.getString("image");
		JSONObject imageObj = null;
		imageObj = (JSONObject)JSON.parse(image);
		String imageContent = imageObj.getString("content");
		lbMedia.setImageContent(imageContent);
		
		//select id,dd_media_id,is_ful from media_cp_pull_media by cp_media_id
		CpPullMedia cpPullMedia = new CpPullMedia();
		cpPullMedia.setCpMediaId(lbMedia.getAlbumId().toString());
		cpPullMedia = cpPullMediaService.findMasterUniqueByParamsObjs(cpPullMedia);
		if(cpPullMedia!=null){
			lbMedia.setDdMediaId(cpPullMedia.getDdMediaId());
			
			// A new thread pool is created...
			ThreadPoolExecutor executor = picUpCdnThreadPoolExecutorService
					.getPicUpCdnThreadPoolExecutor();
			executor.allowCoreThreadTimeOut(true);
			
			PicUpCdnObj picUpCdnObj = new PicUpCdnObj();
			picUpCdnObj.setImgContent(lbMedia.getImageContent());
			picUpCdnObj.setMediaId(lbMedia.getDdMediaId());
			picUpCdnObj.setRetryNum(0);
			
			executor.execute(new PicUpCdnSychTask(picUpCdnObj,
					picUpCdnThreadPoolExecutorService));
		}
		
		return "success";
	}
	
	private ListenBookMedia resolveSyncData(String jsonStr){
		long startTime = System.currentTimeMillis();
		ListenBookMedia listenBookMedia = new ListenBookMedia();
		jsonStr = replacer(jsonStr);
		
		JSONObject audioObject = null;
		String originalStr = JSON.parse(jsonStr).toString();
		audioObject = (JSONObject)JSON.parse(originalStr);
		
		BigInteger albumId;
		Long ddMediaId ;
		String name;
		String authors;
		String brief;
		String speakers;
		String category;
		StringBuffer categoryName = new StringBuffer();
		StringBuffer categoryUrl = new StringBuffer();
		String selfCategory;
		Date updateTime = null;
		String status;
		String sources;
		String image;
		String episodes;
		Integer episodesSize;
		String imageUrl = null;
		String imageContent = null;
		
		//过滤作者或演讲者 特殊符号
		String specCharRegEx = "[`~!@#$%^&*+=|{}\\[\\]<>~！@#￥%……&*——+|{}【】’。？]";
		Pattern specCharPa = Pattern.compile(specCharRegEx);
		String contentRegEx = "[`@#$%^&*+=|{}\\[\\]@#￥%&*——+|{}]";
		Pattern contentPa = Pattern.compile(contentRegEx);
		
		albumId = audioObject.getBigInteger("album_id");
		listenBookMedia.setAlbumId(albumId);
//		ddMediaId = mediaId2DdMediaId(albumId.toString());
//		listenBookMedia.setDdMediaId(ddMediaId);
		name = filterUrl(audioObject.getString("name"));
		listenBookMedia.setName(name);
		//authors resolve
		List<String> authorsList = new ArrayList<String>();
		String authorsTemp = audioObject.getString("authors");
		JSONArray authorsAry = JSONArray.parseArray(authorsTemp);
		if (authorsAry!=null&&authorsAry.size()>0) {
			String[] authorsStrs = new String[authorsAry.size()];
			authorsStrs = authorsAry.toArray(authorsStrs);
			for (String s : authorsStrs) {
				formatNameStr(filterSpecialChar(s, specCharPa), authorsList);
			}
		}
		listenBookMedia.setAuthors(authorsList);
		StringBuffer authorNameSB  = new StringBuffer();
        for(String s :authorsList){
        	authorNameSB.append(s).append(",");
        }
        int authorNameEndIdx = (authorNameSB.length()>0) ? (authorNameSB.length()-1) : 0;
        listenBookMedia.setAuthorsStr(authorNameSB.substring(0, authorNameEndIdx).toString());
		
        if (audioObject.getString("brief")!=null) {
			brief = filterSpecialChar(
					filterUrl(audioObject.getString("brief")), contentPa);
		}else{
			brief = "";
		}
		listenBookMedia.setBrief(brief);
		
		//speakers resolve
		List<String> speakersList = new ArrayList<String>();
		String speakersTemp = audioObject.getString("speakers");
		JSONArray speakersAry = (JSONArray)JSON.parse(speakersTemp);
		if (speakersAry!=null&&speakersAry.size()>0) {
			String[] speakersStrs = new String[speakersAry.size()];
			speakersStrs = speakersAry.toArray(speakersStrs);
			for (String s : speakersStrs) {
				formatNameStr(filterSpecialChar(s, specCharPa), speakersList);
			}
		}
		listenBookMedia.setSpeakers(speakersList);
		StringBuffer speakerNameSB  = new StringBuffer();
        for(String s :speakersList){
        	speakerNameSB.append(s).append(",");
        }
        int speakerNameEndIdx = (speakerNameSB.length()>0) ? (speakerNameSB.length()-1) : 0;
        listenBookMedia.setSpeakersStr(speakerNameSB.substring(0, speakerNameEndIdx).toString());

		//category resolve
		List<String> categorysList = new ArrayList<String>();
		String categoryTemp = audioObject.getString("category");
		JSONArray categoryAry = (JSONArray)JSON.parse(categoryTemp);
		if (categoryAry!=null&&categoryAry.size()>0) {
			for (int i = 0; i < categoryAry.size(); i++) {
				JSONObject categoryObj = categoryAry.getJSONObject(i);
				String categoryNameTemp = categoryObj.getString("name");
				String categoryUrlTemp = categoryObj.getString("url");
				if (categoryNameTemp != null && categoryUrlTemp != null) {
					categorysList.add(categoryNameTemp.concat("|").concat(
							categoryUrlTemp));
				}
			}
		}
		listenBookMedia.setCategorys(categorysList);
		
		List<String> selfCategorys = new ArrayList<String>();
//		查询自有分类
		List<DDCpCatetoryMap> dDCpCatetoryMapList = null;
		if (categorysList!=null&&categorysList.size()>0) {
			dDCpCatetoryMapList = dDCpCatetoryMapService
					.findDDCpCategoryByCategoryIds(categorysList);
			if (dDCpCatetoryMapList!=null&&dDCpCatetoryMapList.size()>0) {
				for (DDCpCatetoryMap dd : dDCpCatetoryMapList) {
					//如果分类已存在，不重复添加
					if (!selfCategorys.contains(dd.getCatetoryId() + "")) {
						selfCategorys.add(dd.getCatetoryId() + "");
					}
				}
			}
		}
		listenBookMedia.setSelfCategorys(selfCategorys);
		
		listenBookMedia.setUpdateTime(Calendar.getInstance().getTime());
		
		status = audioObject.getString("status");
		listenBookMedia.setStatus(status2StatusCode(status));
		//sources resolve 废弃
//			sources = audioObject.getString("sources");

		//image resolve
		image = audioObject.getString("image");
		JSONObject imageObj = null;
		imageObj = (JSONObject)JSON.parse(image);
		if (imageObj!=null) {
			imageUrl = imageObj.getString("url");
			imageContent = imageObj.getString("content");
		}
		listenBookMedia.setImageUrl(imageUrl);
		listenBookMedia.setImageContent(imageContent);
		
		if (imageUrl!=null) {
			//根据imageUrl解析出cpCode
			Pattern p = Pattern.compile(
					"(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(imageUrl);
			if (matcher.find()) {
				listenBookMedia.setCpCode((matcher.group().split("\\.")[0]));
			} else {
				listenBookMedia.setCpCode("unknown");
			}
		} else {
			listenBookMedia.setCpCode("unknown");
		}
		episodesSize = audioObject.getInteger("episodes_size");
		if(episodesSize==null){
			episodesSize = 0;
		}
		
		episodes = audioObject.getString("episodes");
		//校验episodeSize是否与实际章节数一致
		JSONArray episodeArray = (JSONArray)JSON.parse(episodes);
		if (episodeArray!=null&&episodeArray.size()>0) {
			if (episodesSize.intValue() != episodeArray.size()) {
				episodesSize = episodeArray.size();
			}
		}
		listenBookMedia.setEpisodesSize(episodesSize);
		
		List<ListenBookChapter> chapterList = new ArrayList<ListenBookChapter>();
		if (episodeArray!=null&&episodeArray.size()>0) {
			for (int i = 0; i < episodeArray.size(); i++) {
				JSONObject episode = episodeArray.getJSONObject(i);
				ListenBookChapter chapter = new ListenBookChapter();
				chapter.setChapterId(episode.getBigInteger("id"));
				chapter.setOrder(episode.getInteger("order"));
				chapter.setName(episode.getString("name"));
				//resolve streams
				List<String> streamsList = new ArrayList<String>();
				String streamsTemp = episode.getString("streams");
				JSONArray streamsAry = (JSONArray) JSON.parse(streamsTemp);
				if (streamsAry!=null&&streamsAry.size()>0) {
					String[] streamsStrs = new String[streamsAry.size()];
					streamsStrs = streamsAry.toArray(streamsStrs);
					for (String s : streamsStrs) {
						streamsList.add(s);
					}
				}
				chapter.setStreams(streamsList);
				Integer sizeInt = episode.getInteger("size");
				if (sizeInt != null) {
					chapter.setSize(sizeInt);
				}
				chapterList.add(chapter);
			}
		}
		listenBookMedia.setChapters(chapterList);
			
		long resTime = System.currentTimeMillis();
		return listenBookMedia;
	}
	
	private String removeChar(String inputString){
		String str = inputString.trim();
		String str2="";
		if(str != null && !"".equals(str)){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)>=48 && str.charAt(i)<=57){
						str2+=str.charAt(i);
					}
			}
		}	
		
		return str2;
	}
	
	public String replacer(String data) {
		try {
			StringBuffer tempBuffer = new StringBuffer();
			int incrementor = 0;
			int dataLength = data.length();
			while (incrementor < dataLength) {
				char charecterAt = data.charAt(incrementor);
				if (charecterAt == '%') {
					tempBuffer.append("<percentage>");
				} else if (charecterAt == '+') {
					tempBuffer.append("<plus>");
				} else {
					tempBuffer.append(charecterAt);
				}
				incrementor++;
			}
			data = tempBuffer.toString();
			data = URLDecoder.decode(data, "utf-8");
			data = data.replaceAll("<percentage>", "%");
			data = data.replaceAll("<plus>", "+");
		} catch (Exception e) {
			LOGGER.error("AudioSourceController:replacer URLDecoder error : ", e);
		}
		return data;
	}
	
	public void formatNameStr(String s,List container){
		if(s.contains("、")){
			String[] ary = s.split("、");
			CollectionUtils.addAll(container, ary);
		}else if(s.contains("/")){
			String[] ary = s.split("/");
			CollectionUtils.addAll(container, ary);
		}else if(s.contains(" ")){
			String[] ary = s.split(" ");
			CollectionUtils.addAll(container, ary);
		}else if(s.contains(",")){
			String[] ary = s.split(",");
			CollectionUtils.addAll(container, ary);
		}else if(s.contains("，")){
			String[] ary = s.split("，");
			CollectionUtils.addAll(container, ary);
		}else if(s.contains(";")){
			String[] ary = s.split(";");
			CollectionUtils.addAll(container, ary);
		}else if(s.contains("|")){
			String[] ary = s.split("|");
			CollectionUtils.addAll(container, ary);
		}else{
			container.add(s);
		}
	}
	
	private String filterSpecialChar(String origStr,Pattern p ){
//		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        Pattern p = Pattern.compile(regEx);
		if(origStr!=null&&origStr.equals("")){
	        Matcher m = p.matcher(origStr);
	        return m.replaceAll("").trim();
		}else{
	        return origStr;
		}
	}
	
	private String filterUrl(String origStr){
		if(origStr!=null&&origStr.equals("")){
			Pattern pattern = Pattern.compile("([http]{4}\\:\\/\\/)*([a-zA-Z]|[0-9])*(\\.([a-zA-Z]|[0-9])*)*(\\/([a-zA-Z]|[0-9])*)*\\s?");
			Matcher m = pattern.matcher(origStr);
			return m.replaceAll("").trim();
		}else{
	        return origStr;
		}
	}
	
	private String status2StatusCode(String status){
		try {
			if (status!=null) {
				status = status.trim();
				String statuCodeTemp = ConfigPropertieUtils
						.getString("listen.media.status.".concat(URLEncoder
								.encode(status, "UTF-8")));
				if (statuCodeTemp == null || statuCodeTemp.equals("")) {
					status = "0";
				} else {
					status = statuCodeTemp;
				}
			}else{
				return "0";
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("URLEncoder lbMedia Status error: ", e);
		}
		return status;
	}
	
	/**
	 * 更新 media_dd_cp_catetory_map表
	 */
	@RequestMapping(value="/insertDd", method = RequestMethod.POST,produces="text/xml")
	public  @ResponseBody String insertDDCpCatetoryMap(@RequestParam("content") String jsonStr) {
		LineIterator it = null;
		try {
			it = FileUtils.lineIterator(new File("D:\\dataCenter\\listenBook\\media_dd_cp_catetory.txt"), "UTF-8");
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] ary = line.split("@");
		        
		        DDCpCatetoryMap dDCpCatetoryMap= new DDCpCatetoryMap();
		        dDCpCatetoryMap.setCatetoryId(Integer.parseInt(ary[0]));
		        dDCpCatetoryMap.setCatetoryCode(ary[1]);
		        dDCpCatetoryMap.setCpCatetoryId(ary[2]);
		        dDCpCatetoryMap.setType(ary[3]);
		        
		        System.out.println(Integer.parseInt(ary[0])+" "+ary[1]+" "+ary[2]+" "+ary[3]);
		        dDCpCatetoryMapService.save(dDCpCatetoryMap);
		    }
		} catch (Exception e) {
			LOGGER.error("file error;",e);
		} finally {
		    LineIterator.closeQuietly(it);
		}
		
		return "success";
	}
	
}
