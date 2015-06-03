/**
 * Description: JdbcSqlServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:44:43  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.service.IJdbcSqlService;

/**
 * Description: 自定sql执行service实现类
 * All Rights Reserved.
 * @version 1.0  2015年2月3日 下午3:44:43  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class JdbcSqlServiceImpl implements IJdbcSqlService {

	@Resource(name = "jdbcQueryTemplate")
	JdbcTemplate jdbcQueryTemplate;

	@Override
	public List<Map<String, Object>> queryList(String sql) {
		List<Map<String, Object>> result = jdbcQueryTemplate.queryForList(sql);
		if(!CollectionUtils.isEmpty(result)){
			for(Map<String, Object> map : result){
				for(String key : map.keySet()){
					map.put(key, map.get(key) != null ? map.get(key).toString() : "null");
				}
			}
		}
		return result;
	}
	
	
}
