package com.dangdang.digital.processor.ucenter;

import java.util.HashMap;
import java.util.Map;

import javacommon.util.ConfigReader;
import javacommon.util.HeadportraitUtil;
import javacommon.util.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.api.service.IUcenterLoginApi;
import com.dangdang.ucenter.enums.UCErrorCodeEnum;
import com.dangdang.ucenter.model.DangdangUser;
import com.dangdang.ucenter.utils.Encrypt;
import com.dangdang.ucenter.vo.LoginResultVO;

/**
 * Description: 当当登录(5.0)
 * All Rights Reserved.
 * @version 1.0  2015年5月28日 下午3:36:22  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapiddLoginprocessor")
public class DDLoginProcessor extends BaseApiProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(DDLoginProcessor.class);
	
	private static final int USER_BIND_MOBILE_NUM = ConfigPropertieUtils.getInteger("user.bind.mobile.num", 20);
	private static final String ADD_KEY = ConfigPropertieUtils.getString("dangdanguser.custid.add.key", "test@#$%");
			
	public static final String EMAIL = "email";//昵称或邮箱登录
	public static final String PHONE = "phone";//手机号登录
	
	@Autowired
	private IUcenterLoginApi ucenterLoginApi;
	
	@Autowired
	private IUcenterApi ucenterApi;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//必传字段
		String username = request.getParameter("userName");
		String password = request.getParameter("passWord");
		String deviceType = request.getParameter("deviceType");
		String deviceNo = get(request, "deviceSerialNo", "");
		String key = request.getParameter("key");
		
		if(!checkParams(username, password, deviceType, deviceNo, key)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		//非必传字段
		String code = request.getParameter("code");
		String loginType = get(request, "loginType", EMAIL);
		if (username.indexOf("@") == -1) {
			loginType = PHONE;
		}
		String pdfkey = request.getParameter("pdfkey");
		//String serverVersion = request.getParameter("serverVersionNo");
		String loginClient = request.getParameter("loginClient");
		String ip = IpUtils.getIpAddr(request);
		
		try {
			Map<String, String> params = this.buildParamsMap(username, password, deviceType, deviceNo, key, code, ip, loginType, pdfkey, loginClient);
			LoginResultVO loginResult = ucenterLoginApi.ddLogin(params);
			if(loginResult == null || MapUtils.isEmpty(loginResult.getDataMap())){
				sender.fail(UCErrorCodeEnum.ERR_60005.getCode(), UCErrorCodeEnum.ERR_60005.getMsg(), response);
				return;
			}
			//登录处理成功与失败标示
			boolean flag = loginResult.getRet();
			if(!flag){
				sender.fail(loginResult.getErrcode(), loginResult.getErrmsg(), response);
				return;
			}else{
				String www_auth_sign = "";
				if ("pcreader".equals(deviceType)) {
					String callback = request.getParameter("callback");
					String redirect_url = request.getParameter("redirectUrl");
					String entrustKey = ConfigReader.get("entrustKey");
					checkParams(redirect_url, callback);

					StringBuffer auth_sign = new StringBuffer();
					auth_sign.append("callback=").append(callback).append("&")
							.append("password=").append(password).append("&")
							.append("redirect_url=").append(redirect_url).append("&")
							.append("username=").append(username).append(entrustKey);
					www_auth_sign = DigestUtils.md5Hex(auth_sign.toString());
				}
				if (StringUtils.isNotBlank(www_auth_sign)) {
					sender.put("www_auth_sign", www_auth_sign);
				}
				
				Map<String, ?> loginMap = loginResult.getDataMap();
				String token = (String) loginMap.get("token");
				DangdangUser user = (DangdangUser) loginMap.get("user");
				Integer bindNum = (Integer) loginMap.get("bindNum");
				if(user == null || StringUtils.isBlank(token)){
					sender.fail(UCErrorCodeEnum.ERR_60005.getCode(), UCErrorCodeEnum.ERR_60005.getMsg(), response);
					return;
				}
				sender.put("token", token);
				sender.put("uniqueKey", getUniqueKey(user));
				sender.put("userPubId", ucenterApi.getUserPubId(user.getId()));
				user.setHeadportrait(ucenterApi.getHeadportrait(user.getDisplayId(), HeadportraitUtil.IMG_SIZE_96_96));
				sender.setCustId(user.getId());
				sender.put("user", user);
				sender.put("unbindNum", USER_BIND_MOBILE_NUM - bindNum);
				sender.success(response);
				return;
			}
		} catch (Exception e) {
			logger.info("dd_login_fail, username:{}|deviceType:{}|deviceNo:{}", new String[] {username, deviceType, deviceNo});
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}
	
	/**
	 * Description: 构建调用当当登录SOA参数map
	 * @Version1.0 2015年5月28日 下午4:07:38 by 代鹏（daipeng@dangdang.com）创建
	 * @param username
	 * @param password
	 * @param deviceType
	 * @param deviceNo
	 * @param key
	 * @param code
	 * @param ip
	 * @param loginType
	 * @param pdfkey
	 * @param loginClient
	 * @return
	 */
	private Map<String, String> buildParamsMap(String username, String password, String deviceType, String deviceNo,
			String key, String code, String ip, String loginType, String pdfkey, String loginClient){
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", username);
		map.put("passWord", password);
		map.put("deviceType", deviceType);
		map.put("deviceNo", deviceNo);
		map.put("key", key);
		map.put("code", code);
		map.put("ip", ip);
		map.put("loginType", loginType);
		map.put("pdfkey", pdfkey);
		map.put("loginClient", loginClient);
		return map;
	}
	
	private String getUniqueKey(DangdangUser user) throws Exception {
		String content = user.getId() + "";
		String sign = ADD_KEY + content;
		return DigestUtils.md5Hex(Encrypt.SHA1(sign));
	}

}
