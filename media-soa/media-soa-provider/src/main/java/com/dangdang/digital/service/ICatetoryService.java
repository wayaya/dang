package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Catetory;

/**
 * Description: EbookDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
public interface ICatetoryService extends IBaseService<Catetory, Integer>{
	
	public List<Catetory> getCatetoryByMediaId(Long mediaId);
	
	

}