package com.dangdang.digital.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import com.dangdang.digital.model.DDClick;
import com.dangdang.digital.vo.DDClickValueObj;

public interface IDDClickService {
	
	public void insert(DDClick click);
	public void insertBatch(List<DDClick> ddClicks);
	public List<DDClick> getListByRefActionAndPeriodMapReduce(String refAction, Date startTime, Date endTime);
	/**
	 * 
	 * Description: 
	 * @Version1.0 2015年3月2日 下午3:35:13 by 魏嵩（weisong@dangdang.com）创建
	 * @param startTime
	 * @param endTime
	 * @param number
	 * @return map keys:  "saleId" Long; "count" Integer
	 */
	List<LinkedHashMap<String, Object>> getTopNSalePageBrowseMedia(Date startTime, Date endTime, int number);
	
	/**
	 * 
	 * Description: 
	 * @Version1.0 2015年3月2日 下午3:35:13 by 魏嵩（weisong@dangdang.com）创建
	 * @param startTime
	 * @param endTime
	 * @param number
	 * @return map keys:  "mediaId" Long; "count" Integer
	 */
	List<LinkedHashMap<String, Object>> getTopNFreeTryBrowseMedia(Date startTime, Date endTime, int number);
}