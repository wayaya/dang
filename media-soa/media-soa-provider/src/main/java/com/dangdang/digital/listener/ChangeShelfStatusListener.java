package com.dangdang.digital.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.model.ColumnContent;
import com.dangdang.digital.model.ListRanking;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.model.MediaStatistics;
import com.dangdang.digital.service.IActivitySaleService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IColumnContentService;
import com.dangdang.digital.service.IListRankingService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaStatisticsService;
import com.dangdang.digital.utils.SearchUtils;
import com.dangdang.digital.vo.ChangeShelfMessage;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;

/**
 * 
 * Description: 订单上下架处理消息监听 All Rights Reserved.
 * 
 * @version 1.0 2015年1月4日 上午11:29:32 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class ChangeShelfStatusListener {
	private static final Logger logger = Logger.getLogger(ChangeShelfStatusListener.class);

	@Resource
	private ICacheService cacheService;

	@Resource
	IMediaSaleService mediaSaleService;

	@Resource
	RabbitTemplate rabbitTemplate;

	@Resource
	IMediaStatisticsService mediaStatisticsService;

	@Resource
	IColumnContentService columnContentService;

	@Resource
	IListRankingService listRankingService;

	@Resource
	IActivitySaleService activitySaleService;

	public void handleMessage(ChangeShelfMessage changeShelfMessage) {
		try {
			List<Long> saleIds = changeShelfMessage.getSaleIds();
			Integer status = changeShelfMessage.getStatus();
			logger.info("接收上下架处理消息," + saleIds + ":" + status);
			// 1、重设缓存
			resetCache(saleIds);

			// 2.榜单或分类或栏目
			clearTheCacheOnMediaShelfStatusChangeHit(saleIds, status);

			// 3.添加删除索引
			resetIndex(saleIds, status);

			// 4、活动商品下架时置为无效
			this.resetActivitySale(saleIds, status);

		} catch (Exception e) {
			logger.error("接收上下架处理消息异常：", e);
			try {
				// 等待一分钟，重发消息
				Thread.sleep(1000 * 60);
				rabbitTemplate.convertAndSend("media.sale.change.shelf", changeShelfMessage);
				logger.info("重发上下架处理消息：" + changeShelfMessage.getSaleIds());
			} catch (InterruptedException e1) {
				logger.error("重发上下架处理消息失败：", e);
			}

		}
	}

	/**
	 * 
	 * Description: 下架时活动商品置为无效
	 * 
	 * @Version1.0 2015年1月28日 下午2:34:52 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIds
	 * @param status
	 */
	private void resetActivitySale(List<Long> saleIds, Integer status) {
		if (Constans.MEDIA_SHELF_STATUS_DOWN == status) {
			for (Long saleId : saleIds) {
				ActivitySale activitySale = new ActivitySale();
				activitySale.setSaleId(saleId);
				activitySale.setStatus(Constans.ACTIVITYINFO_STATUS_INVALID);
				activitySaleService.update(activitySale);
			}
			logger.info("下架后活动商品置为无效" + saleIds);
		}
	}

	/**
	 * 
	 * Description: 添加、删除索引
	 * 
	 * @Version1.0 2015年1月28日 下午2:33:49 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param saleIds
	 * @param status
	 */
	private void resetIndex(List<Long> saleIds, Integer status) {
		if (Constans.MEDIA_SHELF_STATUS_UP == status) {
			SearchUtils.addDocument(saleIds);
			logger.info("上架后添加索引成功" + saleIds);
		} else if (Constans.MEDIA_SHELF_STATUS_DOWN == status) {
			SearchUtils.deleteDoucument(saleIds);
			logger.info("下架后删除索引成功" + saleIds);
		}

	}

	/**
	 * 
	 * Description: 上下架修改时重设缓存
	 * 
	 * @Version1.0 2015年1月4日 上午11:34:59 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @throws Exception
	 */
	private void resetCache(List<Long> saleIds) throws Exception {
		// 先删除销售主体下的media缓存
		List<MediaSaleCacheVo> mediaSaleCacheList = cacheService.batchGetMediaSaleFromCache(saleIds);
		if (CollectionUtils.isNotEmpty(mediaSaleCacheList)) {
			List<Long> mediaIdList = new ArrayList<Long>();
			for (MediaSaleCacheVo mediaSale : mediaSaleCacheList) {
				if (CollectionUtils.isNotEmpty(mediaSale.getMediaList())) {
					for (MediaCacheWholeVo media : mediaSale.getMediaList()) {
						mediaIdList.add(media.getMediaId());
					}
				}
			}
			cacheService.batchDeleteMediaBasicCache(mediaIdList);
			cacheService.batchDeleteMediaWholeCache(mediaIdList);
		}

		// 删除销售主体缓存
		cacheService.batchDeleteMediaSaleCache(saleIds);
		// 查询主库
		List<MediaSale> mediaSaleList = mediaSaleService.findMasterByIds(saleIds);
		// 重设销售主体缓存（包含media）
		cacheService.batchSetMediaSaleCache(mediaSaleList);

		// 更新Category对应的热销列表，去除已下架的商品
		mediaStatisticsService.batchUpdateCategoryHotSellCache(saleIds);
		logger.info("上下架后重设缓存成功：" + saleIds);

	}

	/**
	 * 
	 * Description:清除上下架状态改变的media关联的缓存---榜单,分类 要求:从数据库加载数据到缓存时需要过滤掉下架的media
	 * 规则:1.上下架是否在栏目或分类销量topn中,是则清除相应的分类的缓存key 2.上下架是否在分类排序维度中,是则清除相应的分类维度的key
	 * 3.上下架是否在榜单中,是则清除相应的缓存key
	 * 
	 * @Version1.0 2015年1月7日 下午3:43:28 by 吕翔 (lvxiang@dangdang.com) 创建
	 * @param saleIds
	 */
	private void clearTheCacheOnMediaShelfStatusChangeHit(List<Long> saleIds, int status) {
		List<ColumnContent> ccList = columnContentService.getColumnContentBySaleIds(saleIds);
		if (null != ccList) {
			// 清除缓存
			for (ColumnContent cc : ccList) {
				// 是分类
				logger.info("清除分类或栏目内容缓存,有上下架变化：标识:" + cc.getColumnCode() + " status=" + status + " saleIds " + saleIds);
				cacheService.cleanCacheByKey(Constans.COLUMN_CONTENT_CACHE_KEY + cc.getColumnCode());
			}
		}
		List<ListRanking> rankingList = listRankingService.getListRankingBySaleIds(saleIds);
		if (null != rankingList) {
			for (ListRanking lr : rankingList) {
				logger.info("清除榜单内容缓存,有上下架变化：标识:" + lr.getListType() + " status=" + status + " saleIds " + saleIds);
				cacheService.cleanCacheByKey(Constans.RANKING_LIST_CACHE_KEY + lr.getListType());
			}
		}
		//清除分类的缓存
		List<MediaStatistics> mediaStatisticsList = mediaStatisticsService.getBySaleIds(saleIds);
		
		if(null != mediaStatisticsList){
			Set<String> categoryPathSet =  new HashSet<String>();
			for(MediaStatistics ms: mediaStatisticsList){
				String paths = ms.getMediaCategoryIds();
				if(null != paths){
					String[] ary = paths.split(",");
					for(String path:ary){
						categoryPathSet.add(path);
					}
				}
			}
		 for(String path:categoryPathSet){
			 path = path.substring(path.lastIndexOf("-")+1);
			 String keyPattern = Constans.MEDIA_CATEGORY_DATA_CACHE_KEY+path+"*" ;
			 logger.info("清除分类内容缓存,有上下架变化：keyPattern = " + keyPattern);
			 cacheService.cleanCacheByKeyPattern(keyPattern);
		 }
		}
	}
}
