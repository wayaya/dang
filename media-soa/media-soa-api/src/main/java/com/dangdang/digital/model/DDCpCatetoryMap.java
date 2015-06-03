package com.dangdang.digital.model;

public class DDCpCatetoryMap {
    private Long id;

    private Integer catetoryId;

    private String catetoryCode;

    private String cpCatetoryId;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCatetoryId() {
        return catetoryId;
    }

    public void setCatetoryId(Integer catetoryId) {
        this.catetoryId = catetoryId;
    }

    public String getCatetoryCode() {
        return catetoryCode;
    }

    public void setCatetoryCode(String catetoryCode) {
        this.catetoryCode = catetoryCode == null ? null : catetoryCode.trim();
    }

    public String getCpCatetoryId() {
        return cpCatetoryId;
    }

    public void setCpCatetoryId(String cpCatetoryId) {
        this.cpCatetoryId = cpCatetoryId == null ? null : cpCatetoryId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}