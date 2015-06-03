/**
 * Description: IDigestLableService.java
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午3:20:24  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.DigestLable;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午3:20:24  by 代鹏（daipeng@dangdang.com）创建
 */
public interface IDigestLableService extends IBaseService<DigestLable, Long> {
	
	int insert(DigestLable record);
	
	int delete(Long id);
	
	List<Long> queryDigestIdsBySignId(Long signId);
	
}
