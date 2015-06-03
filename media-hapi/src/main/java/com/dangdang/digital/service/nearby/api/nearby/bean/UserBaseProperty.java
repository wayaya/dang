package com.dangdang.digital.service.nearby.api.nearby.bean;


import java.io.Serializable;
import java.util.Date;

import org.apache.solr.common.SolrInputDocument;

import com.dangdang.digital.service.nearby.impl.nearby.util.MapUtils;
/**
 * 
 * Description:用户基本信息实体 
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:14:21  by 林永奇（linyongqi@dangdang.com）创建
 */
public class UserBaseProperty implements Serializable, ISolrDoc {


    //custId
    private String custId;


    private UserLocation location;

    private String geocode;

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }



    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
        if (null == this.location.getTime()) {
            this.location.setTime(new Date());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBaseProperty property = (UserBaseProperty) o;
        if (location != null ? !location.equals(property.location) : property.location != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = custId != null ? custId.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (geocode != null ? geocode.hashCode() : 0);
        return result;
    }

    @Override
    public SolrInputDocument wrap2SolrDoc() {

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("uid",this.custId);
        if(null != location){
            doc.addField("geoloc", MapUtils.toMap("set",location.getLat()+","+location.getLng()));
            doc.addField("active_time_min", MapUtils.toMap("set",(location.getTime().getTime() / 60000)));
            doc.addField("geocode",MapUtils.toMap("set", geocode));
        }
        return doc;
    }
}
