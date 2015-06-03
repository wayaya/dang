package com.dangdang.digital.constant;

/**
 * 
 * Description: 举报类型 All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午8:31:33 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public enum ReportTypeEnum {
	WGNR("WGNR", "违规内容"), GG("GG", "广告"), YZ("YZ", "音质"), BQ("BQ", "版权");

	private String code;
	private String name;

	private ReportTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ReportTypeEnum getByCode(String code) {
		for (ReportTypeEnum reportType : ReportTypeEnum.values()) {
			if (reportType.getCode().equals(code)) {
				return reportType;
			}
		}
		return null;
	}
}
