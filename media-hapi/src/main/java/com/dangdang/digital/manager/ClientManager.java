package com.dangdang.digital.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javacommon.util.CollectionUtils;
import javacommon.util.ConfigReader;
import javacommon.util.DateTimeUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.model.DangdangUser;

/**
 * 
 * @author yangming
 * 
 */
@Component
@Transactional
public class ClientManager {

	public static final String GET_CUSTOMER_POINT_ACTION = "action.customerpoint.get";
	public static final String REDUCE_CUSTOMER_POINT_ACTION = "action.customerpoint.reduce";
	public static final String REGISTER_USER_WEBSERVICE_ACTION = "action.customer.registerservice";
	public static final String OTHER_USER_WEBSERVICE_ACTION = "action.customer.otherservice";
	public static final String REGISTER_USER_TYPE_INITIATIVE = "registertype.initiative";
	public static final String REGISTER_USER_FROMTYPE_EMAIL = "registerfromtype.email";
	public static final String REGISTER_USER_FROMTYPE_PHONE = "registerfromtype.phone";
	public static final String REGISTION_TYPE_EMAIL = "registion_type.email";
	public static final String REGISTION_TYPE_PHONE = "registion_type.phone";
	public static final String LOGIN_USER_APPKEY = "user.appkey";
	public static final String NEW_DANGDANG_REGISTER = "new.dangdang.register";
	public static final String ACTION_NEW_LOGIN = "action.new.login";
	public static final String ACTION_NEW_LOGINPHONE = "action.new.loginPhone";
	public static final String ACTION_CHECK_USER = "action.checkUser";
	public static final String ACTION_CLEAR_SESSION = "action.clearSession";
	public static final String ACTION_CHANGE_USER_PASSWORD = "action.changeUserPassword";
	public static final String ACTION_GET_USER_INFO = "action.getUserInfo";
	public static final String ACTION_GET_USER_PROFILE = "action.getUserProfile";
	public static final String ACTION_CHANGE_USER_PROFILE = "action.changeUserProfile";

	public static final String DANDGANG_E_DOMAIN = ConfigReader
			.get("dangdang.e.domain");
	private static Logger logger = LoggerFactory.getLogger(ClientManager.class);

	/**
	 * 验证用户是否登录.
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	public Map isLogin(final String userCookie) {
		Map<String, Object> user = new HashMap<String, Object>();
		if (true) {
			user.put("customerid", 316821);
			user.put("display_id", 100586148139L);
			user.put("email", "mazheng@btamail.net.cn");
			user.put("nickname", "qincai929");
		} // TODO
		return user;
	}

	/**
	 * 构建User. 用于返回参数为custid的情况
	 * 
	 * @param doc
	 * @param user
	 * @param strInfo
	 * @throws Exception
	 */
	public static void fillUser(final JSONObject jsonObj,
			final DangdangUser user) throws Exception {
		String id = jsonObj.getString("custid");
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("custid为空");
		}
		String displayId = jsonObj.getString("display_id");
		String email = jsonObj.getString("email");
		String nickName = jsonObj.getString("nickname");
		String registerDate = jsonObj.getString("register_date");
		String phone = jsonObj.getString("mobile_phone");
		String vipType = jsonObj.getString("viptype");
		if (StringUtils.isNotBlank(registerDate)) {
			try {
				user.setRegisterDate(DateTimeUtils.parse(registerDate,
						"yyyy-MM-dd HH:mm:ss"));
			} catch (Exception e) {
				logger.error("设置用户注册日期失败.", e);
			}
		}

