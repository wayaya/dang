package com.dangdang.digital.model;

import java.io.Serializable;

/**
 * 
 * Description: 用户消费明细实体
 * All Rights Reserved.
 * @version 1.0  2014年11月13日 下午3:19:36  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public class ConsumerConsumeDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 896804684920762790L;
    private Long consumerConsumeDeatilId;

    private Long consumerConsumeId;

    private Long mediaId;

    private String mediaName;

    private Integer mediaConsumeChapterNum;

    private Integer propId;

    private String propName;

    private Integer totalPrice;

    private Integer prePrice;

    private Integer mainGoldPrice;

    private Integer subGoldPrice;

    private Integer propCount;

    public Long getConsumerConsumeDeatilId() {
        return consumerConsumeDeatilId;
    }

    public void setConsumerConsumeDeatilId(Long consumerConsumeDeatilId) {
        this.consumerConsumeDeatilId = consumerConsumeDeatilId;
    }

    public Long getConsumerConsumeId() {
        return consumerConsumeId;
    }

    public void setConsumerConsumeId(Long consumerConsumeId) {
        this.consumerConsumeId = consumerConsumeId;
    }

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

	public Integer getMediaConsumeChapterNum() {
		return mediaConsumeChapterNum;
	}

	public void setMediaConsumeChapterNum(Integer mediaConsumeChapterNum) {
		this.mediaConsumeChapterNum = mediaConsumeChapterNum;
	}

	public Integer getPropId() {
        return propId;
    }

    public void setPropId(Integer propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName == null ? null : propName.trim();
    }

    public Integer getMainGoldPrice() {
        return mainGoldPrice;
    }

    public void setMainGoldPrice(Integer mainGoldPrice) {
        this.mainGoldPrice = mainGoldPrice;
    }

    public Integer getSubGoldPrice() {
        return subGoldPrice;
    }

    public void setSubGoldPrice(Integer subGoldPrice) {
        this.subGoldPrice = subGoldPrice;
    }

    public Integer getPropCount() {
        return propCount;
    }

    public void setPropCount(Integer propCount) {
        this.propCount = propCount;
    }

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(Integer prePrice) {
		this.prePrice = prePrice;
	}
}