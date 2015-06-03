package com.dangdang.digital.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.DDClick;
import com.dangdang.digital.system.SpringContextHolder;
import com.dangdang.digital.vo.ExtIdParamsVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * DDClick对象帮助类.
 */
public abstract class DDClickUtils {
	private static Logger log = LoggerFactory.getLogger(DDClickUtils.class);
	
	private static final String DDCLICK_NOT_RECORD_ACTIONS = 
			ConfigPropertieUtils.getString("ddclick.not.record.actions", "submitErrorInfo");
	
	private static final String DDCLICK_RECORD_MEMO_ACTIONS = 
			ConfigPropertieUtils.getString("ddclick.record.memo.actions", "submitOperationLog");
	
	private static IAuthorityApiFacade authorityApiFacade = null;
	
	private static Map<String, ExtIdParamsVo> specialIdsMap = null;
	
	/**
	 * 获取DDClick对象,非数据统计processor使用.
	 * @param action 请求的action
	 * @param actionId 从apiConfig获取的actionId
	 * @param request
	 * @return
	 */
	public static DDClick getCommonDDClick(String action, String actionName, 
			HttpServletRequest request) {
		return getDDClick(action, actionName, request, null);
	}
	
	/**
	 * 获取DDClick对象，客户端统计数据使用.
	 * @param action 统计数据对应的action
	 * @param actionId 统计数据对应的actionId
	 * @param request
	 * @param operationMap 请求的统计数据JSONObject
	 * @return
	 */
	public static DDClick getOperateDataDDClick(String action, String actionName, 
			HttpServletRequest request, Map<String, Object> operationMap) {
		return getDDClick(action, actionName, request, operationMap);
	}
	
	private static DDClick getDDClick(String action, String actionName, 
			HttpServletRequest request, Map<String, Object> operationMap) {
		try {
			//判断是否要记录到DDClick中
			if (DDCLICK_NOT_RECORD_ACTIONS.indexOf(action) >= 0) {
				return null;
			}
			
			//获取请求的全部参数
			Map<String, String[]> params = request.getParameterMap();
			Map<String, Object> saveMap = new HashMap<String, Object>();
			for (String key : params.keySet()) {
				String value = params.get(key)[0];	
				if (!"password".equalsIgnoreCase(key.trim().toLowerCase()) && 
						(!"operationlog".equalsIgnoreCase(key.trim().toLowerCase()) || DDCLICK_RECORD_MEMO_ACTIONS.indexOf(action) >= 0)) {
					saveMap.put(key, value);
				}				
			}
			if (operationMap != null) {
				saveMap.putAll(operationMap);
			}
			
			DDClick click = new DDClick();
			click.setActionTime(new Date());
			
			BeanUtils.copyProperties(click, request.getParameterMap());
			
			String operateTimes = request.getParameter("operateTime");
			if ("submitOperationLog".equals(action) && StringUtils.isNotEmpty(operateTimes)) {
				try {
					Long operateTime = Long.parseLong(operateTimes);
					Date date = new Date(operateTime);
					if (date.getYear() > 2010) {
						click.setActionTime(date);
					}
				} catch (Exception e) {
					log.error("时间格式转换错误!", e);
				}
			}
			
			click.setIp(getRequestIp(request));
			click.setParameter(JSON.toJSONString(saveMap));			
						
			if (actionName != null) {
				click.setActionName(actionName);	
				
				setExtIds(actionName, request, click);
				
			} 
			
			click.setActionId(DDClick.NULL_ACTION_ID);
			click.setActionType(DDClick.NULL_ACTION_TYPE);
			
			String orderSource = request.getParameter("orderSource");
			String fromUrl = request.getParameter("from_url");
			if (StringUtils.isNotEmpty(orderSource)) {
				click.setUnionId(Integer.valueOf(orderSource));
			} else if (StringUtils.isNotEmpty(fromUrl)) {
				click.setUnionId(Integer.valueOf(fromUrl));
			}
			if (StringUtils.isNotEmpty(click.getDeviceType())) {
				Integer appId = DeviceTypeUtils.getAppIdByDevicetype(click.getDeviceType().trim());
				if (appId != null) {
					click.setAppId(appId);
				}		
				click.setDeviceType(click.getDeviceType().toLowerCase());
			}
			String deviceSn = get(request, "deviceSerialNo", "");
			if (StringUtils.isNotEmpty(deviceSn) && StringUtils.isEmpty(click.getDeviceSerialNo())) {
				click.setDeviceSerialNo(deviceSn);
			}
			// 添加referType
			String refer = get(request, "referType", "");
			if (StringUtils.isNotEmpty(refer) && StringUtils.isEmpty(click.getRefer())) {
				click.setRefer(refer);
			}
			
			String token = request.getParameter("token");
			if (StringUtils.isNotEmpty(token) && !"\"\"".equals(token) && !"null".equals(token)) {
				click.setToken(token);
				if(authorityApiFacade ==null){
					authorityApiFacade = SpringContextHolder.getBean("authorityApiFacade");
				}
				UserTradeBaseVo vo = authorityApiFacade.getUserInfoByToken(token);
				if(vo!=null){
					Long custId = vo.getCustId();
					if(custId!=null){
						click.setCustId(custId);
					}
				}
			}
			
			String columnType = request.getParameter("columnType");
			if(StringUtils.isNotEmpty(columnType)){
				click.setColumnType(columnType);
			}
			
			String refAction = request.getParameter("refAction");
			if(StringUtils.isNotEmpty(refAction)){
				click.setRefAction(refAction);
			}
			
			String mediaId = request.getParameter("mediaId");
			if(StringUtils.isNotEmpty(mediaId)){
				Long mediaLongId = SafeConvert.convertStringToLong(mediaId, -1);
				if(mediaLongId != -1){
					click.setMediaId(mediaLongId);
				}
			}
			
			String saleId = request.getParameter("saleId");
			if(StringUtils.isNotEmpty(saleId)){
				Long saleLongId = SafeConvert.convertStringToLong(saleId, -1);
				if(saleLongId != -1){
					click.setSaleId(saleLongId);
				}
			}
			
			if ("html5".equals(click.getDeviceType()) && StringUtils.isNotEmpty(click.getPermanentId())) {
				click.setDeviceSerialNo(click.getPermanentId());
			}
			
			//处理添加购物车对应的productIds到ddclick数据库
			if (click.getProductId() == null) {
				String productIds = request.getParameter("productIds");
				if (StringUtils.isNotEmpty(productIds)) {
					if (productIds.indexOf(",") > 0) {
						productIds = productIds.substring(0, productIds.indexOf(","));
					}
					click.setProductId(Integer.valueOf(productIds));					
				}
			}
			//添加offline_action_time字段在ddclick中
			String offlineActionTime = request.getParameter("offline_action_time");
			if (StringUtils.isNotEmpty(offlineActionTime)) {
				click.setOfflineActionTime(offlineActionTime);
			}			
			return click;
		} catch (Exception ex) {
			log.error("ddclick获取出现错误", ex);
		}
		return null;
	}
	
