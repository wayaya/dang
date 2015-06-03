package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Column;

public interface IColumnService extends IBaseService<Column, Integer> {
	List<Column> getAll();

}
