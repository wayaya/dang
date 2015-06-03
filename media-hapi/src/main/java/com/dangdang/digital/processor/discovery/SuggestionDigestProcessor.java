package com.dangdang.digital.processor.discovery;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Suggestion;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.ISuggestionService;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 翻篇儿意见反馈
 * All Rights Reserved.
 * @version 1.0  2015年3月5日 下午5:03:35  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapisuggestionDigestprocessor")
public class SuggestionDigestProcessor extends BaseApiProcessor {
	
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	
	@Resource
	private ISuggestionService  suggestionService;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//必填项
		String advice = request.getParameter(GlobalParamNameUtils.ADVICE);//建议内容
		String deviceType = request.getParameter(GlobalParamNameUtils.DEVICE_TYPE);//设备类型
		String clientOs = request.getParameter(GlobalParamNameUtils.CLIENT_OS);//手机系统
		String clientVersionNo = request.getParameter(GlobalParamNameUtils.CLIENT_VERSION_NO);//客户端版本号
		//选填
		String token = request.getParameter("token");
		String contactWay = request.getParameter(GlobalParamNameUtils.CONTACT_WAY);
		
		if(!checkParams(deviceType, clientOs, clientVersionNo)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//校验意见反馈内容不能为空
		if(StringUtils.isBlank(advice)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//校验意见反馈内容长度
		if(advice.length() > 500){
			sender.fail(ErrorCodeEnum.ERROR_CODE_22027.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22027.getErrorMessage(), response);
			return;
		}
		
		//电话号码正则表达式
		String mail_regex = "^\\w+((\\.\\w+)|(-\\w+))*\\@[a-zA-Z0-9]+((\\.|-)[a-zA-Z0-9]+)*\\.[a-zA-Z0-9]+$";
		Pattern mail_pattern = Pattern.compile(mail_regex);
		Matcher mail_m = mail_pattern.matcher(contactWay.trim());
		
		//电话号码正则表达式
		String phone_regex = "^1[34578]\\d{9}$";
		Pattern phone_pattern = Pattern.compile(phone_regex);
		Matcher phone_m = phone_pattern.matcher(contactWay.trim());
		
		//校验联系方式有效性
		if(StringUtils.isNotBlank(contactWay)){
			if((!mail_m.matches()) && (!phone_m.matches())){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22025.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22025.getErrorMessage(), response);
				return;
			}
		}
		
		Suggestion  suggestion = new Suggestion();
		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
		if (custVo == null || custVo.getCustId() == null) {
			suggestion.setUserName("未登录用户");
		}else{
			suggestion.setCustId(custVo.getCustId());
			suggestion.setUserName(custVo.getUsername());
			suggestion.setNickName(custVo.getNickname());
		}
		suggestion.setAdvice(advice);
		suggestion.setDeviceType(deviceType);
		suggestion.setContactWay(contactWay);
		suggestion.setClientOs(clientOs);
		suggestion.setPlatform(PlatformSourceEnum.FP_P.getSource());
		suggestion.setClientVersion(clientVersionNo);
		suggestionService.save(suggestion);
		sender.success(response);
		return;
	}

}
