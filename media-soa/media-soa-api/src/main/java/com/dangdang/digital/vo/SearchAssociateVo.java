package com.dangdang.digital.vo;

import java.io.Serializable;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2014年12月27日 下午3:42:42 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public class SearchAssociateVo implements Serializable {
	private static final long serialVersionUID = 1896081532656156501L;

	/**
	 * 关键字名称
	 */
	private String keyword;

	/**
	 * 关键字拼音
	 */
	private String pinyin;

	/**
	 * 大约结果数
	 */
	private Integer count;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}