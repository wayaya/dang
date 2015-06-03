package com.dangdang.digital.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Anthology;

public interface IAnthologyService extends IBaseService<Anthology, Long>{
	
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
	Anthology findAnthologyByCustIdAndAnthologyName(Long custId, String anthologyName);
	
	/**
	 * Description: 获取个人文集列表
	 * @Version1.0 2015年2月6日 下午2:23:53 by 代鹏（daipeng@dangdang.com）创建
	 * @param custId
	 * @param platform
	 * @param lastDate
	 * @param pageSize
	 * @return
	 */
	List<Anthology> queryAnthologyListByCustId(Long custId, String platform, Date lastDate, Integer pageSize);
	
	/**
	 * Description: 新建文集
	 * @Version1.0 2015年2月5日 下午4:22:27 by 代鹏（daipeng@dangdang.com）创建
	 * @param anthology
	 * @param digestIds
	 * @return Map<String, Integer> key:errorMsg; value:errorCode
	 */
	Map<String, Integer> addAnthology(Anthology anthology, List<Long> digestIds, boolean newAnthology);
	
	/**
	 * Description: 删除整个文集
	 * @Version1.0 2015年2月9日 上午11:24:47 by 代鹏（daipeng@dangdang.com）创建
	 * @param anthologyId
	 * @return
	 */
	boolean deleteAnthology(Long anthologyId);
	
	/**
	 * Description: 删除某个文集下几篇精品文章
	 * @Version1.0 2015年2月9日 上午11:26:05 by 代鹏（daipeng@dangdang.com）创建
	 * @param anthologyId
	 * @param digestIds
	 * @return
	 */
	boolean deleteDigestsForAnthology(Long anthologyId, Collection<Long> digestIds);
	
	/**
	 * Description: 校验文集名称
	 * @Version1.0 2015年3月11日 上午10:29:43 by 代鹏（daipeng@dangdang.com）创建
	 * @param custId
	 * @param anthologyName
	 * @return
	 */
	Map<String, Integer> checkAnthologyName(Long custId, String anthologyName);
	
}
