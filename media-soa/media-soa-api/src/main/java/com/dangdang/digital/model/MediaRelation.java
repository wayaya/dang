package com.dangdang.digital.model;

public class MediaRelation {
    private Long mediaRelationId;

    private Long saleId;

    private Long chapterId;

    private Long mediaId;

    public Long getMediaRelationId() {
        return mediaRelationId;
    }

    public void setMediaRelationId(Long mediaRelationId) {
        this.mediaRelationId = mediaRelationId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }
}