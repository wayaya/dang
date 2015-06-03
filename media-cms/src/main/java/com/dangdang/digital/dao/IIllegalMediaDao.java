package com.dangdang.digital.dao;
import com.dangdang.digital.model.IllegalMedia;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 
 * Description: 敏感作品
 * All Rights Reserved.
 * @version 1.0  2014年11月22日 下午2:56:07  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IIllegalMediaDao extends IBaseDao<IllegalMedia>{
	
	IllegalMedia getIllegalMediaById(Integer id);
	
	
	/**
	 * 根据分页参数查询并返回结果列表
	 * @param paramMap	分页参数等
	 * @param query
	 * @return
	 */
	PageFinder<IllegalMedia> getIllegalMediaList(IllegalMedia illegalMedia, Query query);
}
