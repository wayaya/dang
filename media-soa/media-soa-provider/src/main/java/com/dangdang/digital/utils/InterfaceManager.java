package com.dangdang.digital.utils;

import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;


/**
 * 为接口调用提供合适方法.
 *
 */
public final class InterfaceManager {

	private static Logger logger = LoggerFactory.getLogger(InterfaceManager.class);
	
	private static Map<String, String> paramNameStringMap = CollectionUtils.buildMap(
			"abstract", "编辑推荐",
			"author_intro", "作者介绍",
			"author_name", "作者",
			"barcodes", "ISBN",
			"book_id", "试读ID",
			"catalog", "目录",
			"clientIP", "客户端IP",
			"commfields", "评论",
			"content", "摘要/评论正文",
			"cust_id", "用户ID",
			"customer_id", "用户ID",
			"device_type", "设备类型",
			"device_id", "设备ID", 
			"display_status", "显示状态",
			"email", "邮箱地址",
			"entries", "API使用者部门",
			"extract", "在线试读章节",
			"fields", "返回字段",
			"filt_type", "过滤",
			"key", "查询关键字",
			"length", "每页取回的记录数",
			"list", "分类路径",
			"media_feeback", "媒体推荐",
			"memo", "备注说明",
			"mobile_phone", "手机号",
			"mobilephone", "用户手机号码",
			"new_password", "新密码",
			"nickname", "用户昵称",
			"num_images", "图片数量",
			"number_of_pages", "页数",
			"number_of_words", "字数",
			"offset", "记录偏移量",
			"old_password", "旧密码",
			"op_source_id", "数据来源编号",
			"original_price", "原价",
			"page", "页码",
			"pagesize", "每页中查询结果数量",
			"paper_book_product_id", "纸书单品ID",
			"password", "用户密码",
			"permanent_id", "确定用户设备唯一ID",
			"platform", "平台",
			"print_copy", "印次",
			"product_id", "单品ID",
			"product_ids", "多本纸书单品ID",
			"product_name", "书名",
			"publish_date", "出版日期",
			"publisher", "出版社",
			"registerfrom", "注册来源",
			"registerfromtype", "用户注册来源类型",
			"registertype", "用户主动/被动",
			"result_format", "返回样式",
			"return_type", "期望返回的格式类型",
			"sale_price", "售价",
			"score", "分值",
			"series_name", "从套书名",
			"sign", "签名",
			"sort_type", "排序类型",
			"source_type", "来源类型",
			"source_url", "来源URL地址",
			"standard_id", "ISBN",
			"startIndex", "下标起点",
			"subject_id", "单品ID",
			"title", "评论标题",
			"trace_id", "用户本次访问唯一ID",
			"ts", "时间戳",
			"usercookie", "用户Cookie",
			"username", "邮箱或昵称");
	
	private InterfaceManager() {
	}
	
	/**
	 * 判断调用请求结果是否与预期值匹配（用来判断操作成功或失败）.
	 * @param map
	 * @param action
	 * @return
	 */
	public static boolean getBooleanResultOfRequest(Map<String, Object> map, 
			String action, String xpath, String expectedValue) {
		try {
			boolean result = false;
			Map<String, String> paramMap = getParamCapsuleOfHttpUrl(
					action, map);
			
			String resultStr = getUrlInterfaceResult(paramMap);
			
			try {
				Document doc = convertStringToDoc(resultStr);
				String getValue = getNodeTextOfRequestResult(doc, xpath);
				
				if (StringUtils.isNotBlank(getValue) && getValue.equals(expectedValue)) {
					result = true;
				}
			} catch (Exception e) {
				logger.error("发生错误: ", e);
			}
			
			if (result) {
				logRequestProcedure(action, paramMap, resultStr, "info");
			} else {
				logRequestProcedure(action, paramMap, resultStr, "error");
			}
			
			return result;
		} catch (Exception e) {
			logger.error("发生错误: ", e);
		}
		
		return false;
	}
	
	/**
	 * 包含url等参数的map.
	 * @param map
	 * @return
	 */
	public static String getUrlInterfaceResult(Map<String, String> map) {
		String interfaceUrl = map.remove("url");
		String methodStr = map.remove("method");
		if (StringUtils.isBlank(methodStr)) {
			methodStr = "get";
		}
		String encode = map.remove("code");
		if (StringUtils.isBlank(encode)) {
			encode = "UTF-8";
		}
		boolean useProxy = false;
		String useProxyStr = map.remove("useproxy");
		if (StringUtils.isNotBlank(useProxyStr)) {
			useProxy = Boolean.valueOf(useProxyStr);
		}
		return HttpUtils.getContent(interfaceUrl, methodStr, map, encode, useProxy);
	}
	
	/**
     * 包含url等参数的map,且参数中含有method.
     * @param map
     * @return
     */
    public static String getUrlInterfaceResultWithMethod(Map<String, String> map) {
        String interfaceUrl = map.remove("url");
        String methodStr = map.remove("reqMethod");
        if (StringUtils.isBlank(methodStr)) {
            methodStr = "get";
        }
        String encode = map.remove("code");
        if (StringUtils.isBlank(encode)) {
            encode = "UTF-8";
        }
        boolean useProxy = false;
        String useProxyStr = map.remove("useproxy");
        if (StringUtils.isNotBlank(useProxyStr)) {
            useProxy = Boolean.valueOf(useProxyStr);
        }
        return HttpUtils.getContentWithOutFormEncode(interfaceUrl, methodStr, map, encode, useProxy);
    }
	
