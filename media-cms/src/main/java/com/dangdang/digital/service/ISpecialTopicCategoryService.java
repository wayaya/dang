package com.dangdang.digital.service;

import java.util.List;
import com.dangdang.digital.model.SpecialTopicCategory;
import com.dangdang.digital.service.IBaseService;


/**
 * SpecialTopicCategory Manager.
 */
public interface ISpecialTopicCategoryService extends IBaseService<SpecialTopicCategory, Long> {
	
	List<SpecialTopicCategory> getAll();
}
