package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.IllWord;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 标签
 * @author Simple
 *
 * @date 2014年11月7日 上午11:18:43
 *
 */
public interface IIllWordDao extends IBaseDao<IllWord>{
	/**
	 * 根据标签id获取相应的敏感词实体
	 * @param id
	 * @return
	 */
	IllWord getIllWordById(Integer id);
	
	/**
	 * 查询得到所有敏感词的列表
	 * @return
	 */
	List<IllWord> getIllWordList();
	
	
	/**
	 * 根据分页参数查询并返回结果列表
	 * @param paramMap	分页参数等
	 * @param query
	 * @return
	 */
	PageFinder<IllWord> getIllWordList(IllWord illWord, Query query);
}
