/**
 * Description: IUserAuthorityApi.java
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午3:18:58  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api;

import java.util.List;

import com.dangdang.digital.model.UserMonthly;
import com.dangdang.digital.vo.BindUserMediaAuthorityResultVo;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;
import com.dangdang.digital.vo.UserAuthorityResultVo;

/**
 * Description: 用户阅读权限接口
 * All Rights Reserved.
 * @version 1.0  2014年12月8日 下午3:18:58  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IUserAuthorityApi {

	/**
	 * 
	 * Description: 查询阅读权限
	 * @Version1.0 2014年12月8日 下午3:22:18 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @param mediaId
	 * @param chapterIds 为空时代表查询全本权限
	 * @return
	 */
	public UserAuthorityResultVo queryAuthority(Long custId,Long mediaId,List<Long> chapterIds);
	
	/**
	 * 
	 * Description: 绑定用户权限
	 * @Version1.0 2014年12月18日 下午2:30:35 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @param mediaId
	 * @param chapterIds 
	 * @return
	 */
	public BindUserMediaAuthorityResultVo bindUserAuthority(BindUserMediaAuthorityVo vo) throws Exception;
	
	/**
	 * 首次登陆送三天全场包月
	 */
	public boolean bindUserMonthlyForFirstLogin(Long custId) throws Exception;
	
	/**
	 * 首次分享送三天全场包月
	 */
	public BindUserMediaAuthorityResultVo bindUserMonthlyForFirstShare(Long custId,String nikeName) throws Exception;

	/**
	 * 
	 * Description: 查询包月信息
	 * @Version1.0 2014年12月30日 下午8:14:54 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param mediaId
	 * @param chapterIds
	 * @return
	 */
	public UserMonthly findUserMonthlyNowByCustId(Long custId, Long mediaId, List<Long> chapterIds);
}
