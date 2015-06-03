package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.RecommendRelation;
import com.dangdang.digital.utils.Query;

public interface IRecommendRelationDao extends IBaseDao<RecommendRelation>{

	List<Long> getMediaList(RecommendRelation queryObj);

	List<Long> getRelatedMediaList(RecommendRelation recommendRelation,
			Query query);

}
