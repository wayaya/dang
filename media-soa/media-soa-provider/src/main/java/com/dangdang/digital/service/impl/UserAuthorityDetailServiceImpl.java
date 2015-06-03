/**
 * Description: UserAuthorityDetailServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:39:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserAuthorityDetailDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.UserAuthorityDetail;
import com.dangdang.digital.service.IUserAuthorityDetailService;

/**
 * Description: 用户阅读权限详情service实现类
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:39:00  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class UserAuthorityDetailServiceImpl extends BaseServiceImpl<UserAuthorityDetail, Long>
		implements IUserAuthorityDetailService {

	@Resource
	private IUserAuthorityDetailDao userAuthorityDetailDao;
	
	@Override
	public IBaseDao<UserAuthorityDetail> getBaseDao() {
		return userAuthorityDetailDao;
	}

	@Override
	public int batchInsert(List<UserAuthorityDetail> userAuthorityDetails)
			throws Exception {
		if(CollectionUtils.isEmpty(userAuthorityDetails)){
			return 0;
		}
		Long custId = userAuthorityDetails.get(0).getCustId();
		Long mediaId = userAuthorityDetails.get(0).getMediaId();
		StringBuffer chapterIds = new StringBuffer("");
		for(UserAuthorityDetail detail : userAuthorityDetails){
			chapterIds.append(detail.getMediaChapterId()).append(",");
		}
		int result = this.getMasterCountByCustIdAndChapterIds(custId, chapterIds.substring(0, chapterIds.length() - 1), mediaId);
		if(result > 0){
			throw new MediaException("绑定用户章节权限时异常：已拥有章节权限！custId："+custId+"，mediaId："+mediaId+"，chapterIds："+chapterIds.toString());
		}
		return userAuthorityDetailDao.insert("UserAuthorityDetailMapper.batchInsert", userAuthorityDetails);
	}

	@Override
	public int getMasterCountByCustIdAndChapterIds(Long custId,
			String chapterIds,Long mediaId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custId", custId);
		param.put("mediaChapterIds", chapterIds);
		param.put("mediaId", mediaId);
		return (Integer)userAuthorityDetailDao.getSqlSessionTemplate().selectOne("UserAuthorityDetailMapper.getMasterCountByCustIdAndChapterIds", param);
	}

}
