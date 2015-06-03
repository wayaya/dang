/**
 * Description: IUserMonthlyService.java
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:23:57  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2014年12月6日 上午11:23:57  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserMonthlyService extends IBaseService<UserMonthly,Long>{

	/**
	 * 
	 * Description: 获取用户包月结束时间
	 * @Version1.0 2014年12月16日 上午11:57:33 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @param mediaId
	 * @param chapterIds 为空则查询全本权限
	 * @return
	 */
	public UserMonthly findUserMonthlyNowByCustId(Long custId, Long mediaId,List<Long> chapterIds);
	
	/**
	 * 
	 * Description: 获取用户当前的包月信息
	 * @Version1.0 2014年12月17日 下午2:12:44 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<UserMonthly> selectUserMonthly(Long custId);
	
	/**
	 * 绑定用户包月权限
	 * Description: 
	 * @Version1.0 2014年12月18日 下午3:01:32 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param vo
	 */
	public UserMonthly bindUserMonthlyAuthority(BindUserMediaAuthorityVo vo) throws Exception;
	
	public int insertUserMonthly(Map<String, Object> map);
	
	public int updateUserMonthly(Map<String, Object> map);
	
	public UserMonthly findMasterUserMonthly(Short monthlyType,Long custId,String monthlyPaymentRelation);
}
