package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ICatetoryDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;
import com.dangdang.digital.vo.MediaSimpleSearchReturnVo;
import com.ibm.icu.util.Calendar;

@Service(value="mediaService")
public class MediaServiceImpl extends BaseServiceImpl<Media, Long> implements
		IMediaService {

	@Resource(name="mediaDao")
	private IMediaDao mediaDao;
	@Resource
	private ICatetoryDao catetoryDao;
	@Resource
	private IMediaSaleService mediaSaleService;
	
	@Override
	public IBaseDao<Media> getBaseDao() {
		return mediaDao;
	}
	@Override
	public void saveCateSign(Media media, String cateIds) {
		media.setLastModifyDate(Calendar.getInstance().getTime());
		media.setModifier(UsercmsUtils.getCurrentUser().getLoginName());
		this.update(media);
		catetoryDao.delCatetoryByMediaId(media.getMediaId());
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
	public List<MediaSimpleSearchReturnVo> findSimpleSearchReturnByIds(List<Integer> ids) {
		return (List<MediaSimpleSearchReturnVo>) mediaDao.findSimpleSearchReturnByIds(ids);
	}

	@Override
	public void saveCatetorys(Media media) {
		mediaDao.saveCatetorys(media);
	}
	
	@Override
	public PageFinder<Long> queryEpubProductList(Query query){
		return mediaDao.queryEpubProductList(query);
	}
	
	@Override
	public PageFinder<Media> queryEpubMedia(Media media, Query query,
			Map<String, Object> param) {
		Map<String,Object> newparam = convertBeanToMap(media);
		newparam.putAll(param);
		return mediaDao.queryEpubMedia(newparam, query);
	}
}
