package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;

import javacommon.util.IpUtils;
import javacommon.util.JsonUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.intecepter.MultiActionRequestWrapper;
import com.dangdang.digital.job.MultiActionJobResult;
import com.dangdang.digital.system.SpringContextHolder;
import com.dangdang.digital.utils.JsonResultSender2;
import com.dangdang.digital.utils.ResultSender;

/**
 * 同时处理多个请求，可处理依赖关系及非依赖关系 Description: All Rights Reserved.
 * 
 * @version 1.0 2014-8-21 上午10:07:06 by 王冠华（wangguanhua@dangdang.com）创建
 */
@Component("hapimultiActionprocessor")
public class MultiActionProcessor extends BaseApiProcessor {

	@Autowired
	private UnknownApiProcessor unknownApiProcessor;

	@Resource
	private ThreadPoolTaskExecutor taskExecutorForMultiAction;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {

		String field = request.getParameter("field");
		JSONObject multiAction = null;
		try {
			multiAction = JSONObject.parseObject(field);
		} catch (JSONException e) {
			log.error("解析json出错", e);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage()
					+ "解析field参数json出错！field:" + field, response);
			return;
		}
		
		if (multiAction == null) {
			throw ApiException.invalidParams();
		}

		// 依赖关系即每一个action执行之前必须保证前面的action执行成功
		JSONArray dependActions = multiAction.getJSONArray("dependActions");
		// 非依赖关系
		JSONArray noDependActions = multiAction.getJSONArray("noDependActions");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> errorMap = new HashMap<String, Object>();
		Map<String, Object> dependParamValues = new HashMap<String, Object>();
		String alias = null;
		String returnKey = null;
		String actionName = null;
		JSONObject parseParam = null;
		JSONObject dependAction = null;
		JSONObject noDependAction = null;
		Map<String, String> dependParamKeys = null;
		Map<String, String> pubParamMap = new HashMap<String, String>();
		ResultSender tmpSender;
		Map<String, String> paramMap;
		HttpServletRequest tmpRequest;
		// 多线程执行结果列表
		List<Future<MultiActionJobResult>> futureList = new ArrayList<Future<MultiActionJobResult>>();

		// 添加公共参数
		addPublicParametersV2(request, pubParamMap);

		// 处理依赖关系action
		if (dependActions != null) {
			for (int i = 0; i < dependActions.size(); i++) {
				dependAction = dependActions.getJSONObject(i);
				actionName = dependAction.getString("action");
				alias = dependAction.getString("alias");
				returnKey = alias == null ? actionName : alias;
				parseParam = dependAction.getJSONObject("parseParams");
				paramMap = new HashMap<String, String>();

				// 封装依赖参数key
				dependParamKeys = addDependParamKey(parseParam);
				// 公共参数
				paramMap.putAll(pubParamMap);
				// 私有参数
				addPrivateParameters(paramMap, dependAction);
				// 依赖参数
				addPrivateParameters(paramMap, dependParamValues);
				// 包装request,重写request.getParameter方法
				tmpRequest = new MultiActionRequestWrapper(request, paramMap);
				// 获取sender实例
				tmpSender = JsonResultSender2.getInstance();
				// 执行processor
				sendRequest(actionName, tmpRequest, tmpSender);
				// 获取执行结果
				String returnJson = tmpSender.getReturnJson();
				// 处理依赖参数
				if (dependParamKeys != null && !dependParamKeys.isEmpty()) {
					processDependResult(returnJson, dependParamKeys, dependParamValues);
				}

				// 依赖关系的只返回最后一个返回结果,出错停止执行
				if (checkError(errorMap, returnKey, returnJson) || i == dependActions.size() - 1) {
					try {
						Map<?, ?> map = (Map<?, ?>) JsonUtils.toObject(returnJson, Map.class);
						resultMap.put(returnKey, map);
					} catch (Exception e) {
						resultMap.put(returnKey, returnJson);
					}
					break;
				}
			}
		}

