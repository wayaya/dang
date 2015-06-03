package com.dangdang.digital.service;

import com.dangdang.digital.model.PushConditionParamsDefinition;

public interface IPushConditionParamsDefinitionService extends IBaseService<PushConditionParamsDefinition, Long>{
	
	public PushConditionParamsDefinition getByIdAndToCache(Long id);
}
