package com.dangdang.digital.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ITestApi;
import com.dangdang.digital.model.Test;
import com.dangdang.digital.service.ITestService;

/**
 * test
 *
 */
@Component("testApi")
public class TestApiImpl implements ITestApi {
	
	@Resource ITestService testService;
	
	public Test test() {
		Test test = testService.getTestById(1);
		return test;
	}
}
