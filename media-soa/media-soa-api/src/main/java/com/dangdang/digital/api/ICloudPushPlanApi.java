package com.dangdang.digital.api;

import com.dangdang.digital.vo.CloudPushPlanVo;

/**
 * Description: 查询推送计划表的数据接口
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:45:46  by 于楠（yunan@dangdang.com）创建
 */
public interface ICloudPushPlanApi {
	
	/**
	 * Description: 查询media_cloud_push_plan表的数据  
	 * @Version1.0 2015年2月2日 下午2:46:25 by 于楠（yunan@dangdang.com）创建
	 * @param planId 参数planid
	 * @return
	 */
	public CloudPushPlanVo getCloudPushPlan(Long planId);

}
