package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.UsercmsFunctionality;
/**
 * 
* @Description: 用户功能对应关系管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:20:15
* @version V1.0
 */
public interface IUsercmsFunctionalityService extends IBaseService<UsercmsFunctionality,Long>{

	int insertList(List<UsercmsFunctionality> list);

}
