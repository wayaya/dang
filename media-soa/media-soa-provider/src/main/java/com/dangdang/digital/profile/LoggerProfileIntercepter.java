package com.dangdang.digital.profile;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;

/**
 * 
 * Description: 记录dubbo调用日志 All Rights Reserved.
 * 
 * @version 1.0 2015年1月8日 上午11:36:40 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Aspect
@Component
public class LoggerProfileIntercepter {

	private static final Logger logger = LoggerFactory.getLogger(LoggerProfileIntercepter.class);

	@Around("@annotation(com.dangdang.digital.profile.LoggerProfile)")
	public Object doLoggerProfiler(final ProceedingJoinPoint joinPoint) throws Throwable {
		if (logger.isInfoEnabled()) {
			Method method = this.getMethod(joinPoint);
			LoggerProfile loggerProfile = method.getAnnotation(LoggerProfile.class);
			Map<String, Object> logMap = new HashMap<String, Object>();

			if (loggerProfile.methodNote() != null && loggerProfile.methodNote().length() > 0) {
				logMap.put("note", loggerProfile.methodNote());
			}
			String methodName = joinPoint.getSignature().getName();
			String className = joinPoint.getTarget().getClass().getName();
			logMap.put("class", className);
			logMap.put("method", methodName);

			Object[] args = joinPoint.getArgs();
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					logMap.put("arg-" + i, args[i]);
				}
			}
			try {
				RpcContext rpcContext = RpcContext.getContext();
				if (rpcContext != null) {
					logMap.put("remoteHost", rpcContext.getRemoteHost());
					URL url = rpcContext.getUrl();
					if (url != null) {
						logMap.put("application", url.getParameter("application"));
					}
				}
			} catch (Exception e) {
				logger.error("RpcContext.getContext() Exception ", e);
			}
			// 是否记录用时
			if (loggerProfile.recordTime()) {
				StopWatch clock = new StopWatch("Profiling for '" + joinPoint.getSignature() + "'");
				try {
					clock.start(joinPoint.toShortString());
					return joinPoint.proceed();
				} finally {
					clock.stop();
					logMap.put("timeCost", clock.getLastTaskTimeMillis() + "ms");
					logger.info(JSON.toJSONString(logMap));
					if (clock.getLastTaskTimeMillis() > loggerProfile.allowMaxTime()) {
						logger.warn(loggerProfile.methodNote() + "接口调用超过设定时间,用时:" + clock.getLastTaskTimeMillis()
								+ "ms,设定默认时间：" + loggerProfile.allowMaxTime());
					}
				}
			} else {
				logger.info(JSON.toJSONString(logMap));
				return joinPoint.proceed();
			}
		} else {
			return joinPoint.proceed();
		}
	}

	/**
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected Method getMethod(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Signature sig = joinPoint.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new NoSuchMethodException("This annotation is only valid on a method.");
		}
		final MethodSignature msig = (MethodSignature) sig;
		final Object target = joinPoint.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}
}
