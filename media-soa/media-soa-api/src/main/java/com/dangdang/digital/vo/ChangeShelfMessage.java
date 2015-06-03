package com.dangdang.digital.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description:上下架消息 All Rights Reserved.
 * 
 * @version 1.0 2015年1月4日 下午1:56:08 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ChangeShelfMessage implements Serializable {

	private static final long serialVersionUID = 7080181267757335865L;

	/**
	 * 售后主体id
	 */
	private List<Long> saleIds;

	/**
	 * 上下架状态 Constans.MEDIA_SHELF_STATUS_UP Constans.MEDIA_SHELF_STATUS_DOWN
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Long> getSaleIds() {
		return saleIds;
	}

	public void setSaleIds(List<Long> saleIds) {
		this.saleIds = saleIds;
	}

}
