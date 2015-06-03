/**
 * Description: IUserAuthorityDetailService.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:38:01  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.UserAuthorityDetail;

/**
 * Description: 用户阅读权限详情service
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:38:01  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserAuthorityDetailService extends IBaseService<UserAuthorityDetail, Long> {

	public int batchInsert(List<UserAuthorityDetail> userAuthorityDetails) throws Exception;
	
	public int getMasterCountByCustIdAndChapterIds(Long custId,String chapterIds,Long mediaId);
}
