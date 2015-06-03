package com.dangdang.digital.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.IRankingListApi;
import com.dangdang.digital.service.IListRankingService;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
@Component("rankingListApi")
public class RankingListApiImpl implements IRankingListApi {
	@Resource
	IListRankingService rankingService;
	
	@Resource
	IMediaStatisticsService  statisticsService;
	
	@Override
	public ResultTwoTuple<Long, List<Long>> getSaleIdListByType(int offset,int count, String type) {

		if (("np_newest").equals(type) || ("vp_newest").equals(type)) {
			// 新品榜,暂没有
			return getNewestSaleIdList(offset, count, type);
		} else {
			Long total = rankingService.getListRankingSaleTotalCount(type);
			List<Long> mediaSaleIdList = rankingService.getListRankingSaleIdList(offset, count, type);
			return new ResultTwoTuple<Long, List<Long>>(total, mediaSaleIdList);
		}
	}

	private ResultTwoTuple<Long,List<Long>>  getUpdatedSaleIdList(int offset,int count,String type) {
		Map<String,Object> paramObj = new HashMap<String,Object>(4);
		String sexChannel = type.substring(0,type.indexOf("_"));
		paramObj.put("limit_offset", offset);
		paramObj.put("limit_count", count);
		paramObj.put("sexChannel", sexChannel);//频道标识
		paramObj.put("order_column","media_chapter_change_date");//排序字段
		List<Long> saleIdList =  statisticsService.getUpdatedSaleIdList(paramObj);
		Long total =statisticsService.getUpdatedSaleTotalCount(paramObj);
		return new ResultTwoTuple<Long,List<Long>>(total,saleIdList);
	}

	private ResultTwoTuple<Long,List<Long>>  getNewestSaleIdList(int offset,int count,String type) {
		Map<String,Object> paramObj = new HashMap<String,Object>(4);
		String sexChannel = type.substring(0,type.indexOf("_"));
		paramObj.put("limit_offset", offset);
		paramObj.put("limit_count", count);
		paramObj.put("sexChannel", sexChannel);
		paramObj.put("order_column","media_creation_date");
		List<Long> saleIdList =  statisticsService.getNewestSaleIdList(paramObj);
		Long total = statisticsService.getNewestSaleTotalCount(paramObj) ;
		return new ResultTwoTuple<Long,List<Long>>(total,saleIdList);
	}

}
