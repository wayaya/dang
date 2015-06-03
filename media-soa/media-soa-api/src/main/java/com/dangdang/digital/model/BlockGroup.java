package com.dangdang.digital.model;

/**
 * Description: 块的组 主体 
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 上午11:16:28  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
public class BlockGroup {
    private Long mediaBlockGroupId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String descn;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 父节点id
     */
    private Long parentId;

    public Long getMediaBlockGroupId() {
        return mediaBlockGroupId;
    }

    public void setMediaBlockGroupId(Long mediaBlockGroupId) {
        this.mediaBlockGroupId = mediaBlockGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}