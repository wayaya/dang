package com.dangdang.digital.controlller;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.processor.ApiProcessor;
import com.dangdang.digital.processor.UnknownApiProcessor;
import com.dangdang.digital.system.SpringContextHolder;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.JsonResultSender2;
import com.dangdang.digital.utils.ResultSender;

/**
 * 
 * @author wang.zw
 *
 */
@Controller
@RequestMapping("media")
public class HApiController {
	// 服务端版本号，判断是否允许访问api.do
	private final String headerErrorAction = ConfigPropertieUtils.getString("server.response.header.error"); // 需要在header中增加错误码接口
	// 长请求时间对应的action
	private final String longRequestTimeActions = ConfigPropertieUtils.getString("server.long.request.time.actions",
			"getBuyBookList,multiAction,multiActionV2");
	private static final Logger logger = LoggerFactory.getLogger(HApiController.class);

	@Autowired
	private UnknownApiProcessor unknownApiProcessor;

	@Autowired
	protected HttpServletRequest request;

	@RequestMapping("api")
	public void api(String action, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Date date = new Date();
		int responseState = 200;
		String gizp = get("gzip");
		ResultSender sender;
		if ("yes".equals(gizp)) {
			sender = JsonResultSender2.getGzipInstance();
		} else {
			sender = JsonResultSender2.getInstance();
		}
		// DDClick click = getDDClickDate(action);
		try {
			ApiProcessor processor;
			try {
				processor = (ApiProcessor) SpringContextHolder.getBean("hapi" + action + "processor");
			} catch (NoSuchBeanDefinitionException e) {
				processor = null;
			}
			if (processor == null) {
				processor = unknownApiProcessor;
			}
			processor.handle(request, response, sender, action);

		} catch (RuntimeException ex) {
			logger.error("移动接口报错runtime：", ex);
			ApiException ae = MobileApiException.otherError();
			if (ex.getCause() != null) {
				if (ex.getCause() instanceof UnknownHostException || ex.getCause() instanceof SocketTimeoutException
						|| ex.getCause() instanceof ConnectException) {
					ae = MobileApiException.networkError();
				} else {
					ae = MobileApiException.netWorkDataError();
				}
			}
			if (checkHeaderError(action)) {
				sender.errorResponseHeader(ae.getCode(), ae.getMessage(), response);
			}
			sender.fail(ae.getCode(), ae.getMessage(), response);
			responseState = 500;
		} catch (Exception e) {
			logger.error("移动接口报错：", e);
			ApiException ae = MobileApiException.otherError();
			if (e instanceof ApiException) {
				ae = (ApiException) e;
			}
			if (checkHeaderError(action)) {
				sender.errorResponseHeader(ae.getCode(), ae.getMessage(), response);
			}
			sender.fail(ae.getCode(), ae.getMessage(), response);
			responseState = 500;
		}
		sender.addAppHead(response);
		// saveDDClickData(click, sender);
		logRequestUrl(action, responseState, date, request);

	}
	
	/**
	 * Description: 翻篇儿项目请求
	 * @Version1.0 2015年3月2日 下午5:43:26 by 代鹏（daipeng@dangdang.com）创建
	 * @param action
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("api2")
	public void api2(String action, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Date date = new Date();
		int responseState = 200;
		String gizp = get("gzip");
		ResultSender sender;
		if ("yes".equals(gizp)) {
			sender = JsonResultSender2.getGzipInstance();
		} else {
			sender = JsonResultSender2.getInstance();
		}
		// DDClick click = getDDClickDate(action);
		try {
			ApiProcessor processor;
			try {
				processor = (ApiProcessor) SpringContextHolder.getBean("hapi" + action + "processor");
			} catch (NoSuchBeanDefinitionException e) {
				processor = null;
			}
			if (processor == null) {
				processor = unknownApiProcessor;
			}
			processor.handle(request, response, sender, action);

		} catch (RuntimeException ex) {
			logger.error("移动接口报错runtime：", ex);
			ApiException ae = MobileApiException.otherError();
			if (ex.getCause() != null) {
				if (ex.getCause() instanceof UnknownHostException || ex.getCause() instanceof SocketTimeoutException
						|| ex.getCause() instanceof ConnectException) {
					ae = MobileApiException.networkError();
				} else {
					ae = MobileApiException.netWorkDataError();
				}
			}
			if (checkHeaderError(action)) {
				sender.errorResponseHeader(ae.getCode(), ae.getMessage(), response);
			}
			sender.fail(ae.getCode(), ae.getMessage(), response);
			responseState = 500;
		} catch (Exception e) {
			logger.error("移动接口报错：", e);
			ApiException ae = MobileApiException.otherError();
			if (e instanceof ApiException) {
				ae = (ApiException) e;
			}
			if (checkHeaderError(action)) {
				sender.errorResponseHeader(ae.getCode(), ae.getMessage(), response);
			}
			sender.fail(ae.getCode(), ae.getMessage(), response);
			responseState = 500;
		}
		sender.addAppHead(response);
		// saveDDClickData(click, sender);
		logRequestUrl(action, responseState, date, request);
	}

	@SuppressWarnings("unchecked")
	private void logRequestUrl(String action, int responseState, Date startDate, HttpServletRequest request)
			throws Exception {
		StringBuffer queryString = new StringBuffer();
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			String value = params.get(key)[0];
			if (StringUtils.length(key) < 200 && StringUtils.length(value) < 200
					&& !"password".equals(key.trim().toLowerCase())) { // 忽略长度大于200的参数
				if (value.trim().indexOf(" ") > 0)
					value = URLEncoder.encode(value, "UTF-8");
				queryString.append(key).append("=").append(value).append("&");
			}
		}
		logger.info("移动接口访问：" + request.getRequestURI() + "?" + queryString.toString());

		String requestParams = request.getRequestURI() + "?" + queryString.toString();
		String actionType = "short";
		if (longRequestTimeActions.indexOf(action) >= 0) {
			actionType = "long";
		}
		queryString = new StringBuffer();
		queryString.append(actionType);
		queryString.append(" ").append(requestParams);
		Date endDate = new Date();
		double resTime = endDate.getTime() - startDate.getTime();
		queryString.append(" ").append(resTime / 1000);
		queryString.append(" ").append(responseState);
		logger.info("移动接口访问：" + queryString.toString());
	}

	/**
	 * 验证是否是跟文件相关的接口.
	 */
	private boolean checkHeaderError(String action) {
		if (StringUtils.isNotEmpty(headerErrorAction)) {
			if (headerErrorAction.indexOf("/" + action + "/") >= 0) {
				return true;
			}
		}
		return false;
	}

	protected String get(final String name) {
		return get(name, null);
	}

	protected String get(String name, String defaultValue) {
		String value = request.getParameter(name);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
}
