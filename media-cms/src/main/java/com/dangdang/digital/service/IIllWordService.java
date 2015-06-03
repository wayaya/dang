package com.dangdang.digital.service;
import com.dangdang.digital.model.IllWord;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IIllWordService extends IBaseService<IllWord, Integer> {
	/**
	 * 
	 * Description: 根据查询参数反馈实体Bean列表
	 * @Version1.0 2014年11月18日 上午10:17:54 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param label	查询参数
	 * @param query 分页参数
	 * @return
	 */
	public PageFinder<IllWord> getIllWordList(IllWord illword, Query query);

}
