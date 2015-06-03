package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.ListCategory;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ThreeTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;

/**
 * 
 * Description: 榜单接口,根据榜单标识和请求数据条数,返回榜单数据
 * All Rights Reserved.所有参数最小写
 * @version 1.0  2014年12月10日 上午9:36:28  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapimediaRankingListprocessor")
public class RankingListProcessor extends BaseApiProcessor {
	
	@Resource
	ICacheApi  cacheApi; 
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//榜单类型
		//栏目类型标识
		String channelType = request.getParameter(GlobalParamNameUtils.CHANNEL_TYPE);
		if(RequestParamCheckerUtils.isNullOrEmpty(channelType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入频道参数channelType", response);
			return;
		}
		ThreeTuple<Boolean,String,String> checkTypeResult = RequestParamCheckerUtils.checkParam("listType",request);
		if(!checkTypeResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkTypeResult.errorMsg, response);
			return;
		}
		//start,end请求参数判断
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
		
		String lisType =  RequestParamCheckerUtils.getFullCodeByChannelTypeAndShortCode(channelType,checkTypeResult.value);
		ListCategory listCategory = cacheApi.getListCategoryFromCache(lisType);
		if(null == listCategory){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"榜单类型参数listType非法,不存在该榜单类型", response);
			return;
		}
		
		List<MediaSaleCacheVo> listMediaSaleCacheVo = cacheApi.getMediaSaleByRankingListCode(checkResult.start,checkResult.end,lisType);
		if(null == listMediaSaleCacheVo||listMediaSaleCacheVo.size()==0){
			LogUtil.warn(LOGGER, "该榜单下:"+lisType+" 没有单品销售实体数据");
			sender.fail(ErrorCodeEnum.ERROR_CODE_11003.getErrorCode(),"该栏目下没有单品销售实体数据", response);
			return ;
		}
		long total = cacheApi.getRankingListCount(lisType);
		List<ReturnSaleVo> returnSalelist = ReturnBeanUtils.batchgetReturnSaleListVo(listMediaSaleCacheVo);
		sender.put("saleList", returnSalelist);
		sender.put("count", returnSalelist.size());
		sender.put("name", listCategory.getCategoryName());
		sender.put("listType", lisType);//榜单类型
		sender.put("total", total);//榜单类型
		sender.success(response);
		}

}