	private static void setExtIds(String actionName, HttpServletRequest request, DDClick click) {
		
		if(specialIdsMap == null){
			specialIdsMap = SpringContextHolder.getBean("specialIdsMap");
		}
		
		if(StringUtils.isEmpty(actionName)){
			return;
		}
		
		if(specialIdsMap!=null && specialIdsMap.keySet().contains(actionName)){
			ExtIdParamsVo vo = specialIdsMap.get(actionName);
			click.setExtId1(request.getParameter(vo.getExtIdParam1()));
			click.setExtId2(request.getParameter(vo.getExtIdParam2()));
			click.setExtId3(request.getParameter(vo.getExtIdParam3()));
		}
	}

	/**
	 * 如果参数中包含ipParam并且IpUtils方法获取的Ip相等于本机ip,返回ipParam;否则返回IpUtils获取的Ip.
	 */
	private static String getRequestIp(HttpServletRequest request) throws Exception{
		String getIp = IpUtils.getIpAddr(request);
		String ipParam = request.getParameter("ipParam");
		String remoteAddr = request.getRemoteAddr();
		if (getIp.equals(remoteAddr) && StringUtils.isNotEmpty(ipParam)) {
			return ipParam;
		}
		return getIp;
	}

	/**
	 * 获取参数.
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String get(HttpServletRequest request, String key, String defaultValue) {
		if ("deviceSerialNo".equals(key) || "deviceSn".equals(key)) {
			if (StringUtils.isNotBlank(request.getParameter("deviceSn"))) {
				return request.getParameter("deviceSn");
			} else if (StringUtils.isNotBlank(request.getParameter("deviceSerialNo"))) {
				return request.getParameter("deviceSerialNo");
			} else if (StringUtils.isNotBlank(request.getParameter("macAddr"))) {
				return request.getParameter("macAddr");
			} else {
				return defaultValue;
			}
		}
		return StringUtils.isBlank(request.getParameter(key)) ? defaultValue : request.getParameter(key);
	}
}
