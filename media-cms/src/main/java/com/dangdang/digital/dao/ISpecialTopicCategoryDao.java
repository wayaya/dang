package com.dangdang.digital.dao;
import java.util.List;

import com.dangdang.digital.model.SpecialTopicCategory;

public interface ISpecialTopicCategoryDao extends IBaseDao<SpecialTopicCategory> {
	public List<SpecialTopicCategory> getAll();
}