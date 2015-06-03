package com.dangdang.digital.model;

public class CpContract {

    private Integer contractId;

    private String contractCode;

    private String cpName;

    private Integer cpId;

    private Integer type;

    private String startDate;

    private String endDate;

    private Float contentRatio;

    private Float manageRatio;

    private Float otherRadtio;

    private Integer payCycle;

    private Integer freeRatio;

    private Integer invoiceType;

    private Boolean isExclusive;

    private String attachFile;

    private String creator;

    private String creationDate;

    private String auditor;

    private String auditDate;

    private Integer status;

    private String comments;

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName == null ? null : cpName.trim();
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Float getContentRatio() {
        return contentRatio;
    }

    public void setContentRatio(Float contentRatio) {
        this.contentRatio = contentRatio;
    }

    public Float getManageRatio() {
        return manageRatio;
    }

    public void setManageRatio(Float manageRatio) {
        this.manageRatio = manageRatio;
    }

    public Float getOtherRadtio() {
        return otherRadtio;
    }

    public void setOtherRadtio(Float otherRadtio) {
        this.otherRadtio = otherRadtio;
    }

    public Integer getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(Integer payCycle) {
        this.payCycle = payCycle;
    }

    public Integer getFreeRatio() {
        return freeRatio;
    }

    public void setFreeRatio(Integer freeRatio) {
        this.freeRatio = freeRatio;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Boolean getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(Boolean isExclusive) {
        this.isExclusive = isExclusive;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile == null ? null : attachFile.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

}