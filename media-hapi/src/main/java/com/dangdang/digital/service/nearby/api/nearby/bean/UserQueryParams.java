package com.dangdang.digital.service.nearby.api.nearby.bean;


import java.io.Serializable;

/**
 * 
 * Description: 查询参数
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:14:49  by 林永奇（linyongqi@dangdang.com）创建
 */
public class UserQueryParams implements Serializable {

    private static final long serialVersionUID = -2079189132128846652L;

    private String custId;


    private double lat;

    private double lng;


    /**
     * 距离 单位为公里
     */
    private double distance;

    private String geocode;

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserQueryParams that = (UserQueryParams) o;

        if (Double.compare(that.distance, distance) != 0) return false;
        if (Double.compare(that.lat, lat) != 0) return false;
        if (Double.compare(that.lng, lng) != 0) return false;
        if (custId != null ? !custId.equals(that.custId) : that.custId != null) return false;
        if (geocode != null ? !geocode.equals(that.geocode) : that.geocode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = custId != null ? custId.hashCode() : 0;
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (geocode != null ? geocode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserQueryParams{" +
                "custId='" + custId + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", distance=" + distance +
                ", geocode='" + geocode + '\'' +
                '}';
    }
}
