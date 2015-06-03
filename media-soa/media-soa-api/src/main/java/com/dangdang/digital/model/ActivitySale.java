package com.dangdang.digital.model;

import java.util.Date;

public class ActivitySale {
    private Integer activitySaleId;

    private Integer activityId;

    private Long saleId;

    private String saleName;

    private Date startTime;

    private Date endTime;

    private Integer status;
    
    /**
	 * 上下架状态 0:下架 1:上架
	 */
	private Integer shelfStatus;
	
    private Integer sort;

    private Long salePrice;
    
    private String ids;

    public Integer getActivitySaleId() {
        return activitySaleId;
    }

    public void setActivitySaleId(Integer activitySaleId) {
        this.activitySaleId = activitySaleId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(Integer shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}