package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.Media;

@Repository(value = "mediaDao")
public class MediaDaoImpl extends BaseDaoImpl<Media> implements IMediaDao {

	@Override
	public void saveCatetorys(Media media) {
		insert("MediaMapper.saveCatetorys", media);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getMediaListBySaleId(Long saleId) {
		return (List<Long>) this.getSqlSessionQueryTemplate().selectList("MediaMapper.getMediaListBySaleId", saleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getCategorysByMediaId(List<Long> mediaIds) {
		return (List<Map<String, Object>>) this.getSqlSessionQueryTemplate().selectList(
				"MediaMapper.getCategorysByMediaId", mediaIds);
	}

	@Override
	public Map<String, Object> getCategorysByMediaId(Long mediaId) {
		List<Long> mediaIds = new ArrayList<Long>();
		mediaIds.add(mediaId);
		List<Map<String, Object>> categorys = this.getCategorysByMediaId(mediaIds);
		if (CollectionUtils.isEmpty(categorys)) {
			return null;
		}
		return categorys.get(0);
	}

}
