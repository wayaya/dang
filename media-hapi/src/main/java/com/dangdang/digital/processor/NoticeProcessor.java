package com.dangdang.digital.processor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Notice;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.ReturnNoticeVo;

/**
 * 
 * Description: 公告接口
 * All Rights Reserved.
 * @version 1.0  2015年1月6日 下午6:29:46  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapinoticeprocessor")
public class NoticeProcessor extends BaseApiProcessor {
	@Resource
	private ICacheApi  cacheApi; 
	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		
		String channelType = request.getParameter(GlobalParamNameUtils.CHANNEL_TYPE);
		if(RequestParamCheckerUtils.isNullOrEmpty(channelType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入频道参数channelType", response);
			return;
		}
		String lastRequestTime = request.getParameter(GlobalParamNameUtils.LAST_REQUEST_TIME);
		if(RequestParamCheckerUtils.isNullOrEmpty(lastRequestTime)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"请传入参数"+GlobalParamNameUtils.LAST_REQUEST_TIME, response);
			return;
		}
		List<Notice> noticeList = cacheApi.getNoticeList(Long.valueOf(lastRequestTime));
		List<ReturnNoticeVo> returnnoticeList = ReturnBeanUtils.getReturnNoticeVoList(noticeList);
		sender.put("noticeList", returnnoticeList);
		sender.success(response);
	}

}
