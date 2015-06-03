package com.dangdang.digital.model;

public class CardTemplate {
    private Long id;

    private String title;

    private String remark;

    private Integer type;

    private String pic1Path;

    private String pic2Path;

    private String pic3Path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPic1Path() {
        return pic1Path;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path == null ? null : pic1Path.trim();
    }

    public String getPic2Path() {
        return pic2Path;
    }

    public void setPic2Path(String pic2Path) {
        this.pic2Path = pic2Path == null ? null : pic2Path.trim();
    }

    public String getPic3Path() {
        return pic3Path;
    }

    public void setPic3Path(String pic3Path) {
        this.pic3Path = pic3Path == null ? null : pic3Path.trim();
    }
}