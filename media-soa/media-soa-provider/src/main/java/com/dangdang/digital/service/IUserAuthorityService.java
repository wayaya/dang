/**
 * Description: IUserAuthorityService.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:29:26  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;
import com.dangdang.digital.vo.CreateOrderVo;

/**
 * Description: 用户阅读权限service
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:29:26  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserAuthorityService extends IBaseService<UserAuthority, Long> {

	/**
	 * 
	 * Description: 绑定用户阅读权限
	 * @Version1.0 2014年12月12日 下午3:56:01 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param orderMain
	 * @throws Exception
	 */
	public UserAuthority bindUserAuthority(OrderMain orderMain) throws Exception;
	
	/**
	 * 
	 * Description: 更新redis缓存中的用户阅读权限
	 * @Version1.0 2014年12月15日 上午11:31:45 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param result
	 * @throws Exception
	 */
	public void updateUserAuthorityForRedis(UserAuthority result) throws Exception;
	
	/**
	 * 
	 * Description: 验证用户阅读权限
	 * @Version1.0 2014年12月16日 上午11:54:11 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @param mediaId
	 * @param chapterIds 为空则查询全本权限
	 * @return
	 */
	public boolean findWithDetailByCustIdAndMediaId(Long custId, Long mediaId,List<Long> chapterIds);
	
	/**
	 * 
	 * Description: 绑定用户电子书阅读权限
	 * @Version1.0 2014年12月18日 下午3:02:48 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param vo
	 */
	public UserAuthority bindUserMediaAuthority(BindUserMediaAuthorityVo vo) throws Exception;
	
	/**
	 * 
	 * Description: 创建订单时的权限验证
	 * @Version1.0 2014年12月30日 上午10:11:37 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param createOrderVo
	 */
	public boolean checkUserAuthorityForCreateOrder(CreateOrderVo createOrderVo) throws Exception;
}
