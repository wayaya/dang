package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.vo.DSDepositPayInfoVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午2:16:40  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public interface IActivityInfoService extends IBaseService<ActivityInfo, Integer>{
	
	/**添加分类树节点
	 * @param ActivityInfoQueryVo
	 * @return
	 */
	public void insert(ActivityInfo activityInfo);
	
	/**
	 * 删除树节点
	 * @param ActivityInfoQueryVo
	 * @return
	 */
	public void delete(Integer id);
	
	/**
	 * 
	 * Description: 获取福袋道具列表
	 * @Version1.0 2014年12月17日 下午4:08:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @return
	 */
	public List<ActivityInfo> getBuyLuckyBag();
	
	/**
	 * 
	 * Description: 获取充值方式选择列表
	 * @Version1.0 2014年12月24日 上午11:14:04 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param activityTypeId
	 * @return
	 */
	public List<ActivityInfo> getConsumerDepositPayMent(Integer activityTypeId,String fromPaltform);
	
	/**
	 * 
	 * Description: 通过虚拟商品id查询充值活动信息
	 * @Version1.0 2014年12月24日 下午5:10:47 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param productId
	 * @return
	 */
	public ActivityInfo getConsumerDepositInfo(String productId,Integer activityTypeId,String fromPaltform);
	
	/**
	 * Description: 通过code获取单个活动信息
	 * @Version1.0 2015年1月7日 下午8:26:21 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param activityTypeCode
	 * @return
	 */
	ActivityInfo getActivityInfoByCode(String activityTypeCode);
	
	/**
	 * 
	 * Description: 获取充值选择列表
	 * @Version1.0 2014年12月24日 上午11:14:04 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param fromPaltform
	 * @return
	 */
	public List<DSDepositPayInfoVo> getDSConsumerDepositPayInfo(String fromPaltform);
}