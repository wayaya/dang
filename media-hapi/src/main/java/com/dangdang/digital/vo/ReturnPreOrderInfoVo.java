package com.dangdang.digital.vo;

import java.io.Serializable;

public class ReturnPreOrderInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4289591438273452907L;

	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 区
	 */
	private String district;
	
	/**
	 * 下单信息（xxx时间内下单，预计xxx到达）
	 */
	private Long predictOrderTime;
	
	/**
	 * 下单信息（xxx时间内下单，预计xxx到达）
	 */
	private Long predictArrivalTime;
	
	/**
	 * 运费
	 */
	private String postalfee;
	/**
	 * 满xx免运费
	 */
	private String freeLimit;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Long getPredictOrderTime() {
		return predictOrderTime;
	}
	public void setPredictOrderTime(Long predictOrderTime) {
		this.predictOrderTime = predictOrderTime;
	}
	public Long getPredictArrivalTime() {
		return predictArrivalTime;
	}
	public void setPredictArrivalTime(Long predictArrivalTime) {
		this.predictArrivalTime = predictArrivalTime;
	}
	public String getPostalfee() {
		return postalfee;
	}
	public void setPostalfee(String postalfee) {
		this.postalfee = postalfee;
	}
	public String getFreeLimit() {
		return freeLimit;
	}
	public void setFreeLimit(String freeLimit) {
		this.freeLimit = freeLimit;
	}
	
}
