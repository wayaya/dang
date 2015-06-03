/**
 * 
 */
package com.dangdang.digital.service.nearby.api.nearby.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Description: 用户地理位置
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:14:36  by 林永奇（linyongqi@dangdang.com）创建
 */
public class UserLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int LOC_TYPE_UNFIXED = 0;
	public static final int LOC_UNAVAILABLE = 0;
	
	private double lat;
	private double lng;
	private double slat = LOC_UNAVAILABLE;
	private double slng = LOC_UNAVAILABLE;
	private Date time;
	private int deviation = 0;

	public UserLocation() {

	}

	public UserLocation(double lat, double lng, double acc, Date time) {
		this(lat, lng, acc, time, null);
	}
	
	public UserLocation(int deviation, double lat, double lng, double acc, Date time) {
		this(deviation, lat, lng, acc, time, null, 0);
	}

	public UserLocation(double lat, double lng, double acc, Date time, String ip) {
		this(lat, lng, acc, time, ip, 0);
	}
	
	public UserLocation(int deviation, double lat, double lng, double acc, Date time, String ip) {
		this(lat, lng, acc, time, ip, 0);
	}

	public UserLocation(double lat, double lng, double acc, Date time, String ip, int locType) {
		this(0, lat, lng, acc, time, ip, locType);
	}
	
	public UserLocation(int deviation, double lat, double lng, double acc, Date time, String ip, int locType) {
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.deviation = deviation;
	}
	
	public UserLocation(double slat, double slng, double lat, double lng, double acc, Date time, String ip, int locType) {
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.slat = slat;
		this.slng = slng;
	}
	
	public double getSlat() {
		return slat;
	}

	public void setSlat(double slat) {
		this.slat = slat;
	}

	public double getSlng() {
		return slng;
	}

	public void setSlng(double slng) {
		this.slng = slng;
	}



	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}


	public int getDeviation() {
		return deviation;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("(%f, %f) ", lat, lng));
		buf.append(time);
		buf.append(deviation);
		return buf.toString();
	}

	public static void main(String[] args) {
		System.out.println("111");
	}
}
