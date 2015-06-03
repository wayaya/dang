package com.dangdang.digital.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IChapterDao;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.model.Chapter;
import com.dangdang.digital.service.IChapterService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MD5Utils;
import com.dangdang.digital.utils.ZipUtil;

@Service(value = "chapterService")
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Long> implements IChapterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChapterServiceImpl.class);

	@Resource
	private IChapterDao chapterDao;

	@Override
	public IBaseDao<Chapter> getBaseDao() {
		return chapterDao;
	}

	@Override
	public Chapter getHttpPathByChapterId(Long chapterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", chapterId);
		Chapter chapter = chapterDao.selectOne("ChapterMapper.getAll", map);
		if (chapter == null) {
			chapter = chapterDao.selectOne("ChapterMapper.getAll", map);
		}
		if (chapter == null) {
			return null;
		}
		chapter.setHttpPath("redirect:" + ConfigPropertieUtils.getString("media.chapter.resource.http.path") + "/"
				+ chapter.getFilePath());
		return chapter;
	}

	@Override
	public File getZipByChapterIds(List<Long> chapterIds) throws ApiException {
		List<File> fileList = new ArrayList<File>();
		for (Long chapterId : chapterIds) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", chapterId);
			Chapter chapter = chapterDao.selectOne("ChapterMapper.getAll", map);
			if (chapter == null) {
				chapter = chapterDao.selectOne("ChapterMapper.getAll", map);
			}
			if (chapter == null || StringUtils.isBlank(chapter.getFilePath())) {
				ApiException ae = (ApiException) MobileApiException.cannotFindChapter();
				throw ae;
			}
			File file = new File(ConfigPropertieUtils.getString("media.resource.root.path") + File.separator
					+ chapter.getFilePath());
			fileList.add(file);
		}
		String zipFileName = MD5Utils.getInstance().cell32(chapterIds.toString());
		File zipFile = new File(ConfigPropertieUtils.getString("media.resource.root.path") + File.separator
				+ zipFileName);
		if (zipFile.exists()) {
			return zipFile;
		}
		try {
			ZipUtil.ZipFiles(fileList, zipFile);
			return zipFile;
		} catch (IOException e) {
			LogUtil.error(LOGGER, e, "压缩文件出错");
			return null;
		}
	}

	@Override
	public boolean isTheLastChapter(Long chapterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", chapterId);
		Chapter chapter = chapterDao.selectOne("ChapterMapper.getAll", map);
		if (chapter == null || chapter.getMediaId() == null || chapter.getIndex() == null) {
			LogUtil.error(LOGGER, "查询章节信息出错，chapterId：" + chapterId);
			return true;
		}
		Integer maxIndex = chapterDao.getMaxIndexOrderByMediaId(chapter.getMediaId());

		if (maxIndex != null && maxIndex > chapter.getIndex()) {
			return false;
		}
		return true;
	}

	@Override
	public List<Chapter> getAllChapterByMediaIds(Long mediaId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mediaId", mediaId);
		return chapterDao.selectList("ChapterMapper.getChaptersByMediaId", paramMap);
	}
	
	@Override
	public List<Chapter> getAllChapterByMediaIds(Long mediaId, Integer start, Integer count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mediaId", mediaId);
		return chapterDao.getAllChapterByMediaId(paramMap, start, count);
	}

	@Override
	public Integer getCountByMediaId(Long mediaId) throws ApiException {
		if (mediaId == null) {
			throw ApiException.invalidParams();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mediaId", mediaId);
		return chapterDao.getCountByMediaId(paramMap);
	}

	@Override
	public boolean isFreeChapters(List<Long> chapterIdList) {
		List<Chapter> chapterList = this.findByIds(chapterIdList);
		for (Chapter chapter : chapterList) {
			if (chapter != null && Constans.IS_FREE_NO.equals(chapter.getIsFree())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isFreeChapters(Long chapterId) {
		List<Long> chapterIdList = new ArrayList<Long>();
		chapterIdList.add(chapterId);
		return this.isFreeChapters(chapterIdList);
	}
	
	@Override
	public List<Map<String, Object>> getMaxIndexOrderByMediaIds(List<Long> mediaIds) {
		return chapterDao.getMaxIndexOrderByMediaIds(mediaIds);
	}
}