		// 处理非依赖关系action
		if (noDependActions != null) {
			for (int i = 0; i < noDependActions.size(); i++) {
				noDependAction = noDependActions.getJSONObject(i);
				actionName = noDependAction.getString("action");
				alias = noDependAction.getString("alias");
				returnKey = alias == null ? actionName : alias;
				paramMap = new HashMap<String, String>();
				// 私有参数
				addPrivateParameters(paramMap, noDependAction);
				// 公共参数
				paramMap.putAll(pubParamMap);
				// 包装request,重写request.getParameter方法
				tmpRequest = new MultiActionRequestWrapper(request, paramMap);
				// 获取sender实例
				tmpSender = JsonResultSender2.getInstance();
				// 执行processor
				sendRequest(actionName, tmpRequest, tmpSender);
				// 获取执行结果
				String returnJson = tmpSender.getReturnJson();
				// 获取失败结果集
				checkError(errorMap, returnKey, returnJson);

				// 非依赖关系的action返回所有结果
				try {
					Map<?, ?> map = (Map<?, ?>) JsonUtils.toObject(returnJson, Map.class);
					resultMap.put(returnKey, map);
				} catch (Exception e) {
					resultMap.put(returnKey, returnJson);
				}
			}
		}

//		// 处理非依赖关系action
//		if (noDependActions != null) {
//			for (int i = 0; i < noDependActions.size(); i++) {
//				// 获取json对象
//				noDependAction = noDependActions.getJSONObject(i);
//				// 获取sender实例
//				tmpSender = JsonResultSender2.getInstance();
//				MultiActionJob multiActionJob = new MultiActionJob(noDependAction, request, tmpSender,
//						unknownApiProcessor, pubParamMap);
//				Future<MultiActionJobResult> future = taskExecutorForMultiAction.submit(multiActionJob);
//				futureList.add(future);
//
//			}
//		}
//		// 获取多线程执行结果放入Map
//		for (Future<MultiActionJobResult> future : futureList) {
//			MultiActionJobResult result = future.get();
//			tmpSender = result.getTmpSender();
//			returnKey = result.getReturnKey();
//			// 获取执行结果
//			String returnJson = tmpSender.getReturnJson();
//			// 获取失败结果集
//			checkError(errorMap, returnKey, returnJson);
//
//			// 非依赖关系的action返回所有结果
//			try {
//				Map<?, ?> map = (Map<?, ?>) JsonUtils.toObject(returnJson, Map.class);
//				resultMap.put(returnKey, map);
//			} catch (Exception e) {
//				resultMap.put(returnKey, returnJson);
//			}
//		}

