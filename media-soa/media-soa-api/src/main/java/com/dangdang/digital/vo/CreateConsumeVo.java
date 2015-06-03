/**
 * Description: CreateConsumeVo.java
 * All Rights Reserved.
 * @version 1.0  2014年12月1日 上午11:40:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.model.UserMonthly;

/**
 * Description: 创建消费记录vo
 * All Rights Reserved.
 * @version 1.0  2014年12月1日 上午11:40:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class CreateConsumeVo implements Serializable {

	/**
	   * @Fields serialVersionUID : TODO 2014年12月1日 上午11:40:56  by 张宪斌（zhangxianbin@dangdang.com）创建
	  */
	
	private static final long serialVersionUID = 4595358358332895657L;
	/**
	 * 用户custId
	 */
    private Long custId;	
    /**
     * 消费类型
     */
    private Short consumeType;
    /**
     * <购买道具id，购买道具数量>
     */
    private Map<Integer,Integer> propMap = new HashMap<Integer,Integer>();
    /**
     * 购买包月id
     */
    private Integer monthlyId;
    /**
     * 购买福袋id
     */
    private Integer luckyBagId;
    /**
     * 打赏电子书id
     */
    private Long mediaId;
    /**
     * 打赏金额
     */
    private Integer rewardsMoney;
	/**
	 * 用户消费记录
	 */
	private ConsumerConsume consumerConsume = null;
	/**
	 * 参加活动id
	 */
	private List<Integer> activityIds = new ArrayList<Integer>(0);
	/**
	 * 创建订单错误信息
	 */
	private String errorMsg = null;
	/**
	 * 创建订单错误编码
	 */
	private String errorCode = null;
	/**
	 * 异常回滚点
	 */
	private Integer rollbackPoint = 0;
	/**
	 * 用户购买包月信息
	 */
	private UserMonthly userMonthly;
    /**
     * 设备类型
     */
    private String deviceType;
    
    private String username;
    /**
	 * 支付平台来源 PayFromPaltform
	 */
	private String fromPaltform;
	public ConsumerConsume getConsumerConsume() {
		return consumerConsume;
	}

	public void setConsumerConsume(ConsumerConsume consumerConsume) {
		this.consumerConsume = consumerConsume;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public List<Integer> getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(List<Integer> activityIds) {
		this.activityIds = activityIds;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getRollbackPoint() {
		return rollbackPoint;
	}

	public void setRollbackPoint(Integer rollbackPoint) {
		this.rollbackPoint = rollbackPoint;
	}

	public Short getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(Short consumeType) {
		this.consumeType = consumeType;
	}

	public Map<Integer, Integer> getPropMap() {
		return propMap;
	}

	public void setPropMap(Map<Integer, Integer> propMap) {
		this.propMap = propMap;
	}

	public Integer getMonthlyId() {
		return monthlyId;
	}

	public void setMonthlyId(Integer monthlyId) {
		this.monthlyId = monthlyId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getRewardsMoney() {
		return rewardsMoney;
	}

	public void setRewardsMoney(Integer rewardsMoney) {
		this.rewardsMoney = rewardsMoney;
	}

	public UserMonthly getUserMonthly() {
		return userMonthly;
	}

	public void setUserMonthly(UserMonthly userMonthly) {
		this.userMonthly = userMonthly;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getLuckyBagId() {
		return luckyBagId;
	}

	public void setLuckyBagId(Integer luckyBagId) {
		this.luckyBagId = luckyBagId;
	}

	public String getFromPaltform() {
		return fromPaltform;
	}

	public void setFromPaltform(String fromPaltform) {
		this.fromPaltform = fromPaltform;
	}

}
