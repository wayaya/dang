package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Functionality;

/**
 * 
* @Description: (功能管理)
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:12:09
* @version V1.0
 */
public interface IFunctionalityService extends IBaseService<Functionality,Long>{

	void deleteFunctionById(Long functionalityId);

}