	/**
     * 对httpUrl方式的接口，封装参数.
	 * @param interfaceAction
	 * @param map
	 * @return
	 */
	public static Map<String, String> getParamCapsuleOfHttpUrl(String interfaceAction, 
			Map<String, Object> map) throws Exception {
		String action = ConfigPropertieUtils.getString(interfaceAction);
		
		if (StringUtils.isBlank(action)) {
			throw new Exception("配置文件中没有定义该接口: " + interfaceAction);
		}
		
		Map<String, String> returnMap = new HashMap<String, String>();
		
		String[] paramDefineArr = action.split("\\$");
		
		for (String paramDefine : paramDefineArr) {
			String[] arr = paramDefine.split("~");
			
			String paramName = arr[0];
			String paramValue = arr[1].substring(1, arr[1].length() - 1);
			
			if (paramValue.indexOf("#") >= 0) {
				String[] tmpArr = paramValue.split("#");
				
				if (tmpArr[0].length() == 0) {
					String strTmp = tmpArr[1];
					int pos = strTmp.indexOf("?");
					
					if (pos > 0) {
						String[] defaultArr = tmpArr[1].split("\\?");
						paramValue = (String) map.get(defaultArr[0]);
						if (StringUtils.isBlank(paramValue)) {
							paramValue = defaultArr[1];
						}
					} else {
						paramValue = map.get(strTmp).toString();
					}
					
				} else {
					Object obj = map.get(tmpArr[0]);
					String strTmp = tmpArr[1];
					int pos = strTmp.indexOf("?");
					
					if (pos > 0) {
						String[] defaultArr = tmpArr[1].split("\\?");
						paramValue = convertObjectToString(getPropertyOfObj(obj, defaultArr[0]));
						
						if (StringUtils.isBlank(paramValue)) {
							paramValue = defaultArr[1];
						}
					} else {
						paramValue = convertObjectToString(getPropertyOfObj(obj, tmpArr[1]));
					}
				}
			}
			
			if (paramValue == null) {
				paramValue = "";
			}
			
			returnMap.put(paramName, paramValue);
		}
		
		return returnMap;
	}
	
	/**
	 * 获取xpath节点内容.
	 * @param doc
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static String getNodeTextOfRequestResult(Document doc, String xpath) throws Exception {
		Element root = doc.getRootElement();
		Element ele = (Element) XPath.selectSingleNode(root, xpath);
		
		if (ele != null) {
			return ele.getText();
		} else {
			return "";
		}
	}
	
	/**
	 * 判断节点是否存在.
	 */
	public static boolean judgeNodeExist(Document doc, String xpath) throws Exception {
		try {
			Element root = doc.getRootElement();
			Element ele = (Element) XPath.selectSingleNode(root, xpath);
			
			if (ele != null) {
				return true;
			}
		} catch (Exception e) {
			logger.error("somthing wrong:", e);
		}
		
		return false;
	}
	
	/**
	 * 把string转成Document.
	 * @param content
	 * @return
	 */
	public static Document convertStringToDoc(String content) throws Exception {
		Document doc = null;
		StringReader xmlReader = null;
		try {
			SAXBuilder builder = new SAXBuilder(false);
			//屏蔽dtd验证
			builder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) {
					return new InputSource(new StringBufferInputStream(""));
				}
			});
			//屏蔽非法字符
			if (StringUtils.isNotBlank(content)) {
				int index = content.indexOf("<");
				if (index > 0) {
					content = content.substring(index);
				}
			}
			xmlReader = new StringReader(content);
			doc = builder.build(xmlReader);
		} catch (Exception e) {
			logger.error("创建文档失败。 ", e);
			doc = null;
		} finally {
			IOUtils.closeQuietly(xmlReader);
		}
		
		if (doc == null) {
			throw new Exception("创建文档失败: " + content);
		}
		
		return doc;
	}
	
	/**
	 * 构建请求内容.
	 * @param action
	 * @param map
	 * @param result
	 * @return
	 */
	public static String getContentOfRequest(String action, Map<String, String> map, String result) {
		StringBuilder sbuf = new StringBuilder();
		sbuf.append("请求参数为 :\r\n");
		
		if (map != null && map.size() > 0) {
			Iterator<String> iter = map.keySet().iterator();
			
			
			sbuf.append(" [").append("\r\n");
			
			while (iter.hasNext()) {
				String paramName = iter.next();
				String paramValue = map.get(paramName);
				
				sbuf.append(StringUtils.defaultString(paramNameStringMap.get(paramName), paramName) 
						+ "（" + paramName + "）：" + (paramValue != null ? paramValue : "")).append("\r\n");
			}
			sbuf.append("]\r\n");
		}
		
		sbuf.append("请求结果: \r\n").append(StringUtils.defaultString(result, "空"));
		
		return sbuf.toString();
	}
	
	/**
	 * 根据请求参数、返回结果、日志级别来记录日志.
	 * @param map
	 * @param result
	 * @param logLevel
	 */
	public static void logRequestProcedure(String action, Map<String, String> map, String result, String logLevel) {
		if (logLevel.toLowerCase().equals("info")) {
			logger.info(getContentOfRequest(action, map, result));
		} else {
			logger.error(getContentOfRequest(action, map, result));
		}
	}
	
	private static String convertObjectToString(Object obj) {
		if (obj == null) {
			return null;
		}
		
		if (obj.getClass().getName().equals("java.util.Date")) {
			DateConvert converter = new DateConvert();
			return (String) converter.convert(String.class, obj);
		} else {
			return obj.toString();
		}
	}
	
	private static Object getPropertyOfObj(Object obj, String propertyName) throws Exception {
		Method[] methods = obj.getClass().getMethods();
			
		for (Method method : methods) {
			if (method.getName().equals("get" + propertyName.substring(0, 1)
				.toUpperCase() + propertyName.substring(1))) {
				return method.invoke(obj);
			}
		}
		
		throw new Exception("该属性没有getter：" + propertyName);
	}
}
