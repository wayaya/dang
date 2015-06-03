package com.dangdang.digital.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ISuggestionDao;
import com.dangdang.digital.model.Suggestion;
import com.dangdang.digital.service.ISuggestionService;


/**
 * Suggestion Manager.
 */
@Service
public class SuggestionServiceImpl extends BaseServiceImpl<Suggestion, Long> implements ISuggestionService {

	@Resource
	ISuggestionDao dao;
	
	public IBaseDao<Suggestion> getBaseDao() {
		return dao;
	}
	
}
