package com.dangdang.digital.model;

import java.util.Collection;

/**
 * 
 * Description: 收藏bean
 * All Rights Reserved.
 * @version 1.0  2014年12月23日 下午2:33:32  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class StoreUp {
	
    private Long storeId;

    private String type;

    private Long targetId;

    private String storeDate;

    private Long custId;

    private String userName;

    private String nickName;

    /**
     * 平台来源
     */
    private String platform;
    
    //非入库字段 ,add by:daipeng
    private Collection<Long> targetIds;
    
    public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null :platform.trim();
	}
	
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(String storeDate) {
        this.storeDate = storeDate==null? null : storeDate.trim();;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
    
    public Collection<Long> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(Collection<Long> targetIds) {
		this.targetIds = targetIds;
	}

	public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("type=").append(this.type)
    	  .append("targetId=").append(this.targetId)
    	  .append("userName=").append(this.userName)
    	  .append("platform=").append(this.platform);
    	return sb.toString();
    			
    }
}