package com.dangdang.digital.api;

/**
 * Description: 更新推送计划状态表状态接口
 * All Rights Reserved.
 * @version 1.0  2015年2月2日 下午2:55:30  by 于楠（yunan@dangdang.com）创建
 */
public interface ICloudPushPlanStatusApi {
	
	/**
	 * Description: 更新推送计划状态表状态 	更新planid且日期是今天的推送计划状态记录状态
	 * @Version1.0 2015年2月2日 下午2:56:00 by 于楠（yunan@dangdang.com）创建
	 * @param planId 推送计划编号
	 * @param jobStatus 推送状态
	 */
	public void updateCloudPushPlanStatus(Long planId, Integer jobStatus);

}
