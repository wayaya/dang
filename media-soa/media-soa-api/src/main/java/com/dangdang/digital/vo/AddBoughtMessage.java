package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description: 添加已购信息消息 All Rights Reserved.
 * 
 * @version 1.0 2015年1月5日 下午3:32:47 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class AddBoughtMessage implements Serializable {

	private static final long serialVersionUID = 7665123843188544334L;

	public enum BoughtTypeEnum {
		LOT_TYPE(1, "抽奖");

		private int key;
		private String name;

		private BoughtTypeEnum(int key, String name) {
			this.key = key;
			this.name = name;
		}

		public int getKey() {
			return key;
		}

		public String getName() {
			return name;
		}

	}

	/**
	 * 已购类型
	 */
	private BoughtTypeEnum boughtType;

	/**
	 * 已购内容
	 */
	private String boughtContent;

	/**
	 * 销售主体Id
	 */
	private Long saleId;

	/**
	 * 书籍Id
	 */
	private Long mediaId;

	/**
	 * 全本标志（1：全本，0：按章）
	 */
	private Integer wholeFlag;

	/**
	 * 章节id列表
	 */
	private List<Long> chapterIds;

	/**
	 * 用户Id
	 */
	private Long custId;

	public BoughtTypeEnum getBoughtType() {
		return boughtType;
	}

	public void setBoughtType(BoughtTypeEnum boughtType) {
		this.boughtType = boughtType;
	}

	public String getBoughtContent() {
		return boughtContent;
	}

	public void setBoughtContent(String boughtContent) {
		this.boughtContent = boughtContent;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Integer getWholeFlag() {
		return wholeFlag;
	}

	public void setWholeFlag(Integer wholeFlag) {
		this.wholeFlag = wholeFlag;
	}

	public List<Long> getChapterIds() {
		return chapterIds;
	}

	public void setChapterIds(List<Long> chapterIds) {
		this.chapterIds = chapterIds;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	@Override
	public String toString() {
		return "AddBoughtMessage [boughtType=" + boughtType + ", boughtContent=" + boughtContent + ", saleId=" + saleId
				+ ", mediaId=" + mediaId + ", wholeFlag=" + wholeFlag + ", chapterIds=" + chapterIds + ", custId="
				+ custId + "]";
	}

}
