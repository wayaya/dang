package com.dangdang.digital.processor;

import static com.dangdang.digital.utils.GlobalParamNameUtils.CHANNEL_TYPE;
import static com.dangdang.digital.utils.GlobalParamNameUtils.CHANNEL_TYPE_NP;
import static com.dangdang.digital.utils.GlobalParamNameUtils.CHANNEL_TYPE_VP;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivitySaleService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;
/**
 * 
 * Description: 火辣限限免
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午6:03:20  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapihotFreeprocessor")
public class HotFreeProcessor extends BaseApiProcessor{
	
	/**
	 * 活动服务
	 */
	@Resource  IActivitySaleService activitySaleService;
	
	@Resource
	ICacheApi  cacheApi; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String channelType = request.getParameter(CHANNEL_TYPE);
		//start,end请求参数判断
		int start =0;
		int end =2;
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(checkResult.success){
			start = checkResult.start;
			end = checkResult.end; 
		}
		int activityId = 20001;//20001男频,20002女频
		if(channelType.equalsIgnoreCase(CHANNEL_TYPE_NP)){
			activityId = 20001;
		}else if(channelType.equalsIgnoreCase(CHANNEL_TYPE_VP)){
			activityId = 20002;
		}else {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"频道参数不存在", response);
			return;
		}
		List<MediaSaleCacheVo> listMediaSingelSales = null;
		try {
			//只获取简要字段信息
			listMediaSingelSales =cacheApi.getHotFreeFromCache(start, end, activityId);
		} catch (Exception e) {
			LogUtil.warn(LOGGER, "查询火辣限免缓存数据出错", e);
			sender.fail(ErrorCodeEnum.ERROR_CODE_11003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_11003.getErrorMessage(), response);
			return ;
		}
		if(null == listMediaSingelSales||listMediaSingelSales.size()==0){
			LogUtil.warn(LOGGER, "火辣限免下没有单品销售实体数据");
			sender.fail(ErrorCodeEnum.ERROR_CODE_11003.getErrorCode(),"火辣限免下没有单品销售实体数据", response);
			return ;
		}
		List<ReturnSaleVo> returnSalelist = ReturnBeanUtils.batchgetReturnSaleListVo(listMediaSingelSales);
		sender.put("saleList", returnSalelist);//media数据信息
		long  expireTime = cacheApi.getHotFreeEndTimeFromCache(activityId);
		sender.put("columnEndTime",expireTime);
		sender.success(response);
	}

}
