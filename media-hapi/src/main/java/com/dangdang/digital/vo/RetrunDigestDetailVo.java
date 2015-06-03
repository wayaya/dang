/**
 * Description: RetrunDigestDetailVo.java
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午3:55:09  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.model.GoodArticleSign;

/**
 * Description: 精品详情页Vo
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午3:55:09  by 代鹏（daipeng@dangdang.com）创建
 */
public class RetrunDigestDetailVo implements Serializable {

	private static final long serialVersionUID = -8509412101833386144L;
	
	/**
	 * 精品id
	 */
	private Long digestId;
	
	/**
	 * 标题
	 */
	private String cardTitle;
	
	/**
	 * 心情类别
	 */
	private Integer mood;
	
	/**
	 * 对应作者集合
	 */
	private List<BookAuthor> authorList;
	
	/**
	 * 精品卡片类型
	 */
	private Integer cardType;
	
	/**
	 * 精品对应标签集
	 */
	private List<GoodArticleSign> signList;
	
	/**
	 * 正文对应h5访问的url(相对地址)
	 */
	private String cardUrl;
	
	/**
	 * 精品评论数
	 */
	private Integer reviewCnt;
	
	/**
	 * 点赞数
	 */
	private Integer topCnt;
	
	/**
	 * 是否已经收藏
	 */
	private boolean alreayMark;
	
	/**
	 * 精品摘自的单品id
	 */
	private Long mediaId;
	
	/**
	 * 精品卡片url
	 */
	private String pic1Path;
	
	/**
	 * 精品文章摘要
	 */
	private String cardRemark;
	
	private EbookInfo ebookInfo;
	
	public Integer getTopCnt() {
		return topCnt;
	}

	public void setTopCnt(Integer topCnt) {
		this.topCnt = topCnt;
	}

	public Long getDigestId() {
		return digestId;
	}

	public void setDigestId(Long digestId) {
		this.digestId = digestId;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public Integer getMood() {
		return mood;
	}

	public void setMood(Integer mood) {
		this.mood = mood;
	}

	public List<BookAuthor> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<BookAuthor> authorList) {
		this.authorList = authorList;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public List<GoodArticleSign> getSignList() {
		return signList;
	}

	public void setSignList(List<GoodArticleSign> signList) {
		this.signList = signList;
	}

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public Integer getReviewCnt() {
		return reviewCnt;
	}

	public void setReviewCnt(Integer reviewCnt) {
		this.reviewCnt = reviewCnt;
	}

	public boolean isAlreayMark() {
		return alreayMark;
	}

	public void setAlreayMark(boolean alreayMark) {
		this.alreayMark = alreayMark;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	
	public EbookInfo getEbookInfo() {
		if(ebookInfo == null){
			return  new EbookInfo();
		}
		return ebookInfo;
	}

	public void setEbookInfo(EbookInfo ebookInfo) {
		this.ebookInfo = ebookInfo;
	}
	
	public String getPic1Path() {
		return pic1Path;
	}

	public void setPic1Path(String pic1Path) {
		this.pic1Path = pic1Path;
	}
	
	public String getCardRemark() {
		return cardRemark;
	}

	public void setCardRemark(String cardRemark) {
		this.cardRemark = cardRemark;
	}
	

	public class EbookInfo implements Serializable{
		
		private static final long serialVersionUID = 2430644815359402105L;
		
		/**
		 * 单品id
		 */
		private Long productId;
		
		/**
		 * 精品摘自的单品id
		 */
		private Long mediaId;

		/**
		 * 单品作者
		 */
		private String bookName;
		
		/**
		 * 单品封面url
		 */
		private String bookImgUrl;
		
		/**
		 * 单品作者
		 */
		private String bookAuthor;
		
		/**
		 * 单品编辑推荐
		 */
		private String editorRecommend;
		
		public Long getMediaId() {
			return mediaId;
		}

		public void setMediaId(Long mediaId) {
			this.mediaId = mediaId;
		}

		public String getBookName() {
			return bookName;
		}

		public void setBookName(String bookName) {
			this.bookName = bookName;
		}

		public String getBookImgUrl() {
			return bookImgUrl;
		}

		public void setBookImgUrl(String bookImgUrl) {
			this.bookImgUrl = bookImgUrl;
		}

		public String getBookAuthor() {
			return bookAuthor;
		}

		public void setBookAuthor(String bookAuthor) {
			this.bookAuthor = bookAuthor;
		}

		public String getEditorRecommend() {
			return editorRecommend;
		}

		public void setEditorRecommend(String editorRecommend) {
			this.editorRecommend = editorRecommend;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}
	}
	
}
