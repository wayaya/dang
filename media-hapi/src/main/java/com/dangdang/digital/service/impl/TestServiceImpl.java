package com.dangdang.digital.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.ITestDao;
import com.dangdang.digital.model.Test;
import com.dangdang.digital.service.ITestService;

@Service
public class TestServiceImpl implements ITestService{
	@Resource ITestDao testDao;
    public Test getTestById(Integer id){
    	return testDao.getTestById(id);
    }
}