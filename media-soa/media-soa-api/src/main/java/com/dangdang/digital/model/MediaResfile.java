package com.dangdang.digital.model;

import java.util.Date;

public class MediaResfile {
    private Long id;

    private Long mediaId;

    private Long resfileId;

    private String resfileType;

    private String resfilePath;

    private Integer resfileSize;

    private Long deviceTypeId;

    private String deviceTypeCode;

    private String deviceTypeName;

    private Date created;

    private Integer resfileWidth;

    private Integer resfileHeight;

    private Integer resfilePageNum;

    private Integer resfilePicNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getResfileId() {
        return resfileId;
    }

    public void setResfileId(Long resfileId) {
        this.resfileId = resfileId;
    }

    public String getResfileType() {
        return resfileType;
    }

    public void setResfileType(String resfileType) {
        this.resfileType = resfileType == null ? null : resfileType.trim();
    }

    public String getResfilePath() {
        return resfilePath;
    }

    public void setResfilePath(String resfilePath) {
        this.resfilePath = resfilePath == null ? null : resfilePath.trim();
    }

    public Integer getResfileSize() {
        return resfileSize;
    }

    public void setResfileSize(Integer resfileSize) {
        this.resfileSize = resfileSize;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode == null ? null : deviceTypeCode.trim();
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getResfileWidth() {
        return resfileWidth;
    }

    public void setResfileWidth(Integer resfileWidth) {
        this.resfileWidth = resfileWidth;
    }

    public Integer getResfileHeight() {
        return resfileHeight;
    }

    public void setResfileHeight(Integer resfileHeight) {
        this.resfileHeight = resfileHeight;
    }

    public Integer getResfilePageNum() {
        return resfilePageNum;
    }

    public void setResfilePageNum(Integer resfilePageNum) {
        this.resfilePageNum = resfilePageNum;
    }

    public Integer getResfilePicNum() {
        return resfilePicNum;
    }

    public void setResfilePicNum(Integer resfilePicNum) {
        this.resfilePicNum = resfilePicNum;
    }
}