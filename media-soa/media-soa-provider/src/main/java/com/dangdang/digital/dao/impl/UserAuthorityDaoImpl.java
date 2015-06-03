/**
 * Description: UserAuthorityDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:26:33  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.dao.IUserAuthorityDao;
import com.dangdang.digital.dao.IUserAuthorityDetailDao;
import com.dangdang.digital.model.UserAuthority;

/**
 * Description: 用户阅读权限dao实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月12日 下午3:26:33 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Repository
public class UserAuthorityDaoImpl extends BaseDaoImpl<UserAuthority> implements IUserAuthorityDao {
	@Resource
	private IUserAuthorityDetailDao userAuthorityDetailDao;

	@Override
	public UserAuthority findWithDetailByCustIdAndMediaId(Long custId, Long mediaId) {
		UserAuthority result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("custId", custId);
		parameter.put("mediaId", mediaId);
		//查询全本购买权限
		List<UserAuthority> mediaUserAuthoritys = this.selectMasterList("UserAuthorityMapper.getAll", parameter);
		if (!CollectionUtils.isEmpty(mediaUserAuthoritys)) {
			for(UserAuthority userAuthority : mediaUserAuthoritys){
				if (result == null) {
					result = userAuthority;
				} else {
					if(userAuthority.getLastModifiedDate().after(result.getLastModifiedDate())){
						result.setLastModifiedDate(userAuthority.getLastModifiedDate());
					}
					result.getUserAuthorityDetails().addAll(userAuthority.getUserAuthorityDetails());
					result.setPayMainPrice(userAuthority.getPayMainPrice() + result.getPayMainPrice());
					result.setPaySubPrice(userAuthority.getPaySubPrice() + result.getPaySubPrice());
				}
			}
		}
		//查询章节购买权限
		List<UserAuthority> chapterUserAuthoritys = this.selectMasterList("UserAuthorityMapper.getWithDetail", parameter);
		if (!CollectionUtils.isEmpty(chapterUserAuthoritys)) {
			for (UserAuthority userAuthority : chapterUserAuthoritys) {				
				if (result == null) {
					result = userAuthority;
				} else {
					if(userAuthority.getLastModifiedDate().after(result.getLastModifiedDate())){
						result.setLastModifiedDate(userAuthority.getLastModifiedDate());
					}
					result.getUserAuthorityDetails().addAll(userAuthority.getUserAuthorityDetails());
					result.setPayMainPrice(userAuthority.getPayMainPrice() + result.getPayMainPrice());
					result.setPaySubPrice(userAuthority.getPaySubPrice() + result.getPaySubPrice());
				}
			}
		}		
		return result;
	}

	@Override
	public List<UserAuthority> findWithDetailByCustId(Long custId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("custId", custId);
		List<UserAuthority> userAuthoritys = this.selectList("UserAuthorityMapper.getAll", parameter);
		Map<Long, UserAuthority> resultMap = new HashMap<Long, UserAuthority>();
		if (!CollectionUtils.isEmpty(userAuthoritys)) {
			for (UserAuthority userAuthority : userAuthoritys) {
				if (Constans.USER_AUTHORITY_CHAPTER == userAuthority.getAuthorityType()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userAuthorityId", userAuthority.getUserAuthorityId());
					map.put("custId", custId);
					map.put("mediaId", userAuthority.getMediaId());
					userAuthority.setUserAuthorityDetails(userAuthorityDetailDao.selectList(
							"UserAuthorityDetailMapper.getAll", map));
				}
				if (!resultMap.containsKey(userAuthority.getMediaId())) {
					resultMap.put(userAuthority.getMediaId(), userAuthority);
				} else {
					resultMap.get(userAuthority.getMediaId()).getUserAuthorityDetails()
							.addAll(userAuthority.getUserAuthorityDetails());
					resultMap.get(userAuthority.getMediaId()).setPayMainPrice(
							userAuthority.getPayMainPrice()
									+ resultMap.get(userAuthority.getMediaId()).getPayMainPrice());
					resultMap.get(userAuthority.getMediaId()).setPaySubPrice(
							userAuthority.getPaySubPrice() + resultMap.get(userAuthority.getMediaId()).getPaySubPrice());
				}
				if (Constans.USER_AUTHORITY_MEIDA == userAuthority.getAuthorityType()) {
					resultMap.get(userAuthority.getMediaId()).setAuthorityType(userAuthority.getAuthorityType());
				}
			}
		}
		return new ArrayList<UserAuthority>(resultMap.values());
	}

}
