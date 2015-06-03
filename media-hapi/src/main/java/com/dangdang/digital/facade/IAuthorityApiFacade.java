package com.dangdang.digital.facade;

import java.util.Date;
import java.util.List;

import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 权限接口封装 All Rights Reserved.
 * 
 * @version 1.0 2014年12月2日 下午5:14:17 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IAuthorityApiFacade {

	/**
	 * 
	 * Description: 通过tokenInfo获取用户信息(这个只有custId，username，email)
	 * 
	 * @Version1.0 2014年12月3日 下午5:28:53 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param token
	 * @return
	 */
	public UserTradeBaseVo getUserInfoByToken(String token);
	/**
	 *
	 * Description: 通过tokenInfo获取用户详细信息
	 *
	 * @Version1.0 2015年4月20日 上午11:28:53 by 杨振平 创建
	 * @param token
	 * @return
	 */
	public UserTradeBaseVo getUserWholeInfoByToken(String token);
	/**
	 * 
	 * Description: 验证顾客是否是包月用户，如果是包月用户返回最后过期时间，如果不是返回null
	 * 
	 * @Version1.0 2014年12月11日 下午3:53:30 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param customerId
	 * @return
	 */
	public Date isMonthlycustomer(Long customerId);

	/**
	 * 
	 * Description: 验证用户章节权限(购买)
	 * 
	 * @Version1.0 2014年12月2日 下午5:17:07 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param customerId
	 * @param chapterId
	 * @return
	 */
	public boolean validateAuthority(Long customerId, Long mediaId, Long chapterId);

	/**
	 * 
	 * Description: 验证用户章节权限（购买）
	 * 
	 * @Version1.0 2014年12月2日 下午5:17:07 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param customerId
	 * @param chapterIdList
	 * @return
	 */
	public boolean validateAuthority(Long customerId, Long mediaId, List<Long> chapterIdList);

	/**
	 * 
	 * Description: 验证用户是否有全本权限（购买）
	 * 
	 * @Version1.0 2014年12月10日 下午4:40:21 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param customerId
	 * @param mediaId
	 * @return
	 */
	public boolean validateAuthorityByMedia(Long customerId, Long mediaId);

}
