package com.dangdang.digital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IMediaSaleService;

@Service(value="mediaSaleService")
public class MediaSaleServiceImpl extends BaseServiceImpl<MediaSale, Long>
		implements IMediaSaleService {

	@Resource(name="mediaSaleDao")
	private IMediaSaleDao mediaSaleDao;
	@Override
	public IBaseDao<MediaSale> getBaseDao() {
		return mediaSaleDao;
	}
	@Override
	public MediaSale getMediaSaleWithRelation(Long mediaId, List<Long> chapterIds)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mediaId", mediaId);
		map.put("chapterIds", chapterIds);
		map.put("type", Short.valueOf("0"));
		List<MediaSale> mediaSales = this.mediaSaleDao.selectList("MediaSaleMapper.getWithDetail", map);
		if(mediaSales != null && !mediaSales.isEmpty()){
			return mediaSales.get(0);
		}
		return null;
	}
	

}
