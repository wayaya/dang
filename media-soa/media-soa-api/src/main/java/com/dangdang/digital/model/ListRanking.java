package com.dangdang.digital.model;
/**
 * 
 * Description: 销售榜单数据
 * All Rights Reserved.
 * @version 1.0  2014年12月4日 下午5:37:22  by 吕翔  (lvxiang@dangdang.com) 创建
 */

	public class ListRanking implements java.io.Serializable{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private Long listId;

		private Long saleId;
	    
	    private Long mediaId;

	    private String mediaName;
	    
	    private String mediaCover;

		private String penName;

	    private String mediaIntroduce;

	    private String listType;

	    private Integer issue;

	    private Long counts;

	    private String lastChangeDate;

	    private Integer appointOrder;

	    private String operator;

	    private String operateTime;
	    
	    //榜单日期
	    private String rankDay;

	    private Integer status;

	    //第二排序值,评论榜评星数
	    private Float orderOne;
	    
	    //上下架状态
	    private Integer shelfStatus;
	    
	    //media的分类path,多个以,分隔
	    private String mediaPaths;
	    
	    
	    
	    public String getMediaPaths() {
			return mediaPaths;
		}

		public void setMediaPaths(String mediaPaths) {
			this.mediaPaths = mediaPaths;
		}

		public String getRankDay(){
	    	return this.rankDay;
	    }
	    
	    public void setRankDay(String rankDay){
	    	this.rankDay =  rankDay == null ? null:rankDay.trim().isEmpty()?null:rankDay.trim();
	    }
	    public Integer getShelfStatus() {
			return shelfStatus;
		}

		public void setShelfStatus(Integer shelfStatus) {
			this.shelfStatus = shelfStatus;
		}

		public Float getOrderOne() {
	        return orderOne;
	    }

	    public void setOrderOne(Float orderOne) {
	        this.orderOne = orderOne;
	    }
	    
	    public Long getListId() {
	        return listId;
	    }

	    public void setListId(Long listId) {
	        this.listId = listId;
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
	        this.mediaName = mediaName == null ? null : mediaName.trim();
	    }

	    public String getPenName() {
	        return penName;
	    }

	    public void setPenName(String penName) {
	        this.penName = penName == null ? null : penName.trim();
	    }

	    public String getMediaIntroduce() {
	        return mediaIntroduce;
	    }

	    public void setMediaIntroduce(String mediaIntroduce) {
	        this.mediaIntroduce = mediaIntroduce == null ? null : mediaIntroduce.trim();
	    }


	    public String getListType() {
	        return listType;
	    }

	    public void setListType(String listType) {
	        this.listType = listType == null ? null : listType.trim();
	    }

	    public Integer getIssue() {
	        return issue;
	    }

	    public void setIssue(Integer issue) {
	        this.issue = issue;
	    }

	    public Long getCounts() {
	        return counts;
	    }

	    public void setCounts(Long counts) {
	        this.counts = counts;
	    }

	    public String getLastChangeDate() {
	        return lastChangeDate;
	    }

	    public void setLastChangeDate(String lastChangeDate) {
	        this.lastChangeDate = lastChangeDate==null?null:lastChangeDate.trim();
	    }

	    public Integer getAppointOrder() {
	        return appointOrder;
	    }

	    public void setAppointOrder(Integer appointOrder) {
	        this.appointOrder = appointOrder;
	    }

	    public String getOperator() {
	        return operator;
	    }

	    public void setOperator(String operator) {
	        this.operator = operator == null ? null : operator.trim();
	    }

	    public String getOperateTime() {
	        return operateTime;
	    }

	    public void setOperateTime(String operateTime) {
	        this.operateTime = operateTime==null?null:operateTime.trim();
	    }

	    public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public Long getSaleId() {
			return saleId;
		}

		public void setSaleId(Long saleId) {
			this.saleId = saleId;
		}
	  
		public String getMediaCover() {
			return mediaCover;
		}

		public void setMediaCover(String mediaCover) {
			this.mediaCover = mediaCover==null?null:mediaCover.trim();
		}
}