package com.dangdang.digital.model;

public class DeviceType {
    private Integer deviceTypeId;

    private String code;

    private String name;

    private String desc;

    private String defaultType;

    private String compatibleServerVersion;

    private String binaryValue;

    private Integer sort;

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType == null ? null : defaultType.trim();
    }

    public String getCompatibleServerVersion() {
        return compatibleServerVersion;
    }

    public void setCompatibleServerVersion(String compatibleServerVersion) {
        this.compatibleServerVersion = compatibleServerVersion == null ? null : compatibleServerVersion.trim();
    }

    public String getBinaryValue() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue == null ? null : binaryValue.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}