package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.dangdang.digital.model.MediaSale;

public interface IMediaSaleService extends IBaseService<MediaSale, Long> {

	public List<MediaSale> getSale(Map<String,Object> map);
	
	public void toShelf(Map map);
	
	public void setIsFull(MediaSale sale);
	
	public void uploadShelf(List<String> saleIds,List<String> mediaIds,String shelfStatus);
	
}
