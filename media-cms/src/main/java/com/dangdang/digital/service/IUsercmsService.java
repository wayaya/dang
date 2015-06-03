package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Usercms;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * 
* @Description: 用户管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:20:49
* @version V1.0
 */
public interface IUsercmsService extends IBaseService<Usercms,Long> {

	void saveOrUpdateUsercms(Usercms usercms, List<Long> roleIds, String creator);

	void deleteUsercmsByIds(List<Long> idsToRemove);

	PageFinder<Usercms> findMasterPageFinderObjs(Usercms usercms, Query query,
			List<Long> userIds);

}
