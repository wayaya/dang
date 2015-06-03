package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月15日 下午5:45:25  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class EbookConsRecord implements Serializable{
	private static final long serialVersionUID = -3959490761728879595L;

	private Long mediaEbookConsRecordId;

    /**
     * 显示的名字。
     */
    private String username;
    
    
    /**
     *头像地址 
     */
    private String userImgUrl;

    private Long custId;
    
    /**
     * 书的id	
     */
    private Long mediaId;

    /**
     * 销售主体id
     */
    private Long saleId;
   

    /**
     * 书的名字
     */
    private String mediaName;

    /**
     * 封面地址
     */
    private String mediaUrl;

    /**
     * 消费的金钱数
     */
    private Integer cons;

    /**
     * 频道
     */
    private String channel;

    private Date creationDate;
    /**
     * 设备类型
     */
    private String deviceType;

    public EbookConsRecord(){};
    
    //打赏的构造方法
    public EbookConsRecord(Long custId,String username,Long mediaId,Long saleId,String mediaName,String mediaUrl,int cons,String channel,Date creationDate,String userImgUrl,String deviceType){
    	this.custId = custId;
    	this.username = username;
    	this.mediaId = mediaId;
    	this.saleId = saleId;
    	this.mediaName = mediaName;
    	this.mediaUrl = mediaUrl;
    	this.cons = cons;
    	this.channel = channel;
    	this.creationDate = creationDate;
    	this.userImgUrl = userImgUrl;
    	this.deviceType = deviceType;
    }
    
    
    public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Long getMediaEbookConsRecordId() {
        return mediaEbookConsRecordId;
    }

    public void setMediaEbookConsRecordId(Long mediaEbookConsRecordId) {
        this.mediaEbookConsRecordId = mediaEbookConsRecordId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }


    public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public Integer getCons() {
        return cons;
    }

    public void setCons(Integer cons) {
        this.cons = cons;
    }


    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	@Override
	public String toString() {
		return "EbookConsRecord [mediaEbookConsRecordId="
				+ mediaEbookConsRecordId + ", username=" + username
				+ ", userImgUrl=" + userImgUrl + ", custId=" + custId
				+ ", mediaId=" + mediaId + ", saleId=" + saleId
				+ ", mediaName=" + mediaName + ", mediaUrl=" + mediaUrl
				+ ", cons=" + cons + ", channel=" + channel + ", creationDate="
				+ creationDate + ", deviceType=" + deviceType + "]";
	}
	
	
    
}