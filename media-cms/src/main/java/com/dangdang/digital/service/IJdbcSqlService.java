/**
 * Description: IJdbcSqlService.java
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:43:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:43:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IJdbcSqlService {

	/**
	 * 
	 * Description: 自定义sql查询
	 * @Version1.0 2015年2月3日 下午3:51:15 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryList(String sql);
}
