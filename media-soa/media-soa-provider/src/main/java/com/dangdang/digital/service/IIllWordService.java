package com.dangdang.digital.service;
import java.util.List;

import com.dangdang.digital.model.IllWord;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IIllWordService extends IBaseService<IllWord, Integer> {
	
	public List<IllWord> getAll();

}
