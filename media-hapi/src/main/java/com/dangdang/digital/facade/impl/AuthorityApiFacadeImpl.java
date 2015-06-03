package com.dangdang.digital.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IUserApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.vo.UserAuthorityResultVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

@Service("authorityApiFacade")
public class AuthorityApiFacadeImpl implements IAuthorityApiFacade {

	@Resource(name = "cacheApi")
	ICacheApi cacheApi;

	@Resource(name = "userAuthorityApi")
	IUserAuthorityApi userAuthorityApi;

	@Resource(name = "userApi")
	IUserApi userApi;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityApiFacadeImpl.class);

	@Override
	public UserTradeBaseVo getUserInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return cacheApi.getUserInfoByToken(token);
	}

	@Override
	public UserTradeBaseVo getUserWholeInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return cacheApi.getUserWholeInfoByToken(token);
	}

	@Override
	public Date isMonthlycustomer(Long customerId) {
		UserAuthorityResultVo result = userAuthorityApi.queryAuthority(customerId, null, null);
		if (result != null && result.getMonthlyEndTime() != null) {
			return result.getMonthlyEndTime();
		}
		return null;
	}

	@Override
	public boolean validateAuthority(Long customerId, Long mediaId, Long chapterId) {
		List<Long> chapterIds = new ArrayList<Long>();
		chapterIds.add(chapterId);
		UserAuthorityResultVo result = userAuthorityApi.queryAuthority(customerId, mediaId, chapterIds);
		if (result != null && result.isHasMediaAuthority()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateAuthority(Long customerId, Long mediaId, List<Long> chapterIdList) {
		UserAuthorityResultVo result = userAuthorityApi.queryAuthority(customerId, mediaId, chapterIdList);
		if (result != null && result.isHasMediaAuthority()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateAuthorityByMedia(Long customerId, Long mediaId) {
		UserAuthorityResultVo result = userAuthorityApi.queryAuthority(customerId, mediaId, null);
		if (result != null && result.isHasMediaAuthority()) {
			return true;
		}
		return false;
	}
}
