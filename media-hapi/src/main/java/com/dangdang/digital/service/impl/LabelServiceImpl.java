package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ILabelDao;
import com.dangdang.digital.model.Label;
import com.dangdang.digital.service.ILabelService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

@Service
public class LabelServiceImpl extends BaseServiceImpl<Label, Integer>implements ILabelService{
	@Resource ILabelDao labelDao;
	@Override
	public List<Label> getLabelList() {
		// TODO Auto-generated method stub
		return labelDao.getLabelList();
	}

	@Override
	public Label getLabelById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageFinder<Label> getLabelList(Label label, Query query) {
		return labelDao.getLabelList(label, query);

	}

	public IBaseDao<Label> getBaseDao() {
		// TODO Auto-generated method stub
		return labelDao;
	}

	
}