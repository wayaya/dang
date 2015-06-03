package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ThreeTuple;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnAnnouncementVo;

/**
 * 
 * Description: 公告接口
 * All Rights Reserved.
 * @version 1.0  2015年1月6日 下午6:29:46  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapiannouncementprocessor")
public class AnnouncementProcessor extends BaseApiProcessor {
	
	@Resource
	private ICacheApi  cacheApi; 
	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		
		String channelType = request.getParameter(GlobalParamNameUtils.CHANNEL_TYPE);
		if(RequestParamCheckerUtils.isNullOrEmpty(channelType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入频道参数channelType", response);
			return;
		}
		//公告类型标识
		ThreeTuple<Boolean,String,String> checkTypeResult = RequestParamCheckerUtils.checkParam("type",request);
		if(!checkTypeResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkTypeResult.errorMsg, response);
			return;
		}
		
		String fullCode =channelType+"_"+checkTypeResult.value;
		
		AnnouncementsCategory ac = cacheApi.getAnnouncementsCategoryFromCache(fullCode);
		if(null == ac){
			sender.fail(ErrorCodeEnum.ERROR_CODE_11007.getErrorCode(),ErrorCodeEnum.ERROR_CODE_11007.getErrorMessage(), response);
		}
		List<AnnouncementsContent> acList = cacheApi.getAnnouncementsContentFromCache(ac.getCategoryCode());
		ReturnAnnouncementVo rav =	ReturnBeanUtils.getReturnAnnouncementVo(ac,acList);
		sender.put("announcement", rav);
		sender.success(response);
	}

}
