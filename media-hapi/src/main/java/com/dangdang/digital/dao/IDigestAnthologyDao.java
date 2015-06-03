package com.dangdang.digital.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.DigestAnthology;

/**
 * Description: 文集与精品dao层定义
 * All Rights Reserved.
 * @version 1.0  2015年2月4日 下午5:56:03  by 代鹏（daipeng@dangdang.com）创建
 */
public interface IDigestAnthologyDao extends IBaseDao<DigestAnthology>{
	
    /**
     * Description: 批量保存文集与精品的关联关系
     * @Version1.0 2015年2月4日 下午6:07:07 by 代鹏（daipeng@dangdang.com）创建
     * @param record
     * @return
     */
    int batchInsert(List<DigestAnthology> record);
    
    /**
     * Description: 删除整个文集
     * @Version1.0 2015年2月4日 下午6:05:50 by 代鹏（daipeng@dangdang.com）创建
     * @param anthologyId
     * @return
     */
    int deleteByAnthologyId(Long anthologyId);
    
    /**
     * Description: 删除文集中某些精品
     * @Version1.0 2015年2月4日 下午6:06:15 by 代鹏（daipeng@dangdang.com）创建
     * @param digestId
     * @param anthologyId
     * @return
     */
    int deleteByDigestIdsAndAnthologyId(Long anthologyId, Collection<Long> digestIds);
    
    
    /**
     * Description: 根据文集id和多个精品id查询关联关系集
     * @Version1.0 2015年2月5日 上午11:16:06 by 代鹏（daipeng@dangdang.com）创建
     * @return
     */
    Map<Long, DigestAnthology> queryDigestAnthologyByAnthologyIdAndDigestIds(Long anthologyId, Collection<Long> digestIds);
    
    /**
     * Description: 文集详情页下精品文章列表
     * @Version1.0 2015年3月18日 上午11:12:33 by 代鹏（daipeng@dangdang.com）创建
     * @param paramMap
     * @return
     */
    List<DigestAnthology> queryDigestsByAnthologyId(Map<String, Object> paramMap);
    
}