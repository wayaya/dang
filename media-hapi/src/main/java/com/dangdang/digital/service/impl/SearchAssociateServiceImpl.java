package com.dangdang.digital.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.ISearchAssociateDao;
import com.dangdang.digital.model.SearchAssociate;
import com.dangdang.digital.service.ISearchAssociateService;
import com.dangdang.digital.utils.PinYinUtils;

@Service
public class SearchAssociateServiceImpl extends BaseServiceImpl<SearchAssociate, Integer> implements
		ISearchAssociateService {
	@Resource
	private ISearchAssociateDao searchAssociateDao;

	@Override
	public IBaseDao<SearchAssociate> getBaseDao() {
		return searchAssociateDao;
	}

	@Override
	public List<SearchAssociate> associateSearch(String keyword, String searchSource) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyword", keyword);
		map.put("pinyin", PinYinUtils.getStringPinYin(keyword));
		map.put("searchSource", searchSource);
		List<SearchAssociate> associateList = searchAssociateDao.selectList("SearchAssociateMapper.getSearchAssociateLike", map);
		if(CollectionUtils.isNotEmpty(associateList)){
			return associateList;
		}
		return searchAssociateDao.selectList("SearchAssociateMapper.getSearchAssociateLikePinyin", map);
	}

	@Override
	public void saveSearchUpshot(String keyword, Integer count, String searchSource) {
		if (StringUtils.isNotBlank(keyword) && count > 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyword", keyword);
			map.put("searchSource", searchSource);
			List<SearchAssociate> searchAssociateList = searchAssociateDao.selectMasterList(
					"SearchAssociateMapper.getSearchAssociateEquals", map);
			if (searchAssociateList != null && searchAssociateList.size() > 0) {
				for (SearchAssociate searchAssociate : searchAssociateList) {
					searchAssociate.setCount(count);
					searchAssociate.setLastModifyDate(new Date());
					searchAssociate.setPinyin(PinYinUtils.getStringPinYin(keyword));
					searchAssociateDao.update(searchAssociate);
				}
			} else {
				SearchAssociate searchAssociate = new SearchAssociate();
				searchAssociate.setKeyword(keyword);
				searchAssociate.setsearchSource(searchSource);
				searchAssociate.setCount(count);
				searchAssociate.setCreationDate(new Date());
				searchAssociate.setLastModifyDate(new Date());
				searchAssociate.setPinyin(PinYinUtils.getStringPinYin(keyword));
				searchAssociateDao.insert(searchAssociate);
			}
		}
	}
}
