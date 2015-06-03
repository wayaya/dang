package com.dangdang.digital.profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Description: 记录调用日志 All Rights Reserved.
 * 
 * @version 1.0 2015年1月8日 上午11:36:57 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoggerProfile {
	
	/**
	 * 
	 * Description:方法注释
	 * 
	 * @Version1.0 2015年1月8日 上午11:45:12 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @return
	 */
	public String methodNote();

	/**
	 * 
	 * Description: 是否记录运行时间
	 * 
	 * @Version1.0 2015年1月8日 上午11:56:03 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @return
	 */
	public boolean recordTime() default false;
	
	/**
	 * 
	 * Description: 最大允许时间，超过时间打印错误日志
	 * @Version1.0 2015年2月2日 下午1:49:45 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @return
	 */
	public long allowMaxTime() default 1000;
	

}
