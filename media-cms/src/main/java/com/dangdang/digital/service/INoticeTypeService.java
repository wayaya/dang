package com.dangdang.digital.service;
import java.util.List;

import com.dangdang.digital.model.NoticeType;
import com.dangdang.digital.service.IBaseService;


/**
 * NoticeType Manager.
 */
public interface INoticeTypeService extends IBaseService<NoticeType, Integer> {
	 
	List<NoticeType>  getAll();
}
