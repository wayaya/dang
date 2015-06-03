package com.dangdang.digital.model;

import java.io.Serializable;

public class BookListDetail implements Serializable{

	private static final long serialVersionUID = 6339044413487189512L;

	private Long booklistDetailId;

    private Long booklistId;

    private Byte type;

    private Long saleId;

    private Long mediaId;

    private Long productId;

    private Long index;

    public Long getBooklistDetailId() {
        return booklistDetailId;
    }

    public void setBooklistDetailId(Long booklistDetailId) {
        this.booklistDetailId = booklistDetailId;
    }

    public Long getBooklistId() {
        return booklistId;
    }

    public void setBooklistId(Long booklistId) {
        this.booklistId = booklistId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}