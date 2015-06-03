package com.dangdang.digital.service.nearby.impl.nearby.bean;

import com.dangdang.digital.service.nearby.api.nearby.bean.ISolrDoc;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserLocation;
import com.dangdang.digital.service.nearby.impl.nearby.util.MapUtils;
import org.apache.solr.common.SolrInputDocument;

import java.util.Date;
/**
 * 
 * Description: solr doc实现
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:17:50  by 林永奇（linyongqi@dangdang.com）创建
 */
public class SolrLocation implements ISolrDoc {

    //用户提交的地理位置
    private UserLocation location;
    private String custId;
    private String displayId;
    private String geocode;

    public SolrLocation(String custId,String displayId,UserLocation location,String geocode) {
        this.location = location;
        if(null == this.location.getTime()){
            this.location.setTime(new Date());
        }
        this.geocode = geocode;
        this.custId = custId;
        this.displayId=displayId;
    }

    @Override
    public SolrInputDocument wrap2SolrDoc() {

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("uid",this.custId);
        doc.addField("display_id",this.displayId);
        doc.addField("geoloc", MapUtils.toMap("set", location.getLat() + "," + location.getLng()));
        doc.addField("active_time_min",MapUtils.toMap("set",location.getTime().getTime()/60000));
        doc.addField("geocode",MapUtils.toMap("set",this.geocode));
        return doc;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolrLocation that = (SolrLocation) o;

        if (geocode != null ? !geocode.equals(that.geocode) : that.geocode != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (custId != null ? !custId.equals(that.custId) : that.custId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (custId != null ? custId.hashCode() : 0);
        result = 31 * result + (geocode != null ? geocode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SolrLocation{" +
                "location=" + location +
                ", custId='" + custId + '\'' +
                ", geocode='" + geocode + '\'' +
                "} " + super.toString();
    }
}
