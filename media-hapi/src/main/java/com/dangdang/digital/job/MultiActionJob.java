package com.dangdang.digital.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.intecepter.MultiActionRequestWrapper;
import com.dangdang.digital.processor.ApiProcessor;
import com.dangdang.digital.processor.UnknownApiProcessor;
import com.dangdang.digital.system.SpringContextHolder;
import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * Description: 复合接口job All Rights Reserved.
 * 
 * @version 1.0 2015年1月10日 上午10:04:21 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class MultiActionJob implements Callable<MultiActionJobResult> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private JSONObject noDependAction;
	private HttpServletRequest request;
	private ResultSender tmpSender;
	private UnknownApiProcessor unknownApiProcessor;
	private Map<String, String> pubParamMap;

	public MultiActionJob(JSONObject noDependAction, HttpServletRequest request, ResultSender tmpSender,
			UnknownApiProcessor unknownApiProcessor, Map<String, String> pubParamMap) {
		this.noDependAction = noDependAction;
		this.request = request;
		this.tmpSender = tmpSender;
		this.unknownApiProcessor = unknownApiProcessor;
		this.pubParamMap = pubParamMap;
	}

	@Override
	public MultiActionJobResult call() throws Exception {
		String actionName = noDependAction.getString("action");
		String alias = noDependAction.getString("alias");
		String returnKey = alias == null ? actionName : alias;
		HashMap<String, String> paramMap = new HashMap<String, String>();
		// 公共参数
		paramMap.putAll(pubParamMap);
		// 私有参数
		addPrivateParameters(paramMap, noDependAction);
		// 包装request,重写request.getParameter方法
		MultiActionRequestWrapper tmpRequest = new MultiActionRequestWrapper(request, paramMap);

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
		MultiActionJobResult result = new MultiActionJobResult();
		result.setTmpSender(tmpSender);
		result.setReturnKey(returnKey);
		return result;
	}

	public ResultSender getTmpSender() {
		return tmpSender;
	}

	public void setTmpSender(ResultSender tmpSender) {
		this.tmpSender = tmpSender;
	}

	public UnknownApiProcessor getUnknownApiProcessor() {
		return unknownApiProcessor;
	}

	public void setUnknownApiProcessor(UnknownApiProcessor unknownApiProcessor) {
		this.unknownApiProcessor = unknownApiProcessor;
	}

	public JSONObject getNoDependAction() {
		return noDependAction;
	}

	public void setNoDependAction(JSONObject noDependAction) {
		this.noDependAction = noDependAction;
	}

	public Map<String, String> getPubParamMap() {
		return pubParamMap;
	}

	public void setPubParamMap(Map<String, String> pubParamMap) {
		this.pubParamMap = pubParamMap;
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
}
