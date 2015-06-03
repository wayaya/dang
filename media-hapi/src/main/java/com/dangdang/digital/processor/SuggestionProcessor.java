package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ISystemApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Suggestion;
import com.dangdang.digital.service.ISuggestionService;
import com.dangdang.digital.utils.GlobalParamNameUtils;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 意见反馈接口
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 上午10:27:18  by 吕翔  (lvxiang@dangdang.com) 创建
 */
@Component("hapisuggestionprocessor")
public class SuggestionProcessor extends BaseApiProcessor{

	/**
	 * 
	 * Description: 意见反馈页面显示的客户联系方式
	 * All Rights Reserved.
	 * @version 1.0  2014年12月23日 上午10:39:40  by 吕翔  (lvxiang@dangdang.com) 创建
	 */
	private static class ContactInformation{
		/**
		 * 多个用;分隔,配置在系统参数里面
		 */
		private String hotLine;
		/**
		 * 多个用;分隔,配置在系统参数里面
		 */
		private String qq;
		
		public ContactInformation(String hotLine,String qq){
			this.hotLine = hotLine;
			this.qq = qq;
		}
		
		public String getHotLine() {
			return hotLine;
		}

		public void setHotLine(String hotLine) {
			this.hotLine = hotLine;
		}

		public String getQq() {
			return qq;
		}

		public void setQq(String qq) {
			this.qq = qq;
		}
	}
	
	@Resource ISystemApi systemApi;
	
	@Resource ISuggestionService  suggestionService;
	
	@Resource(name = "authorityApiFacade")
	
	private IAuthorityApiFacade authorityApiFacade;
	
	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		String opType = request.getParameter("op");
		if(RequestParamCheckerUtils.isNullOrEmpty(opType)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"缺少意见反馈类型参数op", response);
			return;
		}else if(opType.equalsIgnoreCase("get")){
			String hotLine = systemApi.getProperty("suggestion_hotline");
			String qq = systemApi.getProperty("suggestion_qq");
			if(null==hotLine||hotLine.trim().isEmpty()|| null ==qq || qq.trim().isEmpty()){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10005.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10005.getErrorMessage()+":suggestion_hotline,suggestion_qq", response);
				return;
			}
			ContactInformation ci = new ContactInformation(hotLine,qq);
			sender.put("ContactInfo", ci);
			sender.success(response);
			//请求客户电话,
		}else if(opType.equalsIgnoreCase("save")){
		   //保存意见信息
			String token = request.getParameter("token");
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			// 未登录用户直接返回
			Long custId = null;
			String userName = null;
			String nickName =null;
			if (custVo == null || custVo.getCustId() == null) {
//				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),
//						ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
//				return;
				//未登录用户
				userName ="未登录用户";
			}else{
				custId = custVo.getCustId();
				userName = custVo.getUsername();
				nickName = custVo.getNickname();
				
			}
			String advice = request.getParameter(GlobalParamNameUtils.ADVICE);
			
			String platform = request.getParameter(GlobalParamNameUtils.PLATFORM);
			
			if(RequestParamCheckerUtils.isNullOrEmpty(advice)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10006.getErrorCode(),"意见反馈内容为空", response);
				return;
			}
			String clientOs = request.getParameter(GlobalParamNameUtils.CLIENT_OS);//手机系统
			String clientVersionNo = request.getParameter(GlobalParamNameUtils.CLIENT_VERSION_NO);//客户端版本号
			String contactWay = request.getParameter(GlobalParamNameUtils.CONTACT_WAY);
			String deviceType = request.getParameter(GlobalParamNameUtils.DEVICE_TYPE);
			Suggestion  suggestion = new Suggestion();
			suggestion.setCustId(custId);
			suggestion.setUserName(userName);
			suggestion.setNickName(nickName);
			suggestion.setAdvice(advice);
			suggestion.setDeviceType(deviceType);
			suggestion.setContactWay(contactWay);
			suggestion.setClientOs(clientOs);
			suggestion.setPlatform(platform);
			suggestion.setClientVersion(clientVersionNo);
			suggestionService.save(suggestion);
			sender.success(response);
			
		}else{
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),"不存在意见反馈类型参数type", response);
			return;
		}
		
		
	}

}
