package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.DDClick;

public interface IDDClickService {

	public void insert(DDClick click);

	public void insertBatch(List<DDClick> ddClicks);

	public void insertBatch(List<DDClick> ddClicks,
			String collectionName);

	public void insert(DDClick click, String collectionName);
	
}