		user.setId(Long.valueOf(id));
		if (StringUtils.isNotBlank(displayId)) {
			user.setDisplayId(Long.valueOf(displayId));
		}
		user.setEmail(email);
		user.setNickName(nickName);
		user.setPhone(phone);
		user.setVipType(vipType);
	}

	/**
	 * 构建User. 用于返回参数为customerid的情况
	 * 
	 * @param doc
	 * @param user
	 * @param strInfo
	 * @throws Exception
	 */
	public static void fillUserByCustomerId(final JSONObject jsonObj,
			final DangdangUser user) throws Exception {
		String id = jsonObj.getString("customerid");
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("customerid为空");
		}
		String displayId = jsonObj.getString("display_id");
		String email = jsonObj.getString("email");
		String nickName = jsonObj.getString("nickname");
		String phone = jsonObj.getString("mobilephone");
		String vipType = jsonObj.getString("viptype");

		user.setId(Long.valueOf(id));
		if (StringUtils.isNotBlank(displayId)) {
			user.setDisplayId(Long.valueOf(displayId));
		}
		user.setEmail(email);
		user.setNickName(nickName);
		user.setPhone(phone);
		user.setVipType(vipType);
	}

	/**
	 * 修改用户信息
	 * 
	 * Description: 
	 * @Version1.0 2014-11-25 上午11:33:18 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param custId
	 * @param keyword
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static int changeUserProfile(Long custId, String keyword,
			String content) throws Exception {
		Map<String, String> actionMap = null;
		Map<String, Object> params = CollectionUtils.buildMap("custid", custId,
				"keyword", keyword, "content", content);

		try {
			actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(
					ACTION_CHANGE_USER_PROFILE, params);
		} catch (Exception e) {
			logger.error("获取修改用户信息地址失败", e);
			return 50;
		}

		String strInfo = InterfaceManager.getUrlInterfaceResult(actionMap);
		logger.debug("验证返回参数：" + strInfo);

		JSONObject returnObj = JSONObject.parseObject(strInfo);

		if (returnObj == null) {
			return 51;
		}

		int errorCode = 1; // 1-失败 0-成功
		if (returnObj.containsKey("error_code")) {
			errorCode = returnObj.getIntValue("error_code");
		}
		
		if (returnObj.containsKey("errorCode")) {
			errorCode = returnObj.getIntValue("errorCode");
		}

		if (errorCode != 0) {
			logger.error("获取用户信息，custId：" + custId);
			logger.error("获取失败：\r\n" + strInfo);

		} else {
			logger.info("获取用户信息，custId：" + custId);
			logger.info("获取成功：\r\n" + strInfo);
		}

		return errorCode;
	}

	/**
	 * 注销时清除redis中的信息 add by wangguanhua 2014-7-9
	 * 
	 * @param sessionID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String clearSessionByRedisAPI(String sessionID) {

		Map<String, String> actionMap = null;
		Map<String, Object> params = CollectionUtils.buildMap("sessionID",
				sessionID);

		try {
			actionMap = InterfaceManager.getParamCapsuleOfHttpUrl(
					ACTION_CLEAR_SESSION, params);
		} catch (Exception e) {
			logger.error("获取清除session地址失败", e);
			return null;
		}

		String strInfo = InterfaceManager
				.getUrlInterfaceResultWithMethod(actionMap);
		logger.debug("验证返回参数：" + strInfo);

		return strInfo;
	}

	public static String getAppKey(String deviceType) {
		String appKeys = ConfigReader.get(LOGIN_USER_APPKEY);
		if (StringUtils.isEmpty(appKeys)) {

		}
		String[] arr_appKey = appKeys.split("/");

		if (null != arr_appKey && arr_appKey.length > 0) {
			for (int j = 0; j < arr_appKey.length; j++) {
				String temp_appKey = arr_appKey[j];
				String arr[] = temp_appKey.split(";");
				if (deviceType.equals(arr[0])) {
					return temp_appKey.split(";")[1];
				}
			}
		}
		return null;
	}

	/**
	 * 解unicode
	 * 
	 * @param str
	 * @return
	 */
	private static String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			char ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), "" + ch);
		}
		return str;
	}
	
	/**
	 * 验证当前IP是否在黑名单中.watchdog无人维护，暂时不实现此方法
	 * Description: 
	 * @Version1.0 2015-2-3 下午04:57:42 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param ip
	 * @return
	 */
	public static boolean isBlackIp(String ip){
		return false;
	}
}
