package com.dangdang.digital.dao;

import java.util.List;

import com.dangdang.digital.model.DigestLable;

/**
 * Description: 章节与标签映射Dao
 * All Rights Reserved.
 * @version 1.0  2015年1月20日 下午4:16:12  by 代鹏（daipeng@dangdang.com）创建
 */
public interface IDigestLableDao extends IBaseDao<DigestLable>{
	
	/**
	 * Description: 添加章节与标签映射关系
	 * @Version1.0 2015年1月21日 上午10:37:54 by 代鹏（daipeng@dangdang.com）创建
	 * @param record
	 * @return
	 */
	int insert(DigestLable record);
	
	/**
	 * Description: 删除映射关系
	 * @Version1.0 2015年1月21日 上午10:42:38 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	int delete(Long id);
	
	/**
	 * Description: 根据标签Ids查询关联的章节Ids
	 * @Version1.0 2015年1月21日 上午10:45:59 by 代鹏（daipeng@dangdang.com）创建
	 * @param signId
	 * @return
	 */
	List<Long> queryDigestIdsBySignId(Long signId);
}