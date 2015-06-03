package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Label;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface ILabelService extends IBaseService<Label, Integer> {

	/**
	 * 根据标签id获取相应的标签实体
	 * @param id
	 * @return
	 */
	Label getLabelById(final int id);
	
	/**
	 * 查询得到所有标签的列表
	 * @return
	 */
	List<Label> getLabelList();
	
	public PageFinder<Label> getLabelList(Label label, Query query);

}
