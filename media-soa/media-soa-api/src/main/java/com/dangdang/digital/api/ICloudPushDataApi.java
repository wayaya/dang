package com.dangdang.digital.api;

import java.util.List;
import java.util.Map;

/**
 * Description: 批量插入推送内容数据表接口
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:52:18  by 于楠（yunan@dangdang.com）创建
 */
public interface ICloudPushDataApi {

	/**
	 * Description: 批量插入推送内容数据表接口
	 * @Version1.0 2015年2月2日 下午2:53:03 by 于楠（yunan@dangdang.com）创建
	 * @param dataList //Map参数messageTitle messageDescription cloudPushPlanId extUserId extChannelId appId custId deviceNo
	 */
	public void saveCloudPushData(List<Map<String, Object>> dataList);
	
	/**
	 * Description: 此方法适用于仅需要custId的自动推送，后台会根据planId获取推送文案，再根据custId对应的设备详情去准备好推送数据
	 * @Version1.0 2015年5月4日 下午12:20:05 by 魏嵩（weisong@dangdang.com）创建
	 * @param planId 自动推送的planId
	 * @param custIdList custId的List
	 */
	public void saveCloudPushData(Long planId, List<Long> custIdList);
}
