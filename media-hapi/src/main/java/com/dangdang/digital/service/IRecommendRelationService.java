package com.dangdang.digital.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dangdang.digital.model.RecommendRelation;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.MediaCacheBasicVo;

public interface IRecommendRelationService extends IBaseService<RecommendRelation, Long>{

	List<Long> getMediaList(RecommendRelation queryObj);

	Set<Long> changeRecommendRelation(Map<String, Double> relationScoreMap, Date endDate);

	List<MediaCacheBasicVo> getRelatedMediaListAndToCache(RecommendRelation recommendRelation, Query query) throws Exception;

}
