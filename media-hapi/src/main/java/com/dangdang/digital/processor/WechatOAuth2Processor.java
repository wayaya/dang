package com.dangdang.digital.processor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IRedPacketService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.HttpUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.thirduser.api.IThirdUser;
import com.dangdang.thirduser.api.bean.ThirdUser;

/**
 * 
 * Description: 微信抢红包，静默授权、非静默授权、注册、登录接口
 * All Rights Reserved.
 * @version 1.0  2015年3月17日 下午3:22:01  by 魏嵩（weisong@dangdang.com）创建
 */
@Component("hapiwechatOAuthprocessor")
public class WechatOAuth2Processor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatOAuth2Processor.class);
	private final String appId = ConfigPropertieUtils.getString("media.oauth.floor12.appId","4");
	private final String domainPrefix = ConfigPropertieUtils.getString("media.oauth.wechat.mydomainPrefix","http://e.dangdang.com");
	private final String wechatAppId = ConfigPropertieUtils.getString("media.oauth.wechat.wechatAppId","wxdab04e669f4618e8");
	private final String wechatAppSecret = ConfigPropertieUtils.getString("media.oauth.wechat.wechatAppSecret","f6f3a21c5020e5d53089cb8bc352c833");
	private final String eapiAddrs = ConfigPropertieUtils.getString("media.oauth.wechat.eapiAddrs","http://e.dangdang.com");
	private final String redPacketPagePrefix = ConfigPropertieUtils.getString("media.oauth.wechat.redPacketPagePrefix","http://e.dangdang.com/block_yc_new_redbag_login.htm");
	
	@Resource
	private IRedPacketService redPacketService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IThirdUser thirdUserApi;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		if(state==null){
			state = "";
		}
		if(StringUtils.isEmpty(code)){
			returnErrorPage(ErrorCodeEnum.ERROR_CODE_22011.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22011.getErrorMessage(), response);
			return;
		}
		
		String act = request.getParameter("act");
		String deviceType = request.getParameter("deviceType");
		String redPacketIdStr = request.getParameter("redPacketId");
		String channelId = request.getParameter("channelId");
		if(StringUtils.isEmpty(act)){
			returnErrorPage(ErrorCodeEnum.ERROR_CODE_22011.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22011.getErrorMessage(), response);
			return;
		}
		
		if("redPacket".equals(act)){
			
			if(StringUtils.isEmpty(redPacketIdStr)){
				returnErrorPage(ErrorCodeEnum.ERROR_CODE_22011.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22011.getErrorMessage(), response);
				return;
			}
			
			if(StringUtils.isEmpty(deviceType)){
				returnErrorPage(ErrorCodeEnum.ERROR_CODE_22011.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22011.getErrorMessage(), response);
				return;
			}
			
			if(StringUtils.isEmpty(channelId)){
				returnErrorPage(ErrorCodeEnum.ERROR_CODE_22011.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22011.getErrorMessage(), response);
				return;
			}
		}
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		StringBuilder accessTokenUrl = new StringBuilder();
		accessTokenUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		accessTokenUrl.append(wechatAppId);
		accessTokenUrl.append("&secret=");
		accessTokenUrl.append(wechatAppSecret);
		accessTokenUrl.append("&code=");
		accessTokenUrl.append(code);
		accessTokenUrl.append("&grant_type=authorization_code");
		
		String accessTokenResultString = HttpUtils.getContent(accessTokenUrl.toString());
		Map<String, Object> accessTokenResultMap = JSON.parseObject(accessTokenResultString, Map.class);
		
		if(accessTokenResultMap.get("errcode")!=null){
			LOGGER.error("wechat accessToken request failed:"+accessTokenResultMap.get("errcode")+"|"+accessTokenResultMap.get("errmsg")+"|"+code);
			returnErrorPage(ErrorCodeEnum.ERROR_CODE_22012.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22012.getErrorMessage(), response);
			return;
		}
		
		//如果没有报错，那么判断scope是base(静默)还是userinfo(非静默)
		
		String scope = (String)accessTokenResultMap.get("scope");
		String openId = (String)accessTokenResultMap.get("openid");
		if("snsapi_base".equals(scope)){
			
			LOGGER.info("静默授权");
			//调用12楼#69接口，查询 openId和appId对应的unionId、custId是否存在， 如果存在，直接进入抢红包逻辑
			
			ThirdUser thirdUser = thirdUserApi.getThirdUserInfo(openId, "200", appId, "");
			LOGGER.info("getThirdUserInfo result:"+thirdUser.getErrorCode()+"|"+thirdUser.getStatusCode()+"|"+openId+"|");
			String errorCode = thirdUser.getErrorCode();
			boolean exists = false;
			if("0".equals(errorCode)){
				exists = true;
			}
			if(exists && "redPacket".equals(act)){
				LOGGER.info("静默授权 exist");
				String unionId = thirdUser.getUnionId();
				loginAndRedirect(response, deviceType, redPacketIdStr, channelId, unionId);
				return;
			}else{
				//如果不存在，返回客户端，触发非静默授权
				
				LOGGER.info("静默授权not exist");
				
				StringBuilder returnUriSb = new StringBuilder();
				returnUriSb.append(domainPrefix);
				returnUriSb.append("/media/api.go?action=wechatOAuth&act=");
				returnUriSb.append(act);
				returnUriSb.append("&redPacketId=");
				returnUriSb.append(URLEncoder.encode(redPacketIdStr, "UTF-8"));
				returnUriSb.append("&deviceType=");
				returnUriSb.append(deviceType);
				returnUriSb.append("&channelId=");
				returnUriSb.append(channelId);
				String returnUri = URLEncoder.encode(returnUriSb.toString(), "UTF-8");
				
				String wechatOAuthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wechatAppId+"&redirect_uri="+returnUri+"&response_type=code&scope=snsapi_userinfo&state="+state;
				response.sendRedirect(wechatOAuthUrl);
				return;
			}
			
		}else if("snsapi_userinfo".equals(scope)){
			
			LOGGER.info("非静默授权");
			//调用12楼#69接口，传入unionId， appId， openId， 去补充、注册用户，拿到返回结果
			String unionId = (String)accessTokenResultMap.get("unionid");
			//此查询接口，如果没有appId和openId对应unionId的关系，则会自动补充这个关系
			ThirdUser thirdUser = thirdUserApi.getThirdUserInfo(openId, "200", appId, unionId);
			LOGGER.info("getThirdUserInfo result:"+thirdUser.getErrorCode()+"|"+thirdUser.getStatusCode()+"|"+openId+"|"+unionId);
			String errorCode = thirdUser.getErrorCode();
			boolean exists = false;
			if("0".equals(errorCode)){
				exists = true;
			}
			//如果存在则直接进入抢红包逻辑
			if(exists && "redPacket".equals(act)){
				LOGGER.info("非静默授权 exist");
				loginAndRedirect(response, deviceType, redPacketIdStr, channelId, unionId);
				return;
			}else{
				
				String emailAppendix = "@weixin_user.com";
				String email = unionId.substring(0, 10)+"_"+RandomStringUtils.random(8, true, true)+emailAppendix;
				String password = RandomStringUtils.random(8, true, true);
				//注册，然后进入抢红包逻辑
				LOGGER.error("account does not exist, now registering:"+unionId+"|"+openId+"|"+email+"|"+password+"|"+deviceType+"|"+request.getRequestURL().toString());
				ThirdUser thirdUserRegister = thirdUserApi.registryForThird(openId, "200", appId, unionId, 
						email, password, "1", "0", request.getRequestURL().toString(), "1", deviceType);
				
				if("0".equals(thirdUserRegister.getErrorCode())){
					loginAndRedirect(response, deviceType, redPacketIdStr, channelId, unionId);
					return;
				}else{
					LOGGER.error("register failed:"+thirdUserRegister.getErrorCode()+"|"+thirdUserRegister.getStatusCode()+"|"+unionId+"|"+openId);
					returnErrorPage(ErrorCodeEnum.ERROR_CODE_22013.getErrorCode()+"", ErrorCodeEnum.ERROR_CODE_22013.getErrorMessage(), response);
					return;
				}
			}
		}
	
	}

	private void loginAndRedirect(HttpServletResponse response,
			String deviceType, String redPacketIdStr, String channelId,
			String unionId) throws UnsupportedEncodingException, IOException {
		
		//1. 登录用户 以拿到token
		String eapiThirdLoginUrl = eapiAddrs+"/mobile/api2.do?action=thridLogin&third_id=200&cust_third_id="+unionId+
				"&order_source="+channelId+"&key=OIUX&isUseCommonAppKey=1&returnType=json&deviceType="+deviceType+"&deviceSerialNo=html5";
		String loginResult = HttpUtils.getContent(eapiThirdLoginUrl);
		Map<String, Object> loginResultMap = JSON.parseObject(loginResult, Map.class);
		Map<String, Object> data = (Map<String, Object>)loginResultMap.get("data");
		String token = (String)data.get("token");
		
		LOGGER.info("loginResult :"+loginResult);
		
		//2. 把token和加密后的红包id拼好，并redirect用户到抢红包结果h5页面，这个页面会自动发起已有的抢红包流程 BAU
		StringBuilder redirectUrl = new StringBuilder();
		redirectUrl.append(redPacketPagePrefix);
		redirectUrl.append("?imgUrl=&from=timeline&isappinstalled=1&redPackets=");
		redirectUrl.append(URLEncoder.encode(redPacketIdStr, "UTF-8"));
		redirectUrl.append("&back=true");
		redirectUrl.append("&token=");
		redirectUrl.append(token);
		redirectUrl.append("&from=timeline&isappinstalled=1");
		response.sendRedirect(redirectUrl.toString());
	}
	
	private void returnErrorPage(String errorCode, String errorMessage, HttpServletResponse response ) throws Exception{
		
		StringBuilder redirectUrl = new StringBuilder();
		redirectUrl.append(redPacketPagePrefix);
		redirectUrl.append("?imgUrl=&errorCode=");
		redirectUrl.append(errorCode);
		redirectUrl.append("&errorMessage=");
		redirectUrl.append(errorMessage);
		redirectUrl.append("&back=true");
		redirectUrl.append("&from=timeline&isappinstalled=");
		response.sendRedirect(redirectUrl.toString());
	}
	
}
