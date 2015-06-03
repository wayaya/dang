package com.dangdang.digital.controlller;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.utils.AppUtil;

public class BaseController{

	protected void toJson(Object json) throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		if(json instanceof String || json instanceof StringBuffer || json instanceof StringBuilder){
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write(JSON.toJSONString(json));
		}
		response.getWriter().flush();
	}

	protected HttpServletRequest getRequest() {
		return AppUtil.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return AppUtil.getResponse();
	}

	protected HttpSession getSession() {
		return AppUtil.getSession();
	}
	
	/**
	 * 等价于request.setAttribute(key, value).
	 */
	protected void put(final String key, final Object value) {
		getRequest().setAttribute(key, value);
	}

	/**
	 * 
	 * Description: 将一个 JavaBean 对象转化为一个  Map
	 * @Version1.0 2014年11月15日 下午5:18:22 by 张宪斌（zhangxianbin@dangdang.com）创建
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> convertBeanToMap(Object bean){ 
		Map<String, Object> returnMap = new HashMap<String, Object>(); 
		if(bean == null){
			return returnMap;
		}
		if(bean instanceof Map){
			return (Map<String, Object>)bean;
		}
        Class type = bean.getClass(); 
        try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); 
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
			for (int i = 0; i< propertyDescriptors.length; i++) { 
			    PropertyDescriptor descriptor = propertyDescriptors[i]; 
			    String propertyName = descriptor.getName(); 
			    if (!propertyName.equals("class")) { 
			        Method readMethod = descriptor.getReadMethod(); 
			        Object result = readMethod.invoke(bean, new Object[0]); 
			        if (result != null) { 
			        	if(result instanceof String && StringUtils.isEmpty(result.toString())){
			        		continue;
			        	}
			            returnMap.put(propertyName, result); 
			        }
			    } 
			}
		} catch (Exception e) {
			throw new RuntimeException("将 JavaBean 转化为 Map失败！");
		}
        return returnMap; 
    } 

}
