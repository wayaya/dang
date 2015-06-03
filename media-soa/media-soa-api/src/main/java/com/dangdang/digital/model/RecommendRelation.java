package com.dangdang.digital.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月3日 上午10:25:28  by 魏嵩（weisong@dangdang.com）创建
 */
public class RecommendRelation implements Serializable{

	private static final long serialVersionUID = 1559155298463667650L;

	private Long recommendRelationId;

    private Long mediaId;

    private Long relatedMediaId;

    /**
     * 1. 买了又买  2看了又看
     */
    private Byte relationType;

    private Double score;

    private Date creationDate;

    public Long getRecommendRelationId() {
        return recommendRelationId;
    }

    public void setRecommendRelationId(Long recommendRelationId) {
        this.recommendRelationId = recommendRelationId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getRelatedMediaId() {
        return relatedMediaId;
    }

    public void setRelatedMediaId(Long relatedMediaId) {
        this.relatedMediaId = relatedMediaId;
    }

    public Byte getRelationType() {
        return relationType;
    }

    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}