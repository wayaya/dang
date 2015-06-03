package com.dangdang.digital.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http相关工具方法.
 * 
 * @author dangdang
 */
public abstract class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/** 代理名称. */
	private static final String PROXY_NAME = ConfigPropertieUtils.getString("proxy.name");

	/** 代理端口. */
	private static final String PROXY_PORT = ConfigPropertieUtils.getString("proxy.port");

	/** 代理用户 */
	private static final String PROXY_USERNAME = ConfigPropertieUtils.getString("proxy.userName");

	/** 代理端口. */
	private static final String PROXY_PASSWORD = ConfigPropertieUtils.getString("proxy.password");

	/** get请求. **/
	private static final String GET_METHOD = "get";

	/** post请求. **/
	private static final String POST_METHOD = "post";

	/** 是否使用代理. */
	private static final boolean IS_WITH_PROXY = isWithProxy();
	
	public static final String UTF8 = "UTF-8";
	
	public static final String GBK = "GBK";

	/** httpclient对应CONNECTION_TIMEOUT对应的值 */
	private static final Integer CONNECTION_TIMEOUT = ConfigPropertieUtils.getInteger(
			"httpclient.connection.timeout", 1000);

	/** httpclient对应SO_TIMEOUT对应的值 */
	private static final Integer SO_TIMEOUT = ConfigPropertieUtils.getInteger(
			"httpclient.so.timeout", 10000);

	private HttpUtils() {
	}

	private static void setProxy(DefaultHttpClient httpclient) {
		httpclient.getCredentialsProvider().setCredentials(
				AuthScope.ANY,
				new NTCredentials(PROXY_USERNAME, PROXY_PASSWORD, PROXY_NAME,
						"dangdang.com"));

		List<String> authpref = new ArrayList<String>();
		authpref.add(AuthPolicy.NTLM);
		httpclient.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF,
				authpref);
		HttpHost proxy = new HttpHost(PROXY_NAME, 8080);

		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}

	private static void setProxy(HttpClient httpClient) {
		httpClient.getHostConfiguration().setProxy(PROXY_NAME,
				Integer.valueOf(PROXY_PORT));
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setProxyCredentials(
				org.apache.commons.httpclient.auth.AuthScope.ANY,
				new org.apache.commons.httpclient.NTCredentials(PROXY_USERNAME,
						PROXY_PASSWORD, PROXY_NAME, "dangdang.com"));
	}

	public static byte[] getBytes(final String urlStr) {

		return getBytes(urlStr, IS_WITH_PROXY);
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port,
					url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy,
			boolean simulateBrowser) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port,
					url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			if (simulateBrowser) {
				httpget.setHeader("Accept",
						"Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Accept-Charset",
						"GB2312,utf-8;q=0.7,*;q=0.7");
				httpget.setHeader("Accept-Encoding", "gzip, deflate");
				httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
				httpget.setHeader("Connection", "keep-alive");
				// httpget.setHeader("Cookie", cookie);
				String host = "";
				if (urlStr.toLowerCase().startsWith("http://")) {
					host = urlStr.substring(7);
				} else {
					host = urlStr;
				}
				int index = host.indexOf("/");
				if (index > 0) {
					host = host.substring(0, index);
				}
				httpget.setHeader("Host", host);
				// httpget.setHeader("refer",
				// "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");
				httpget.setHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			}

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			return new String(bytes);
		}
		return "";
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url, String charset) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	/**
	 * 获取url内容.
	 * 
	 * @param url
	 * @param charset
	 * @param simulateBrowser
	 *            是否设置相关参数模拟浏览器.
	 * @return
	 */
	public static String getContent(final String url, String charset,
			boolean simulateBrowser) {
		byte[] bytes = getBytes(url, IS_WITH_PROXY, simulateBrowser);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	public static String getContentByPost(final String url, byte[] data) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(
				new ByteArrayInputStream(data));
		method.setRequestEntity(requestEntity);
		// 设置连接一个url的连接等待超时时间
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(CONNECTION_TIMEOUT);
		// 设置读取数据的超时时间
		client.getHttpConnectionManager().getParams()
				.setSoTimeout(SO_TIMEOUT);
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getContent(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy,
			int connectionTimeout, int soTimeout) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else {
			method = new PostMethod(url);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
		}

		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}
	/**
	 * 通过http方式访问url调用接口.
	 * 
	 * @param url
	 *            接口地址
	 * @param methodStr
	 *            get或post方式
	 * @param paramsMap
	 *            参数列表
	 * @param encode
	 *            参数编码
	 * @param useProxy
	 *            是否使用代理
	 * @return
	 */
	public static String getContent(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy) {
		return getContent(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public static String getContentWithOutFormEncode(final String url,
			String methodStr, Map<String, String> paramsMap, String encode,
			boolean useProxy, int connectionTimeout, int soTimeout) {
		
		final HttpClient httpClient = new HttpClient();

		// 设置连接一个url的连接等待超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(connectionTimeout);
		// 设置读取数据的超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(soTimeout);

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		method = new GetMethod(url);

		StringBuffer queryString = new StringBuffer();
		Iterator it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			queryString.append(m.getKey()).append("=").append(m.getValue())
					.append("&");
		}

		method.setQueryString(queryString.toString().substring(0,
				queryString.length() - 1));

		try {
			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * Form- without urlencoding.
	 * 
	 * @param url
	 * @param methodStr
	 * @param paramsMap
	 * @param encode
	 * @param useProxy
	 * @return
	 */
	public static String getContentWithOutFormEncode(final String url,
                                                     String methodStr,
                                                     Map<String, String> paramsMap,
                                                     String encode,
                                                     boolean useProxy) {
       return getContentWithOutFormEncode(url, methodStr, paramsMap, encode,
    		   useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

	/**
	 * 从参数map中构建NameValuePair数组.
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static NameValuePair[] getParamsFromMap(
			Map<String, String> paramsMap) {
		NameValuePair[] params = new NameValuePair[paramsMap.size()];
		int pos = 0;
		Iterator<String> iter = paramsMap.keySet().iterator();
		while (iter.hasNext()) {
			String paramName = iter.next();
			String paramValue = paramsMap.get(paramName);

			params[pos++] = new NameValuePair(paramName, paramValue);
		}

		return params;
	}

	/**
	 * 对Url发起请求.
	 * 
	 * @param url
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void requestUrl(final String url) throws IOException {
		final HttpClient httpClient = new HttpClient();
		final GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(getMethod);
		} finally {
			getMethod.releaseConnection();
		}
	}

	/**
	 * 是否使用代理.
	 */
	private static boolean isWithProxy() {
		return StringUtils.isNotBlank(PROXY_NAME)
				&& StringUtils.isNotBlank(PROXY_PORT);
	}

	public static void main(final String[] args) throws Exception {}
}