		if (errorMap.isEmpty()) {
			sender.put("result", resultMap);
			sender.send(response);
		} else {
			sender.putErrorInfo("errors", errorMap);
			sender.putErrorInfo("result", resultMap);
			sender.multiActionPartFail(response);
		}
	}

	/**
	 * 执行processor Description:
	 * 
	 * @Version1.0 2014-11-14 上午10:21:22 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param actionName
	 * @param tmpRequest
	 * @param tmpSender
	 * @throws Exception
	 */
	private void sendRequest(String actionName, HttpServletRequest tmpRequest, ResultSender tmpSender) throws Exception {
		ApiProcessor processor;
		try {
			try {
				processor = (ApiProcessor) SpringContextHolder.getBean("hapi" + actionName + "processor");
			} catch (NoSuchBeanDefinitionException e) {
				processor = null;
			}
			if (processor == null) {
				processor = unknownApiProcessor;
			}
			processor.handle(tmpRequest, null, tmpSender, actionName);
		} catch (RuntimeException ex) {
			log.error("移动接口报错runtime：", ex);
			ApiException ae = MobileApiException.otherError();
			if (ex.getCause() != null) {
				ae = MobileApiException.netWorkDataError();
			}
			tmpSender.fail(ae.getCode(), ae.getMessage(), null);
		} catch (Exception e) {
			log.error("移动接口报错：", e);
			ApiException ae = MobileApiException.otherError();
			if (e instanceof ApiException) {
				ae = (ApiException) e;
			}
			tmpSender.fail(ae.getCode(), ae.getMessage(), null);
		}
	}

	/**
	 * Description:从返回值中获取依赖参数，加入到map中供下次执行action时追加参数
	 * 
	 * @Version1.0 2014-8-22 下午07:06:11 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param result
	 * @param dependParamKeys
	 * @param dependParamValues
	 */
	private void processDependResult(String result, Map<String, String> dependParamKeys,
			Map<String, Object> dependParamValues) {
		JSONObject resultObj = JSONObject.parseObject(result);
		if (resultObj == null) {
			return;
		}
		JSONObject data = resultObj.getJSONObject("data");
		Set<Entry<String, String>> set = dependParamKeys.entrySet();
		for (Entry<String, String> entry : set) {
			if (entry != null) {
				String fromKey = entry.getKey();
				String toKey = entry.getValue();
				Object value = this.getJsonValue(fromKey, data);
				dependParamValues.put(toKey, value);
			}
		}
	}

	private Object getJsonValue(String key, JSONObject json) {
		if (StringUtils.isNotEmpty(key)) {
			String[] keys = StringUtils.split(key, ".", 2);
			// 首先判断是否是有内部对象调用
			if (keys.length == 1) {
				String subKey = keys[0];
				int index1 = StringUtils.indexOf(subKey, '[');
				int index2 = StringUtils.indexOf(subKey, ']');
				// 其次判断是否有数组
				if (index1 == -1 || index2 == -1) {
					return json.get(subKey);
				}
				// 排除数组索引得到真正的key
				String realKey = StringUtils.substring(subKey, 0, index1);
				// 得到数组下表
				try {
					Integer num = Integer.valueOf(StringUtils.substring(subKey, index1 + 1, index2));
					JSONArray jsonArray = json.getJSONArray(realKey);
					// 得到数组内容
					return jsonArray.get(num);
				} catch (NumberFormatException e) {
					return null;
				}
			} else {
				String subKey = keys[0];
				String nextKey = keys[1];
				int index1 = StringUtils.indexOf(subKey, '[');
				int index2 = StringUtils.indexOf(subKey, ']');
				// 其次判断是否有数组
				if (index1 == -1 || index2 == -1) {
					JSONObject newJson = json.getJSONObject(subKey);
					// 递归调用
					return getJsonValue(nextKey, newJson);
				}
				// 排除数组索引得到真正的key
				String realKey = StringUtils.substring(subKey, 0, index1);
				// 得到数组下表
				try {
					Integer num = Integer.valueOf(StringUtils.substring(subKey, index1 + 1, index2));
					JSONArray jsonArray = json.getJSONArray(realKey);
					// 得到数组内容
					JSONObject newJson = jsonArray.getJSONObject(num);
					// 递归调用
					return getJsonValue(nextKey, newJson);
				} catch (NumberFormatException e) {
					return null;
				}
			}

		}
		return null;
	}

	/**
	 * Description:处理依赖参数key
	 * 
	 * @Version1.0 2014-8-22 下午06:56:55 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param keyMap
	 * @param parseParams
	 */
	private Map<String, String> addDependParamKey(JSONObject parseParams) {
		if (parseParams == null) {
			return null;
		}
		Map<String, String> keyMap = new HashMap<String, String>();
		Set<Entry<String, Object>> set = parseParams.entrySet();
		for (Entry<String, Object> entry : set) {
			if (entry != null) {
				keyMap.put(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return keyMap;
	}

	/**
	 * Description:检查错误结果，如果有则放入errorMap中
	 * 
	 * @Version1.0 2014-8-21 上午11:49:20 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param errorMap
	 * @param key
	 * @param result
	 * @return
	 */
	private boolean checkError(Map<String, Object> errorMap, String key, String result) {
		if (StringUtils.isBlank(result)) {
			return true;
		}

		int code = 0;
		try {
			JSONObject json = JSONObject.parseObject(result);
			if (json.getInteger("errorCode") == null) {
				JSONObject status = (JSONObject) json.get("status");
				code = status.getInteger("code");
			} else {
				code = json.getInteger("errorCode");
			}
		} catch (JSONException ej) {
			log.error("", ej);
		}

		if (code != 0) {
			errorMap.put(key, code);
			return true;
		}
		return false;
	}

	/**
	 * Description:添加私有参数
	 * 
	 * @Version1.0 2014-8-21 下午06:36:35 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private void addPrivateParameters(Map<String, String> paramMap, JSONObject action) throws Exception {

		if (action == null) {
			return;
		}

		JSONObject params = action.getJSONObject("params");
		if (params == null || params.size() == 0) {
			return;
		}

		Set<Entry<String, Object>> paramSet = params.entrySet();
		for (Entry<String, Object> param : paramSet) {
			String key = param.getKey();
			String value = String.valueOf(param.getValue());
			paramMap.put(key, value);
		}
	}

	/**
	 * 将公共参数放入map中 Description:
	 * 
	 * @Version1.0 2014-11-13 下午06:13:48 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param request
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private String addPublicParametersV2(HttpServletRequest request, Map<String, String> paramMap) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String parameter = "returnType,deviceType,channelId,clientVersionNo,serverVersionNo,"
				+ "permanentId,deviceSerialNo,macAddr,resolution,clientOs,token,channelType,platformSource";
		String[] params = parameter.split(",");
		for (int i = 0; i < params.length; i++) {
			String value = request.getParameter(params[i]);
			if (StringUtils.isNotBlank(value)) {
				paramMap.put(params[i], value);
			}
		}
		// 增加ip参数
		String ip = IpUtils.getIpAddr(request);
		paramMap.put("ipParam", ip);
		return buffer.toString();
	}

	/**
	 * Description: 添加依赖参数
	 * 
	 * @Version1.0 2014-8-22 下午07:26:53 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param url
	 * @param dependParamValues
	 * @return
	 * @throws Exception
	 */
	private void addPrivateParameters(Map<String, String> paramMap, Map<String, Object> dependParamValues)
			throws Exception {

		if (dependParamValues == null || dependParamValues.size() == 0) {
			return;
		}

		// 添加依赖参数
		Set<String> keySet = dependParamValues.keySet();
		for (String key : keySet) {
			String value = String.valueOf(dependParamValues.get(key));
			paramMap.put(key, value);
		}
	}
}
