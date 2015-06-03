package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;


/**
 * ChannelOwner Entity.
 */
public class ChannelOwner implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2762310376493695672L;
	//date formats
	//审核失败
	public static final int AUDIT_FAIL =1;
	//待审核
	public static final int AUDIT_WATING =0;
	//审核成功
	public static final int AUDIT_SUCCESS =2;
	//运营者为企业
	public static final int TYPE_COMPANY=1;
	//运营者为企业
	public static final int TYPE_PERSON=2;
	//列信息
	private Long ownerId;
	
	private Long custId;
	
	private String name;
	
	private String idNumber;
	
	private String idBackPath;
	
	private String idFrontPath;
	
	private String company;
	
	private String bankSubbranch;
	
	private String bankName;
	
	private String employeeCardPath;
	
	private String bankArea;
	
	private String bankCity;
	
	private String bankProvince;
	
	private String bankOwnerName;
	
	private Long bankCardNumber;
	
	private String phone;
	
	private String verificationCode;
	
	private Integer type;
	
	private String introductionPicPath;
	
	/** 
	 * 成为道长的理由说明
	 */
	private String introduction;
	
	

	private String companyName;
	
	private String licenseNumber;
	
	private String licensePicPath;
	
	private Date createDate;
	
	private Date modifyDate;
	
	private Date auditDate;
	
	private String auditor;
	
	private Integer status;
	
	
	private String failMsg;
	
	/**
	 * 修改次数
	 */
	private Integer modifyTimes;
	
	/**
	 * 申请次数
	 */
	private Integer applyTimes;
		
	/**
	 * 关联审核记录的编号
	 */
	private Long recordId;
	
	
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Integer getApplyTimes() {
		return applyTimes;
	}

	public void setApplyTimes(Integer applyTimes) {
		this.applyTimes = applyTimes;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	
	
	public void setOwnerId(Long value) {
		this.ownerId = value;
	}
	
	public Long getOwnerId() {
		return this.ownerId;
	}
		
		
	public void setCustId(Long value) {
		this.custId = value;
	}
	
	public Long getCustId() {
		return this.custId;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setIdNumber(String value) {
		this.idNumber = value;
	}
	
	public String getIdNumber() {
		return this.idNumber;
	}
		
		
	public void setIdBackPath(String value) {
		this.idBackPath = value;
	}
	
	public String getIdBackPath() {
		return this.idBackPath;
	}
		
		
	public void setIdFrontPath(String value) {
		this.idFrontPath = value;
	}
	
	public String getIdFrontPath() {
		return this.idFrontPath;
	}
		
		
	public void setCompany(String value) {
		this.company = value;
	}
	
	public String getCompany() {
		return this.company;
	}
		
		
	public void setBankSubbranch(String value) {
		this.bankSubbranch = value;
	}
	
	public String getBankSubbranch() {
		return this.bankSubbranch;
	}
		
		
	public void setBankName(String value) {
		this.bankName = value;
	}
	
	public String getBankName() {
		return this.bankName;
	}
		
		
	public void setEmployeeCardPath(String value) {
		this.employeeCardPath = value;
	}
	
	public String getEmployeeCardPath() {
		return this.employeeCardPath;
	}
		
		
	public void setBankArea(String value) {
		this.bankArea = value;
	}
	
	public String getBankArea() {
		return this.bankArea;
	}
		
		
	public void setBankCity(String value) {
		this.bankCity = value;
	}
	
	public String getBankCity() {
		return this.bankCity;
	}
		
		
	public void setBankProvince(String value) {
		this.bankProvince = value;
	}
	
	public String getBankProvince() {
		return this.bankProvince;
	}
		
		
	public void setBankOwnerName(String value) {
		this.bankOwnerName = value;
	}
	
	public String getBankOwnerName() {
		return this.bankOwnerName;
	}
		
		
	public void setBankCardNumber(Long value) {
		this.bankCardNumber = value;
	}
	
	public Long getBankCardNumber() {
		return this.bankCardNumber;
	}
		
		
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
		
		
	public void setVerificationCode(String value) {
		this.verificationCode = value;
	}
	
	public String getVerificationCode() {
		return this.verificationCode;
	}
		
		
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
		
		
	public void setIntroductionPicPath(String value) {
		this.introductionPicPath = value;
	}
	
	public String getIntroductionPicPath() {
		return this.introductionPicPath;
	}
		
		
	public void setCompanyName(String value) {
		this.companyName = value;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
		
		
	public void setLicenseNumber(String value) {
		this.licenseNumber = value;
	}
	
	public String getLicenseNumber() {
		return this.licenseNumber;
	}
		
		
	public void setLicensePicPath(String value) {
		this.licensePicPath = value;
	}
	
	public String getLicensePicPath() {
		return this.licensePicPath;
	}
		
		
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
		
		
	public void setModifyDate(Date value) {
		this.modifyDate = value;
	}
	
	public Date getModifyDate() {
		return this.modifyDate;
	}
		
	
		
	public void setAuditDate(Date value) {
		this.auditDate = value;
	}
	
	public Date getAuditDate() {
		return this.auditDate;
	}
		
		
	public void setAuditor(String value) {
		this.auditor = value;
	}
	
	public String getAuditor() {
		return this.auditor;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
		
	public void setFailMsg(String value) {
		this.failMsg = value;
	}
	
	public String getFailMsg() {
		return this.failMsg;
	}
		
		
	public void setModifyTimes(Integer value) {
		this.modifyTimes = value;
	}
	
	public Integer getModifyTimes() {
		return this.modifyTimes;
	}
		
}

