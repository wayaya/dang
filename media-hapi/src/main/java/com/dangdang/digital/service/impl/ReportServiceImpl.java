package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IReportDao;
import com.dangdang.digital.model.Report;
import com.dangdang.digital.service.IReportService;

/**
 * 
 * Description: 举报service All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午8:51:58 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Service
public class ReportServiceImpl extends BaseServiceImpl<Report, Long> implements IReportService {

	@Resource
	IReportDao dao;

	public IBaseDao<Report> getBaseDao() {
		return dao;
	}

}
