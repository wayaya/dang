package com.dangdang.digital.processor;

import static com.dangdang.digital.utils.GlobalParamNameUtils.CHANNEL_TYPE;
import static com.dangdang.digital.utils.RequestParamCheckerUtils.isNullOrEmpty;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.CatetoryMediaSaleVo;
import com.dangdang.digital.vo.CatetoryVo;

/**
 * 
 * Description: media一级分类页面 处理器
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 上午10:54:08  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapimediaCategoryprocessor")
public class MediaCategoryProcessor extends BaseApiProcessor {
	
	@Resource
	ICacheApi  cacheApi;  
	
	@Resource
	ISystemApi  systemApi;
	
	//原创标识
	
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		String withData = request.getParameter("data");
		if(isNullOrEmpty(withData)){
			withData = "0";//默认不反馈数据 0:不反馈分类叶子节点下数据,1:返回叶子节点下销量前3数据
		}
		//频道,对应media分类中某一个分类
		String channelType = request.getParameter(CHANNEL_TYPE);
		if(isNullOrEmpty(channelType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入频道参数:"+CHANNEL_TYPE, response);
			return;
		}
		//start,end请求参数判断
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
		List<CatetoryMediaSaleVo> catetoryVoList = cacheApi.getMediaSaleByCatetoryCode("1".equals(withData)?true:false,checkResult.start,checkResult.end, channelType.toUpperCase());
		
		if(null != catetoryVoList && catetoryVoList.size()>0){
			List<CatetoryVo> cvList = ReturnBeanUtils.getReturnCatetoryVo(catetoryVoList);
			sender.put("catetoryList",cvList);
			sender.success(response);
		}else{
			sender.fail(ErrorCodeEnum.ERROR_CODE_10007.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10007.getErrorMessage(), response);
			return ;
		}
		}
}
