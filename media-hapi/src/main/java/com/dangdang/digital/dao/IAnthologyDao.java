package com.dangdang.digital.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Anthology;

/**
 * Description: 精品文集dao层接口定义
 * All Rights Reserved.
 * @version 1.0  2015年2月4日 下午4:51:26  by 代鹏（daipeng@dangdang.com）创建
 */
public interface IAnthologyDao extends IBaseDao<Anthology>{
	
	/**
	 * Description: 保存文集
	 * @Version1.0 2015年2月4日 下午4:50:22 by 代鹏（daipeng@dangdang.com）创建
	 * @param record
	 * @return
	 */
	int insert(Anthology record);
	
	/**
	 * Description: 更新文集
	 * @Version1.0 2015年2月4日 下午4:50:47 by 代鹏（daipeng@dangdang.com）创建
	 * @param record
	 * @return
	 */
	int update(Anthology record);
	
	/**
	 * Description: 删除文集
	 * @Version1.0 2015年1月21日 上午10:15:24 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	int delete(Long id);
	
	/**
	 * Description: 根据文集主键id查询文集
	 * @Version1.0 2015年2月4日 下午4:53:11 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	Anthology findAnthologyById(Long id);
	
	/**
	 * Description: 根据用户id和文集名称查询文集
	 * @Version1.0 2015年2月4日 下午4:54:58 by 代鹏（daipeng@dangdang.com）创建
	 * @return
	 */
	Anthology findAnthologyByCustIdAndAnthologyName(Map<String, Object> paramObj);
	
	/**
	 * Description: 获取个人的文集列表
	 * @Version1.0 2015年2月4日 下午4:57:03 by 代鹏（daipeng@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	List<Anthology> queryAnthologyListByCustId(Long custId, String platform, Date lastDate, Integer pageSize);
	
}