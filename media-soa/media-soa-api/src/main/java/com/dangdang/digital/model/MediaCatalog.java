package com.dangdang.digital.model;

public class MediaCatalog {
    private Long mcId;

    private Long productId;

    private String catalogName;

    private String catalogContent;

    public Long getMcId() {
        return mcId;
    }

    public void setMcId(Long mcId) {
        this.mcId = mcId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName == null ? null : catalogName.trim();
    }

    public String getCatalogContent() {
        return catalogContent;
    }

    public void setCatalogContent(String catalogContent) {
        this.catalogContent = catalogContent == null ? null : catalogContent.trim();
    }
}