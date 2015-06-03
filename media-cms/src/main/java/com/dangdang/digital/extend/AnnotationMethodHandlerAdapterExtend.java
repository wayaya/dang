package com.dangdang.digital.extend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.dangdang.digital.utils.AppUtil;

@SuppressWarnings("deprecation")
public class AnnotationMethodHandlerAdapterExtend extends
		AnnotationMethodHandlerAdapter {
	@Override
	protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AppUtil.setHttp(request, response);
		ModelAndView mav = super.invokeHandlerMethod(request, response, handler);
		AppUtil.removeAll();
		return mav;
	}
}
