package com.dangdang.digital.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Media;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.MediaSimpleSearchReturnVo;

public interface IMediaDao extends IBaseDao<Media> {

	public void saveCatetorys(Media media);
	
	public List<MediaSimpleSearchReturnVo> findSimpleSearchReturnByIds(List<Integer> ids);
	
	public void setIsFull(Media media);
	
	public void toShelf(Map para);
	
	public PageFinder<Long> queryEpubProductList(Query query);
	
	public PageFinder<Media> queryEpubMedia(Object params, Query query);
	
	/**
	 * 从media-jo复制过来的
	 * Description: 根据saleIds统计原创书的信息
	 * @Version1.0 2015年3月3日 下午4:42:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIds
	 * @return
	 */
	public List<Map<String, Object>> statisticsBySaleIds(Date startTime,Date endTime,List<Long> saleIds);

	/**
	 * 
	 * Description: 根据mediaIds统计原创书的信息
	 * @Version1.0 2015年3月3日 下午4:45:28 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIds
	 * @return
	 */
	public List<Map<String, Object>> statisticsByMediaIds(Date startTime,Date endTime,List<Long> mediaIds);
}
