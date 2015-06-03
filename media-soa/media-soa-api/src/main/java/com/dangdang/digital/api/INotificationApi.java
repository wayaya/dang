package com.dangdang.digital.api;

import java.util.Map;

/**
 * Description: 获取用户推送信息接口
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:48:37  by 于楠（yunan@dangdang.com）创建
 */
public interface INotificationApi {
	
	/**
	 * Description: 查询media_notification表的count数量，需要输入条件
	 * 				分页查询media_notification表的数据 
	 * @Version1.0 2015年2月2日 下午2:48:48 by 于楠（yunan@dangdang.com）创建
	 * @param pageNo 第几页
	 * @param pageSize 每页记录数
	 * @param queryMap 比如device_type,app_id,channel_id等
	 * @return
	 */
	public Map<String, Object> pagedDataMap(Integer pageNo, Integer pageSize, Map<String ,Object> queryMap);
}
