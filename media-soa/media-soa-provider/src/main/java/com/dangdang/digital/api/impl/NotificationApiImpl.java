package com.dangdang.digital.api.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.INotificationApi;
import com.dangdang.digital.model.Notification;
import com.dangdang.digital.service.INotificationService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

/**
 * Description: 获取用户推送信息实现类
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:48:37  by 于楠（yunan@dangdang.com）创建
 */
@Component("notificationApi")
public class NotificationApiImpl implements INotificationApi {
	@Resource
	INotificationService notificationService;

	@Override
	public Map<String, Object> pagedDataMap(Integer pageNo, Integer pageSize, Map<String, Object> queryMap) {
		//queryMap 应用appId 集合参数channelIds 集合参数deviceTypes 
		Query query = new Query();
		if(pageNo != null){
			query.setPage(pageNo);
		}
		query.setPageSize(pageSize);
		PageFinder<Notification> result = notificationService.getPageFinderObjsByMutliChannel(queryMap, query);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("NotificationList", result.getData());
		resultMap.put("RowCount", result.getRowCount());
		resultMap.put("PageCount", result.getPageCount());
		resultMap.put("CurrentNumber", result.getCurrentNumber());
		return resultMap;
	}

}
