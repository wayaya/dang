package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * 
 * Description: 单品页返回basevo All Rights Reserved.
 * 
 * @version 1.0 2015年5月20日 下午5:52:54 by 魏嵩（weisong@dangdang.com）创建
 */
public class ReturnMediaBaseVo implements Serializable {

	private static final long serialVersionUID = -8785826428732226191L;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 子标题
	 */
	private String subTitle;

	/**
	 * 描述
	 */
	private String descs;

	/**
	 * 封面图
	 */
	private String coverPic;

	/**
	 * 作者Id
	 */
	private Long authorId;

	/**
	 * 笔名
	 */
	private String authorPenname;

	/**
	 * 推荐语
	 */
	private String recommandWords;

	/**
	 * 作者介绍
	 */
	private String authorIntroduction;

	/**
	 * 作者头像
	 */
	private String authorHeadPic;

	/**
	 * 总字数
	 */
	private Long wordCnt;
	
	/**
	 * 分类id(逗号分隔)
	 */
	private String categoryIds;

	/**
	 * 分类名称（逗号分隔）
	 */
	private String categorys;

	/**
	 * 评分
	 */
	private Float score;

	/**
	 * 书评总数
	 */
	private Long bookReviewCount;
	
	/**
	 * 原创  1, 出版物 2, 纸书 3
	 */
	private Integer mediaType;
	
	/**
	 * 频道Id，这个id需要在频道的书单列表带到单品页，然后带到购物车，再带到订单
	 * @return
	 */
	private Long channelId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorPenname() {
		return authorPenname;
	}

	public void setAuthorPenname(String authorPenname) {
		this.authorPenname = authorPenname;
	}

	public String getRecommandWords() {
		return recommandWords;
	}

	public void setRecommandWords(String recommandWords) {
		this.recommandWords = recommandWords;
	}

	public String getAuthorIntroduction() {
		return authorIntroduction;
	}

	public void setAuthorIntroduction(String authorIntroduction) {
		this.authorIntroduction = authorIntroduction;
	}

	public String getAuthorHeadPic() {
		return authorHeadPic;
	}

	public void setAuthorHeadPic(String authorHeadPic) {
		this.authorHeadPic = authorHeadPic;
	}

	public Long getWordCnt() {
		return wordCnt;
	}

	public void setWordCnt(Long wordCnt) {
		this.wordCnt = wordCnt;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getBookReviewCount() {
		return bookReviewCount;
	}

	public void setBookReviewCount(Long bookReviewCount) {
		this.bookReviewCount = bookReviewCount;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}


}
