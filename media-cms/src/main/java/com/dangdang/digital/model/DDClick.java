package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class DDClick implements Serializable{
	private static final long serialVersionUID = 1L;
	//未知action对应id
	public static Integer NULL_ACTION_ID = 10000;
	//未知action对应type
	public static Integer NULL_ACTION_TYPE = 1;
	//时长类对应的actionType
	public static Integer DURATION_ACTION_TYPE = 2;
	
	@Id
	private String id;
	
	@Indexed
	private Date actionTime;
	
	@Indexed
	private Integer appId;
	
	@Indexed
	private String deviceType;
	private String ip;
	
	@Indexed
	private String clientOs;
	private String clientVersionNo;
	private String ServerVersionNo;
	private String deviceSerialNo;
	private String resolution;
	private String macAddr;
	private String offlineActionTime;
	@Indexed
	private String channelId;
	@Indexed
	private Integer actionId;
	@Indexed
	private String actionName;
	private Integer actionType;
	private String permanentId;
	private String parameter;
	@Indexed
	private Integer activityId;
	private Integer unionId;
	private Integer useTime;
	private Integer productId;
	@Indexed
	private Integer categoryId;
	private String categoryPath;
	@Indexed
	private Long custId;
	@Indexed
	private Long mediaId;
	@Indexed
	private Long saleId;
	@Indexed
	private String refAction;
	@Indexed
	private String columnType;
	private Date updateTime;
	@Indexed
	private Integer returnCode;
	
	@Indexed
	private String extId1;
	@Indexed
	private String extId2;
	@Indexed
	private String extId3;
	
	private String msg;
		
	private String token;
	
	private String oneKeyBuy;
	
	//厂商
	private String producer;
	//手机型号
	private String phoneModel;
	private String model;
	
	//返回的状态和返回的json内容
	private int statusCode;
	private String resultContent;
	
	//发送到kafka的状态
	private Integer kafkaCode; 
	
	private String refer;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getActionTime() {
		return actionTime;
	}
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getClientOs() {
		return clientOs;
	}
	public void setClientOs(String clientOs) {
		this.clientOs = clientOs;
	}
	
	public String getClientVersionNo() {
		return clientVersionNo;
	}
	public void setClientVersionNo(String clientVersionNo) {
		this.clientVersionNo = clientVersionNo;
	}
	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}
	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public String getPermanentId() {
		return permanentId;
	}
	public void setPermanentId(String permanentId) {
		this.permanentId = permanentId;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public Integer getActivityId() {
		return activityId;
	}
	
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public Integer getUnionId() {
		return unionId;
	}
	
	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
	}
	
	public Integer getUseTime() {
		return useTime;
	}
	
	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getServerVersionNo() {
		return ServerVersionNo;
	}
	public void setServerVersionNo(String serverVersionNo) {
		ServerVersionNo = serverVersionNo;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getOneKeyBuy() {
		return oneKeyBuy;
	}
	public void setOneKeyBuy(String oneKeyBuy) {
		this.oneKeyBuy = oneKeyBuy;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getResultContent() {
		return resultContent;
	}
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
	
	public String getProducer() {
		return producer;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	public String getPhoneModel() {
		return phoneModel;
	}
	
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	
	public Integer getKafkaCode() {
		return kafkaCode;
	}
	
	public void setKafkaCode(Integer kafkaCode) {
		this.kafkaCode = kafkaCode;
	}
	public String getRefer() {
		return refer;
	}
	public void setRefer(String refer) {
		this.refer = refer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOfflineActionTime() {
		return offlineActionTime;
	}
	public void setOfflineActionTime(String offlineActionTime) {
		this.offlineActionTime = offlineActionTime;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId=mediaId;
	}
	public Long getMediaId() {
		return mediaId;
	}
	public String getRefAction() {
		return refAction;
	}
	public void setRefAction(String refAction) {
		this.refAction = refAction;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	public String getExtId1() {
		return extId1;
	}
	public void setExtId1(String extId1) {
		this.extId1 = extId1;
	}
	public String getExtId2() {
		return extId2;
	}
	public void setExtId2(String extId2) {
		this.extId2 = extId2;
	}
	public String getExtId3() {
		return extId3;
	}
	public void setExtId3(String extId3) {
		this.extId3 = extId3;
	}		
	
}
