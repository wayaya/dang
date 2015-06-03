package com.dangdang.digital.service.nearby.api.nearby.bean;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Description: 分页数据集
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:13:20  by 林永奇（linyongqi@dangdang.com）创建
 */
public class DataPage implements Serializable {

    private List<LocationEntity> pageData = Collections.emptyList();
    private int skip;

    private int limit;

    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<LocationEntity> getPageData() {
        return pageData;
    }


    public void setPageData(List<LocationEntity> pageData) {
        this.pageData = pageData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataPage dataPage = (DataPage) o;

        if (limit != dataPage.limit) return false;
        if (skip != dataPage.skip) return false;
        if (total != dataPage.total) return false;
        if (pageData != null ? !pageData.equals(dataPage.pageData) : dataPage.pageData != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageData != null ? pageData.hashCode() : 0;
        result = 31 * result + skip;
        result = 31 * result + limit;
        result = 31 * result + (int) (total ^ (total >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataPage{");
        sb.append("pageData=").append(pageData);
        sb.append(", skip=").append(skip);
        sb.append(", limit=").append(limit);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
