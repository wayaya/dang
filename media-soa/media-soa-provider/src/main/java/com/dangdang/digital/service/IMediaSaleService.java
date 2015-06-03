package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.MediaSale;

public interface IMediaSaleService extends IBaseService<MediaSale, Long> {
	
	/**
	 * 
	 * Description: 查询包含关联数据的销售实体（for单章购买）
	 * @Version1.0 2014年12月2日 下午2:27:08 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param mediaId
	 * @param chapterId
	 * @return
	 * @throws Exception
	 */
	public MediaSale getMediaSaleWithRelation(Long mediaId,List<Long> chapterIds) throws Exception;
	
}
