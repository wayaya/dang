package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;

/**
 * 
 * Description: media叶子分类页面各维度排名处理器
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 上午10:54:08  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapimediaCategoryLeafprocessor")
public class MediaCategoryLeafProcessor extends BaseApiProcessor {
	
	@Resource
	ICacheApi  cacheApi;  
	
	@Resource
	IMediaStatisticsService  mediaStatisticsService;
	
	private boolean isNullOrEmpty(String value){
		return value==null || value.trim().isEmpty();
	}
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//分类标识
		String categoryType  =  request.getParameter("category");
		//维度标识
		String dimensionType  =  request.getParameter("dimension");
		//检查start,end参数是否全法
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
		// 请求查询条数
		if (isNullOrEmpty(categoryType)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), "分类二级页面类型参数category不存在或非法,必须是合法存在的类型标识",
					response);
			return;
		}
		if (isNullOrEmpty(dimensionType)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), "分类二级页面类型参数dimension不存在或非法,必须是合法存在的维度标识",
					response);
			return;
		}
		Catetory category = cacheApi.getCatetoryFromCache(categoryType);
		if (Constans.CATEGORY_STATUS_DISABLE.equals(category.getStatus())) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_11009.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_11009.getErrorMessage(), response);
			return;

		}

		//media 二级分类维度标识
		String lowerCaseDimensionType= dimensionType.toLowerCase();
		List<MediaSaleCacheVo> mediaSaleCacheVoList = cacheApi.getCategoryMediaSaleByCodeFromCache(checkResult.start, checkResult.end,categoryType, lowerCaseDimensionType);
		if(null == mediaSaleCacheVoList||mediaSaleCacheVoList.size()==0){
			LogUtil.warn(LOGGER, "该分类下" + categoryType + " 没有销售主体数据");
			sender.fail(ErrorCodeEnum.ERROR_CODE_11003.getErrorCode(),"该分类下"+categoryType+"没有销售主体数据", response);
			return ;
		}
		List<ReturnSaleVo> returnSalelist = ReturnBeanUtils.batchgetReturnSaleListVo(mediaSaleCacheVoList);
		long total = cacheApi.getCategoryCount(categoryType, lowerCaseDimensionType);
		sender.put("saleList", returnSalelist);//media数据信息
		sender.put("code", categoryType);//分类标识
		sender.put("dimension", dimensionType);//查询维度
		sender.put("total",total);//本次返回media数量
		sender.put("count",mediaSaleCacheVoList.size());//本次返回media数量
		//将最终数据发出
		sender.success(response);
		}
}
		
