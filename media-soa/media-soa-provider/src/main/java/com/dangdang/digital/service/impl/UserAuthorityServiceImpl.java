/**
 * Description: UserAuthorityServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年12月12日 下午3:30:42  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IUserAuthorityDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.MediaRelation;
import com.dangdang.digital.model.OrderDetailChapter;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.model.UserAuthorityDetail;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IMediaRelationService;
import com.dangdang.digital.service.IUserAuthorityDetailService;
import com.dangdang.digital.service.IUserAuthorityService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCacheBeanUtils;
import com.dangdang.digital.vo.BindUserMediaAuthorityVo;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.UserAuthorityCacheVo;

/**
 * Description: 用户阅读权限service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年12月12日 下午3:30:42 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class UserAuthorityServiceImpl extends
		BaseServiceImpl<UserAuthority, Long> implements IUserAuthorityService {

	@Resource
	private IUserAuthorityDetailService userAuthorityDetailService;
	@Resource
	private ICacheService cacheService;
	@Resource
	private IMediaRelationService mediaRelationService;
	@Resource
	private IUserAuthorityDao userAuthorityDao;

	@Override
	public IBaseDao<UserAuthority> getBaseDao() {
		return userAuthorityDao;
	}

	@Override
	/**
	 * 绑定用户阅读权限
	 */
	public UserAuthority bindUserAuthority(OrderMain orderMain)
			throws Exception {
		// 封装用户阅读权限实体
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setCustId(orderMain.getCustId());
		userAuthority.setMediaType(Short
				.valueOf(Constans.ORDER_TYPE_EBOOK + ""));
		// 保存用户阅读权限
		this.save(userAuthority);
		Long custId = orderMain.getCustId();
		Long userAuthorityId = userAuthority.getUserAuthorityId();
		if (orderMain.getWholeFlag() == Constans.ORDER_WHOLE_NOT) {
			// 章节购买
			userAuthority.setAuthorityType(Constans.USER_AUTHORITY_CHAPTER);
		} else if (orderMain.getWholeFlag() == Constans.ORDER_WHOLE_YES) {
			// 全本购买
			MediaSaleCacheVo vo = cacheService.getMediaSaleFromCache(orderMain
					.getOrderDetails().get(0).getSaleInfoId());
			if (vo == null || vo.getType() == null || vo.getMediaList() == null
					|| vo.getMediaList().isEmpty()) {
				LogUtil.error(logger,
						"订单号：{0}，创建全本购买订单绑定权限时查询不到销售实体相关信息，mediaId：{1}",
						orderMain.getOrderNo());
				throw new MediaException(
						ErrorCodeEnum.ERROR_CODE_12002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage());
			}
			if (vo.getType() == 0) {
				userAuthority.setMediaId(vo.getMediaList().get(0).getMediaId());
				userAuthority.setAuthorityType(Constans.USER_AUTHORITY_MEIDA);
			} else if (vo.getType() == 1) {
				userAuthority.setAuthorityType(Constans.USER_AUTHORITY_CHAPTER);
				List<MediaRelation> mediaRelations = mediaRelationService
						.findListByParams("saleId", orderMain.getOrderDetails()
								.get(0).getSaleInfoId());
				if (mediaRelations == null || mediaRelations.isEmpty()) {
					LogUtil.error(logger, "订单号：{0}，创建全本购买订单绑定权限时查询不到销售实体关联信息！",
							orderMain.getOrderNo());
					throw new MediaException(
							ErrorCodeEnum.ERROR_CODE_12002.getErrorCode() + "",
							ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage());
				}
				for (MediaRelation mediaRelation : mediaRelations) {
					UserAuthorityDetail detail = new UserAuthorityDetail();
					detail.setCustId(custId);
					detail.setMediaChapterId(mediaRelation.getChapterId());
					detail.setMediaId(mediaRelation.getMediaId());
					detail.setUserAuthorityId(userAuthorityId);
					userAuthority.getUserAuthorityDetails().add(detail);
				}
			} else {
				LogUtil.error(logger, "订单号：{0}，创建全本购买订单绑定权限时销售实体type={1}",
						orderMain.getOrderNo(), vo.getType());
				throw new MediaException(
						ErrorCodeEnum.ERROR_CODE_12002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage());
			}
		}
		if (userAuthority.getAuthorityType() != null
				&& userAuthority.getAuthorityType() == Constans.USER_AUTHORITY_CHAPTER) {
			// 章节购买订单需要封装用户阅读权限详情实体
			if (CollectionUtils
					.isEmpty(userAuthority.getUserAuthorityDetails())) {
				for (OrderDetailChapter chapter : orderMain.getOrderDetails()
						.get(0).getOrderDetailChapters()) {
					UserAuthorityDetail detail = new UserAuthorityDetail();
					detail.setCustId(custId);
					detail.setMediaChapterId(chapter.getChapterId());
					detail.setMediaId(chapter.getMediaId());
					detail.setUserAuthorityId(userAuthorityId);
					userAuthority.getUserAuthorityDetails().add(detail);
				}

			}
			userAuthorityDetailService.batchInsert(userAuthority
					.getUserAuthorityDetails());
		}
		userAuthority.setPayMainPrice(orderMain.getPayMainPrice());
		userAuthority.setPaySubPrice(orderMain.getPaySubPrice());
		this.update(userAuthority);
		return userAuthority;
	}

	@Override
	public void updateUserAuthorityForRedis(UserAuthority result)
			throws Exception {
		result.setLastModifiedDate(new Date());
		try {
			if (Constans.USER_AUTHORITY_CHAPTER == result.getAuthorityType()) {
				Map<Long, UserAuthority> userAuthorityMap = new HashMap<Long, UserAuthority>();
				for (UserAuthorityDetail detail : result
						.getUserAuthorityDetails()) {
					UserAuthority userAuthority = userAuthorityMap.get(detail
							.getMediaId());
					if (userAuthority == null) {
						userAuthority = new UserAuthority();
						userAuthority.setAuthorityType(result
								.getAuthorityType());
						userAuthority.setCustId(result.getCustId());
						userAuthority.setLastModifiedDate(result
								.getLastModifiedDate());
						userAuthority.setMediaId(detail.getMediaId());
						userAuthority.setMediaType(result.getMediaType());
						userAuthorityMap.put(userAuthority.getMediaId(),
								userAuthority);
					}
					userAuthority.getUserAuthorityDetails().add(detail);
				}
				for (Long key : userAuthorityMap.keySet()) {
					cacheService.setUserAuthorityCacheVo(userAuthorityMap
							.get(key));
				}
			} else {
				cacheService.setUserAuthorityCacheVo(result);
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "操作redis缓存更新用户阅读权限出错！");
		}
	}

	@Override
	public boolean findWithDetailByCustIdAndMediaId(Long custId, Long mediaId,
			List<Long> chapterIds){
		//验证是否下架
		try {
			MediaCacheBasicVo mediaCacheBasicVo = cacheService.getMediaBasicFromCache(mediaId);
			if (mediaCacheBasicVo != null
					&& (mediaCacheBasicVo.getShelfStatus() == null || mediaCacheBasicVo
							.getShelfStatus() == Constans.MEDIA_SHELF_STATUS_DOWN)) {
				return false;
			}
		} catch (Exception e1) {
			LogUtil.error(logger, e1, "操作redis缓存更新用户阅读权限出错！");
		}
		//验证是否免费章节
		if(!CollectionUtils.isEmpty(chapterIds)){
			List<ChapterCacheBasicVo> chapterCacheBasicVos = null;
			try {
				chapterCacheBasicVos = cacheService.batchGetChapterBasicFromCache(chapterIds);
			} catch (Exception e) {
				LogUtil.error(logger, e, "操作redis缓存更新用户阅读权限出错！");
			}
			if(!CollectionUtils.isEmpty(chapterCacheBasicVos)){
				boolean isAllFree = true;
				for(ChapterCacheBasicVo vo : chapterCacheBasicVos){
					if(StringUtils.isBlank(vo.getIsFree()) || vo.getIsFree().equals(Constans.IS_FREE_NO)){
						isAllFree = false;
						break;
					}
				}
				if(isAllFree){
					return true;
				}
			}
		}
		UserAuthorityCacheVo userAuthorityCacheVo = cacheService
				.getUserAuthorityCacheVoByCustIdAndMediaId(custId, mediaId);
		if (userAuthorityCacheVo == null) {
			return false;
		}
		if (checkMediaAuthority(userAuthorityCacheVo, chapterIds)) {
			return true;
		} else {
			UserAuthority userAuthority = userAuthorityDao
					.findWithDetailByCustIdAndMediaId(custId, mediaId);
			if (userAuthority != null) {
				cacheService.setUserAuthorityCacheVo(userAuthority);
				UserAuthorityCacheVo newUserAuthorityCacheVo = new UserAuthorityCacheVo();
				MediaCacheBeanUtils.copyUserAuthorityToCacheVo(userAuthority,
						null, newUserAuthorityCacheVo);
				return checkMediaAuthority(newUserAuthorityCacheVo,chapterIds);
			}
		}

		return false;
	}

	private boolean checkMediaAuthority(
			UserAuthorityCacheVo userAuthorityCacheVo, List<Long> chapterIds) {
		if (Constans.USER_AUTHORITY_MEIDA == userAuthorityCacheVo
				.getAuthorityType()) {
			return true;
		} else {
			if (CollectionUtils.isEmpty(chapterIds)
					|| CollectionUtils.isEmpty(userAuthorityCacheVo
							.getChapterIdsMap())) {
				return false;
			}
			if (userAuthorityCacheVo.getChapterIdsMap().keySet()
					.containsAll(chapterIds)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserAuthority bindUserMediaAuthority(BindUserMediaAuthorityVo bindUserMediaAuthorityVo) throws Exception{
		// 封装用户阅读权限实体
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setCustId(bindUserMediaAuthorityVo.getCustId());
		userAuthority.setMediaType(Short
				.valueOf(Constans.ORDER_TYPE_EBOOK + ""));
		Long custId = bindUserMediaAuthorityVo.getCustId();
		//验证是否下架
		try {
			MediaCacheBasicVo mediaCacheBasicVo = cacheService.getMediaBasicFromCache(bindUserMediaAuthorityVo.getMediaId());
			if (mediaCacheBasicVo != null
					&& (mediaCacheBasicVo.getShelfStatus() == null || mediaCacheBasicVo
							.getShelfStatus() == Constans.MEDIA_SHELF_STATUS_DOWN)) {
				throw new MediaException(
						ErrorCodeEnum.ERROR_CODE_10009.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage());
			}
		} catch (Exception e1) {
			LogUtil.error(logger, e1, "操作redis缓存更新用户阅读权限出错！");
		}
		if (bindUserMediaAuthorityVo.getUserAuthorityType() == Constans.USER_AUTHORITY_CHAPTER) {
			// 章节权限
			userAuthority.setAuthorityType(Constans.USER_AUTHORITY_CHAPTER);
		} else if (bindUserMediaAuthorityVo.getUserAuthorityType() == Constans.USER_AUTHORITY_MEIDA) {
			// 全本权限
			userAuthority.setMediaId(bindUserMediaAuthorityVo.getMediaId());
			userAuthority.setAuthorityType(Constans.USER_AUTHORITY_MEIDA);
			// 保存用户阅读权限
			this.save(userAuthority);
		}
		if (userAuthority.getAuthorityType() == Constans.USER_AUTHORITY_CHAPTER) {
			// 章节购买订单需要封装用户阅读权限详情实体
			if (CollectionUtils.isEmpty(bindUserMediaAuthorityVo
					.getChapterIds())
					&& (bindUserMediaAuthorityVo.getChapterNum() == null || bindUserMediaAuthorityVo
							.getChapterNum() < 1)) {
				throw new MediaException(
						ErrorCodeEnum.ERROR_CODE_10002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage());
			}
			List<ChapterCacheBasicVo> chapterCacheBasicVos = null;
			if (!CollectionUtils.isEmpty(bindUserMediaAuthorityVo
					.getChapterIds())) {
				chapterCacheBasicVos = cacheService
						.batchGetChapterBasicFromCache(bindUserMediaAuthorityVo
								.getChapterIds());
			} else {
				chapterCacheBasicVos = cacheService
						.batchGetChapterBasicFromCache(getChapterIdsOfNotBuy(
								bindUserMediaAuthorityVo.getCustId(),
								bindUserMediaAuthorityVo.getMediaId(),
								bindUserMediaAuthorityVo.getChapterNum()));
			}
			if(!CollectionUtils.isEmpty(chapterCacheBasicVos)){
				// 保存用户阅读权限
				this.save(userAuthority);
				for (ChapterCacheBasicVo chapter : chapterCacheBasicVos) {
					UserAuthorityDetail detail = new UserAuthorityDetail();
					detail.setCustId(custId);
					detail.setMediaChapterId(chapter.getId());
					detail.setMediaId(chapter.getMediaId());
					detail.setUserAuthorityId(userAuthority.getUserAuthorityId());
					userAuthority.getUserAuthorityDetails().add(detail);
				}
				userAuthorityDetailService.batchInsert(userAuthority
						.getUserAuthorityDetails());
			}else{
				return null;
			}
		}
		userAuthority.setPayMainPrice(bindUserMediaAuthorityVo.getPayMainPrice()!=null ? bindUserMediaAuthorityVo.getPayMainPrice() : 0);
		userAuthority.setPaySubPrice(bindUserMediaAuthorityVo.getPaySubPrice()!=null ? bindUserMediaAuthorityVo.getPaySubPrice() : 0);
		this.update(userAuthority);
		return userAuthority;
	}
	
	private List<Long> getChapterIdsOfNotBuy(Long custId,Long mediaId,Integer chapterNum) throws Exception{
		List<Long> chapterIds = new ArrayList<Long>();
		UserAuthorityCacheVo userAuthorityCacheVo = cacheService.getUserAuthorityCacheVoByCustIdAndMediaId(custId, mediaId);
		if(userAuthorityCacheVo != null && userAuthorityCacheVo.getAuthorityType() == Constans.USER_AUTHORITY_MEIDA){
			return chapterIds;
		}
		Integer startIndex = 0;
		if(userAuthorityCacheVo != null && !CollectionUtils.isEmpty(userAuthorityCacheVo.getChapterIdsMap())){
			Long maxChapterId = Collections.max(userAuthorityCacheVo.getChapterIdsMap().keySet());
			ChapterCacheBasicVo chapterVo = cacheService.getChapterBasicFromCache(maxChapterId);
			startIndex = (chapterVo != null && chapterVo.getIndex() != null && chapterVo.getIndex() > 0) ? (chapterVo.getIndex() + 1) : 0;
		}
		for(int index = 0;index < 6;index++){
			if(chapterIds.size() == chapterNum){
				break;
			}
			List<Long> tempChapterIds = cacheService.getChapterIdsFromCache(mediaId, startIndex, startIndex + 200);
			if(!CollectionUtils.isEmpty(tempChapterIds)){
				List<ChapterCacheBasicVo> tempChapterCacheBasicVos = cacheService.batchGetChapterBasicFromCache(tempChapterIds);
				for(ChapterCacheBasicVo vo : tempChapterCacheBasicVos){
					if(chapterIds.size() == chapterNum){
						break;
					}
					if((vo.getIsFree() == null || vo.getIsFree().equals(Constans.IS_FREE_NO))){
						chapterIds.add(vo.getId());
					}
				}
			}else{
				break;
			}
			startIndex += 201;
		}		
		return chapterIds;
	}

	@Override
	public boolean checkUserAuthorityForCreateOrder(CreateOrderVo createOrderVo) throws Exception{
		List<Long> needPayChapterIds = new ArrayList<Long>();
		UserAuthorityCacheVo userAuthorityCacheVo = cacheService
				.getUserAuthorityCacheVoByCustIdAndMediaId(createOrderVo
						.getCustId(), createOrderVo.getMediaIds().get(0));
		if(!CollectionUtils.isEmpty(createOrderVo.getChapterIds())){
			//过滤免费章节
			List<ChapterCacheBasicVo> chapterVos = cacheService.batchGetChapterBasicFromCache(createOrderVo.getChapterIds());
			if(CollectionUtils.isEmpty(chapterVos) || chapterVos.size() != createOrderVo.getChapterIds().size()){
				LogUtil.error(logger, "创建订单检查权限时查询不到电子书相关信息！");
				throw new MediaException(
						ErrorCodeEnum.ERROR_CODE_12002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage());
			}
			for(ChapterCacheBasicVo vo : chapterVos){
				if(StringUtils.isBlank(vo.getIsFree()) || vo.getIsFree().equals(Constans.IS_FREE_NO)){
					needPayChapterIds.add(vo.getId());
				}
			}
			//过滤已有权限章节
			if(userAuthorityCacheVo != null){
				if(userAuthorityCacheVo.getAuthorityType() == Constans.USER_AUTHORITY_MEIDA){
					needPayChapterIds.clear();
				}
				if (userAuthorityCacheVo.getAuthorityType() == Constans.USER_AUTHORITY_CHAPTER
						&& !CollectionUtils.isEmpty(userAuthorityCacheVo
								.getChapterIdsMap())) {
					List<Long> tempChapterIds = new ArrayList<Long>();
					for(Long chapterId : needPayChapterIds){
						if(userAuthorityCacheVo.getChapterIdsMap().containsKey(chapterId)){
							tempChapterIds.add(chapterId);
						}
					}
					if(!tempChapterIds.isEmpty()){
						needPayChapterIds.removeAll(tempChapterIds);
					}
				}
			}
			createOrderVo.setNeedPayChapterIds(needPayChapterIds);
			if(CollectionUtils.isEmpty(createOrderVo.getNeedPayChapterIds())){
				return true;
			}
		}else{
			if(userAuthorityCacheVo != null){
				if(userAuthorityCacheVo.getAuthorityType() == Constans.USER_AUTHORITY_MEIDA){
					return true;
				}
			}
		}
		return false;
	}
	
}
