/**
 * Description: DigestServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午3:22:02  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDigestDao;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.DateUtil;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午3:22:02  by 代鹏（daipeng@dangdang.com）创建
 */
@Service("digestService")
public class DigestServiceImpl extends BaseServiceImpl<Digest, Long> implements IDigestService{
	
	@Resource
	private IDigestDao digestDao;
	
	@Override
	public IBaseDao<Digest> getBaseDao() {
		return digestDao;
	}
	
	@Override
	public int insert(Digest record) {
		return digestDao.insert(record);
	}

	@Override
	public int delete(Long id) {
		return digestDao.delete(id);
	}
	
	@Override
	public int updateDigest(Digest record) {
		return digestDao.update(record);
	}
	
	@Override
	public Digest findDigestById(Long id) {
		return digestDao.findDigestById(id);
	}

	@Override
	public List<Digest> queryDigestsByIds(Collection<Long> ids, Date lastDate, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		paramMap.put("now", lastDate);
		paramMap.put("pageSize", pageSize);
		return digestDao.queryDigestsByIds(paramMap);
	}

	@Override
	public List<Digest> queryDigestsByMood(Integer mood, Date lastDate, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mood", mood);
		paramMap.put("lastDate", lastDate);
		paramMap.put("pageSize", pageSize);
		paramMap.put("now", new Date());
		return digestDao.queryDigestsByMood(paramMap);
	}

	@Override
	public List<Digest> queryDigestListByDateSlotAndDayOrNight(Date startDate, Date endDate, Integer dayOrNight) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("dayOrNight", dayOrNight);
		return digestDao.queryDigestsForHomePage(paramMap);
	}

	@Override
	public List<Digest> queryDigestsListForDB(String currentPageDate, Integer dayOrNight) throws Exception {
		Date startDate = null;
		Date endDate = null;
		
		Date now = new Date();
		String nowPage = DateUtil.format(now, "yyyy-MM-dd");
		//当前的精品内容展示处理
		if(currentPageDate.equals(nowPage)){
			startDate = DateUtil.getMinTimeByStringDate(nowPage);
			endDate = now;
		}else{
			startDate = DateUtil.getMinTimeByStringDate(currentPageDate);
			endDate = DateUtil.getMaxTimeByStringDate(currentPageDate);
		}
		return this.queryDigestListByDateSlotAndDayOrNight(startDate, endDate, dayOrNight);
	}
	
	@Override
	public List<Digest> queryDigestHomePageList(String currentPageDate, Integer dayOrNight) throws Exception{
		if(StringUtils.isBlank(currentPageDate) || dayOrNight == null){
			return new ArrayList<Digest>();
		}
		return this.queryDigestsListForDB(currentPageDate, dayOrNight);
	}

	@Override
	public Digest getDigestByShowEndDateLimitOne(Date showEndDate, Integer dayOrNight) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("showEndDate", showEndDate);
		paramMap.put("dayOrNight", dayOrNight);
		return digestDao.getDigestByShowEndDateLimitOne(paramMap);
	}

	@Override
	public Digest getDigestContentById(Long id) {
		return digestDao.getDigestContentById(id);
	}

	@Override
	public List<Digest> queryDigestsByIdsAndSortByIds(Collection<Long> ids, Integer offSet, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		paramMap.put("offSet", offSet);
		paramMap.put("pageSize", pageSize);
		return digestDao.queryDigestsByIdsAndSortByIds(paramMap);
	}

	@Override
	public List<Digest> queryDigestsBySignId(Long signId, Date lasteDate, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("signId", signId);
		paramMap.put("lasteDate", lasteDate);
		paramMap.put("pageSize", pageSize);
		paramMap.put("now", new Date());
		return digestDao.queryDigestsBySignId(paramMap);
	}

	@Override
	public List<Digest> queryDigestsByAuthorId(Long authorId, Date lasteDate, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("authorId", authorId);
		paramMap.put("lasteDate", lasteDate);
		paramMap.put("pageSize", pageSize);
		paramMap.put("now", new Date());
		return digestDao.queryDigestsByAuthorId(paramMap);
	}

	@Override
	public List<Digest> queryDigestsByStoreUpAndCustId(Long custId, String storeType, String platForm, Integer offSet, Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("custId", custId);
		paramMap.put("storeType", storeType);
		paramMap.put("platForm", platForm);
		paramMap.put("offSet", offSet);
		paramMap.put("pageSize", pageSize);
		return digestDao.queryDigestsByStoreUpAndCustId(paramMap);
	}

	@Override
	public List<Digest> queryDigestsHomePageNew(Long sortPage, Integer dayOrNight, Integer pageSize, Integer type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cmShowDate", new Date());
		paramMap.put("sortPage", sortPage);
		paramMap.put("dayOrNight", dayOrNight);
		paramMap.put("pageSize", pageSize);
		paramMap.put("type", type);
		return digestDao.queryDigestsHomePageNew(paramMap);
	}

	@Override
	public List<Digest> queryDigestsHomePageOld(Long sortPage, Integer dayOrNight, Integer pageSize, Integer type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cmShowDate", new Date());
		paramMap.put("sortPage", sortPage);
		paramMap.put("dayOrNight", dayOrNight);
		paramMap.put("pageSize", pageSize);
		paramMap.put("type", type);
		return digestDao.queryDigestsHomePageOld(paramMap);
	}

	@Override
	public Long getDigestIdtWithOne(Integer dayOrNight, Date start, Date end) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dayOrNight", dayOrNight);
		paramMap.put("showStart", start);
		paramMap.put("showEnd", end);
		return digestDao.getDigestIdtWithOne(paramMap);
	}
	
}
