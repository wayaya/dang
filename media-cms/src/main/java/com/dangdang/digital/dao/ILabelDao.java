package com.dangdang.digital.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.dangdang.digital.model.Label;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 标签
 * @author Simple
 *
 * @date 2014年11月7日 上午11:18:43
 *
 */
public interface ILabelDao extends IBaseDao<Label>{
	/**
	 * 根据标签id获取相应的标签实体
	 * @param id
	 * @return
	 */
	Label getLabelById(Integer id);
	
	/**
	 * 查询得到所有标签的列表
	 * @return
	 */
	List<Label> getLabelList();
	
	/**
	 * 根据分页参数获取相应的Label列表
	 * @param pagerReqest
	 * @return
	 */
	List<Label> getLabelsByPageRequest(PageRequest pagerReqest);
	
	/**
	 * 根据分页参数查询并返回结果列表
	 * @param paramMap	分页参数等
	 * @param query
	 * @return
	 */
	PageFinder<Label> getLabelList(Label label, Query query);
}
