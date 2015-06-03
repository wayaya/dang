package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Catetory;

/**
 * Description: EbookDao
 * All Rights Reserved.
 * @version 1.0  2014年6月10日 上午10:52:28  by 杜亚锋（duyafeng@dangdang.com）创建
 */
public interface ICatetoryService extends IBaseService<Catetory, Integer>{
	
	
	
	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<Catetory> getTreeByParentId(Catetory catetory);
	
	/**
	 * 
	 * Description: 通过栏目标识获取栏目下所有子(孙子)分类
	 * @Version1.0 2014年12月18日 下午3:28:00 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param parentCode
	 * @return
	 */
	public List<Catetory> getCatetoryByParentCode(String parentCode);
	
	/**添加分类树节点
	 * @param catetory
	 * @return
	 */
	public void insertOrUpdate(Catetory catetory);
	
	/**
	 * 删除树节点
	 * @param catetory
	 * @return
	 */
	public void delete(Integer id);
	
	public List<Catetory> getCatetoryByMediaId(Long mediaId);
	
	public void saveOrder(List<Object> map);
	
	/**
	 * 查询分类下边有没有书
	 * @param catetory
	 * @return
	 */
	public Integer getMediaCountByCatetoryId(Catetory catetory) ;
	
	public void delCatetoryByMediaId(Long mediaId);

}