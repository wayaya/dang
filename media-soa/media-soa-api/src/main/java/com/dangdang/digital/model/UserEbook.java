package com.dangdang.digital.model;

import java.util.Date;
import java.util.Map;

import javacommon.util.CollectionUtils;
import javacommon.util.DateTimeUtils;

import com.dangdang.digital.constant.RelationTypeEnum;

/**
 * @author dangdang
 */


public class UserEbook  implements java.io.Serializable {

	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	public String date2String(final java.util.Date date, final String dateFormat) {
		return DateTimeUtils.format(date, dateFormat);
	}

	public <T extends java.util.Date> T string2Date(
			final String dateString, final String dateFormat,
			final Class<T> targetResultType) {
		return DateTimeUtils.parse(dateString, dateFormat, targetResultType);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "UserEbook";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_CUST_ID = "用户ID";
	public static final String ALIAS_USERNAME = "用户名";
	public static final String ALIAS_EBOOK_ID = "ebookId";
	public static final String ALIAS_PRODUCT_ID = "单品ID";
	public static final String ALIAS_CREATED = "创建时间";
	public static final String ALIAS_CREATOR = "创建人";
	public static final String ALIAS_TYPE = "类型";
	public static final String ALIAS_STATE = "状态";
	public static final String ALIAS_START = "起始时间";
	public static final String ALIAS_END = "结束时间";
	public static final String ALIAS_READING_CARD_ID = "阅读卡ID";
	
	public static final int TYPE_FREE = -1; // 免费阅读
	public static final int TYPE_BUY = 1; // 免费阅读
	
	public static final int TYPE_READING_CARD = 20; //阅读卡
	
	//type字段对应的值
	public static final int ORDER_TYPE_IAP = 10000;
	public static final int ORDER_TYPE_PROMOTION = 50;
	public static final int LENDING_RELATION_TYPE = 1003;
	
	public static final Map<Integer, String> TYPE_MAP = CollectionUtils.buildMap(
														TYPE_FREE, "免费阅读");
	
	public static final int STATE_DEAD = 0; // 无效
	public static final int STATE_LIVE = 1; // 有效
	
	//date formats
	public static final String FORMAT_CREATED = DATE_TIME_FORMAT;
	public static final String FORMAT_STARTED = DATE_TIME_FORMAT;
	public static final String FORMAT_ENDED = DATE_TIME_FORMAT;
	
	//columns START
	private Long id;
	private String username;
	private Date created;
	private String creator;
	private Long ebookId;
	private Integer type;
	private Integer state;
	private Date start;
	private Date end;
	private Long custId;
	private Long readingCardId;
	private String uid;
	private Long productId;
	private Long orderId;
	private Long orderItemId;
	private Long promotionId;
	
	private String relationType = RelationTypeEnum.RELATION_TYPE_BUY.getValue();
	private Long deadline;
	private Integer isReturnBook;
	//columns END

	public UserEbook() {
	}

	public UserEbook(Long id) {
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setUsername(String value) {
		this.username = value;
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getCreatedString() {
		return date2String(getCreated(), FORMAT_CREATED);
	}
	public void setCreatedString(String value) {
		setCreated(string2Date(value, FORMAT_CREATED, Date.class));
	}
	
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public String getTypeString() {
		return TYPE_MAP.get(getType());
	}
	
	public void setState(Integer value) {
		this.state = value;
	}
	
	public Integer getState() {
		return this.state;
	}
	
	public String getStateString() {
		return state == STATE_DEAD ? "过期" : "有效";
	}
	
	public String getStartString() {
		return date2String(getStart(), FORMAT_STARTED);
	}
	public void setStartString(String value) {
		setStart(string2Date(value, FORMAT_STARTED, java.sql.Timestamp.class));
	}
	
	public String getEndString() {
		return date2String(getEnd(), FORMAT_ENDED);
	}
	public void setEndString(String value) {
		setEnd(string2Date(value, FORMAT_ENDED, java.sql.Timestamp.class));
	}
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getReadingCardId() {
		return readingCardId;
	}

	public void setReadingCardId(Long readingCardId) {
		this.readingCardId = readingCardId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * @return the relationType
	 */
	public String getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType the relationType to set
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	/**
	 * @return the deadline
	 */
	public Long getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the isReturnBook
	 */
	public Integer getIsReturnBook() {
		return isReturnBook;
	}

	/**
	 * @param isReturnBook the isReturnBook to set
	 */
	public void setIsReturnBook(Integer isReturnBook) {
		this.isReturnBook = isReturnBook;
	}

	/**
	 * @return the ebookId
	 */
	public Long getEbookId() {
		return ebookId;
	}

	/**
	 * @param ebookId the ebookId to set
	 */
	public void setEbookId(Long ebookId) {
		this.ebookId = ebookId;
	}
	
}

