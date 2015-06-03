package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.dao.IMediaSaleDao;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.vo.ChangeShelfMessage;

@Service(value = "mediaSaleService")
public class MediaSaleServiceImpl extends BaseServiceImpl<MediaSale, Long> implements IMediaSaleService {
	private static final Logger logger = Logger.getLogger(MediaSaleServiceImpl.class);

	@Resource(name = "mediaSaleDao")
	private IMediaSaleDao mediaSaleDao;

	@Resource
	private RabbitTemplate rabbitTemplate;
	@Resource
	private IMediaDao mediaDao;

	@Override
	public IBaseDao<MediaSale> getBaseDao() {
		return mediaSaleDao;
	}

	@Override
	public List<MediaSale> getSale(Map<String, Object> map) {
		return mediaSaleDao.getSale(map);
	}

	@Override
	public void toShelf(Map map) {
		mediaSaleDao.toShelf(map);
		ChangeShelfMessage changeShelfMessage = new ChangeShelfMessage();
		List<Long> saleIds = new ArrayList<Long>();
		String[] ids = (String[]) map.get("ids");
		for (String id : ids) {
			for (String splitId : StringUtils.split(id, ",")) {
				saleIds.add(Long.valueOf(splitId));
			}
		}
		try {
			changeShelfMessage.setSaleIds(saleIds);
			changeShelfMessage.setStatus((Integer) map.get("status"));
			rabbitTemplate.convertAndSend("media.sale.change.shelf", changeShelfMessage);
			logger.info("发送订单上下架消息成功," + changeShelfMessage.getSaleIds() + ":" + changeShelfMessage.getStatus());
		} catch (Exception e) {
			logger.error("发送订单上下架消息失败" + e);
		}

	}

	@Override
	public void setIsFull(MediaSale sale) {
		this.update(sale);
		Media media = new Media();
		media.setSaleId(sale.getSaleId());
		media.setIsSupportFullBuy(sale.getIsSupportFullBuy());
		mediaDao.setIsFull(media);
	}

	@Override
	public void uploadShelf(List<String> saleIds,List<String> mediaIds,String shelfStatus) {
		if(mediaIds.size()>100){
			int cnt = mediaIds.size() / 100;
			int mod = mediaIds.size() % 100;
			if(mod!=0){
				cnt = cnt+1;
			}
			for(int i=0;i<cnt;i++){
				int form = i*100;
				int to = i*100+100;
				if(to>mediaIds.size()){
					to = mediaIds.size();
				}
				mediaToShelf(mediaIds.subList(form, to),shelfStatus);
			}
		}else{
			if(mediaIds.size()>0){
				mediaToShelf(mediaIds,shelfStatus);
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		String[] ids = new String[saleIds.size()];
		map.put("ids", saleIds.toArray(ids));
		map.put("status", Integer.valueOf(shelfStatus));
		toShelf(map);
	}

	private void mediaToShelf(List<String> mediaIds,String shelfStatus){
		String ids = StringUtils.join(mediaIds.toArray(),",");
		Map<String,String> map = new HashMap<String, String>();
		map.put("ids", ids);
		map.put("shelfStatus", shelfStatus);
		mediaDao.toShelf(map);
	}
	public static void main(String[] args) {
	}
}
