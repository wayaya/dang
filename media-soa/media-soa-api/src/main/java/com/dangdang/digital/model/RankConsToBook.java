package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 壕赏榜单实体表
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 下午2:51:06  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class RankConsToBook implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -1969518365252720822L;

	/**
     * ID
     */
    private Long mediaEbookConsRanklistId;

    /**
     * 标识
     */
    private String code;
    
    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String userImgUrl;
    
    /**
     * 用户id
     */
    private Long custId;

    /**
     * 书的ID
     */
    private Long mediaId;

    /**
     * 销售主体id
     */
    private Long saleId;
    
    /**
     * 书名
     */
    private String mediaName;

    /**
     * 书图片地址
     */
    private String mediaUrl;

    /**
     * 消费的金币
     */
    private Integer cons;
    
    /**
     * 前台显示的消耗金币数
     */
    private Integer showCons;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 状态 0：未过期； 1：已过期
     */
    private Integer status;

    /**
     * 榜单类型 1:日;2:周;3:月;4:年'
     */
    private Integer type;

    /**
     * 频道，男频：1，女频：2
     */
    private String channel;
    
    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新时间
     */
    private Date lastModifiedDate;

    /**
     * 最后修改的人
     */
    public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
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

	public void setChannel(String channel) {
		this.channel = channel;
	}

	
    private String modifier;

    public Long getMediaEbookConsRanklistId() {
        return mediaEbookConsRanklistId;
    }

    public void setMediaEbookConsRanklistId(Long mediaEbookConsRanklistId) {
        this.mediaEbookConsRanklistId = mediaEbookConsRanklistId;
    }

    public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
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


    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getShowCons() {
		return showCons;
	}

	public void setShowCons(Integer showCons) {
		this.showCons = showCons;
	}


    public Integer getCons() {
        return cons;
    }

    public void setCons(Integer cons) {
        this.cons = cons;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

	public String getChannel() {
		return channel;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	@Override
	public String toString() {
		return "RankConsToBook [mediaEbookConsRanklistId="
				+ mediaEbookConsRanklistId + ", code=" + code + ", username="
				+ username + ", userImgUrl=" + userImgUrl + ", custId="
				+ custId + ", mediaId=" + mediaId + ", saleId=" + saleId
				+ ", mediaName=" + mediaName + ", mediaUrl=" + mediaUrl
				+ ", cons=" + cons + ", showCons=" + showCons + ", status="
				+ status + ", type=" + type + ", channel=" + channel
				+ ", creationDate=" + creationDate + ", lastModifiedDate="
				+ lastModifiedDate + ", modifier=" + modifier + "]";
	}


    
    
    
}