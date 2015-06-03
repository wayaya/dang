package com.dangdang.digital.service.nearby.api.nearby.bean;


import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Description: 位置实体
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:13:51  by 林永奇（linyongqi@dangdang.com）创建
 */
public class LocationEntity
        implements Serializable
{
    private static final long serialVersionUID = -6222363163683150138L;
    private String custId;
    private String displayId;
    private double lat = 0.0D;

    private double lng = 0.0D;


    private long time = 0L;

    private double distance = 0.0D;
    private int deviation;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

    public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public static LocationEntity wrapProp2Object(String custId,String displayId, UserLocation location)
    {
        LocationEntity userLocation = new LocationEntity();
        try {
            userLocation.setCustId(custId);
            userLocation.setDisplayId(displayId);
            userLocation.setLat(location.getLat());
            userLocation.setLng(location.getLng());
            userLocation.setDeviation(location.getDeviation());
            Date locationTime = location.getTime();
            userLocation.setTime(null == locationTime ? 0L : locationTime.getTime() / 1000L);
        }
        catch (Exception e)
        {
        }
        return userLocation;
    }

  

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationEntity other = (LocationEntity) obj;
		if (custId == null) {
			if (other.custId != null)
				return false;
		} else if (!custId.equals(other.custId))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "LocationEntity{" +
                "custId='" + custId + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", time=" + time +
                ", distance=" + distance +
                ", deviation=" + deviation +
                '}';
    }
}