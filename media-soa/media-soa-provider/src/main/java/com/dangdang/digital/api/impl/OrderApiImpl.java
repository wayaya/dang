/**
 * Description: OrderApiImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月29日 下午5:08:01  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dangdang.digital.api.IOrderApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivitySaleCacheVo;
import com.dangdang.digital.model.Bought;
import com.dangdang.digital.model.BoughtChapter;
import com.dangdang.digital.model.BoughtDetail;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.profile.LoggerProfile;
import com.dangdang.digital.service.IBoughtChapterService;
import com.dangdang.digital.service.IBoughtDetailService;
import com.dangdang.digital.service.IBoughtService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.service.IUserAuthorityService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateBoughtVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ShoppingViewForChapterVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 订单api All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 下午5:08:01 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Component("orderApi")
public class OrderApiImpl implements IOrderApi {

	@Resource
	private ICacheService cacheService;
	@Resource
	private IOrderMainService orderMainService;
	@Resource
	private IUserAuthorityService userAuthorityService;
	@Resource
	private IUserService userService;
	@Resource
	private IBoughtService boughtService;
	@Resource
	private IBoughtDetailService boughtDetailService;
	@Resource
	private IBoughtChapterService boughtChapterService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private final String DEVICE_TYPE_IOS = "ipad,iphone,ipadiap,iphoneiap";

	@Override
	@LoggerProfile(methodNote = "创建订单接口", recordTime = true)
	public CreateOrderVo createAndSaveOrder(CreateOrderVo createOrderVo) throws Exception {
		try {
			//权限验证
			if(userAuthorityService.checkUserAuthorityForCreateOrder(createOrderVo)){
				return createOrderVo;
			}
			//生成订单
			UserAuthority result = orderMainService.createAndSaveOrderOperation(createOrderVo);
			// 更新阅读权限缓存
			if(result != null){
				cacheService.setUserAuthorityCacheVo(result);
			}	
		} catch (MediaException me) {
			createOrderVo.setErrorCode(me.getErrorCode());
			createOrderVo.setErrorMsg(me.getErrorMsg());
			throw me;
		} catch (Exception e) {
			LogUtil.error(logger, e, "");
			createOrderVo.setErrorCode(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode() + "");
			createOrderVo.setErrorMsg(ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage());
			throw e;
		}
		return createOrderVo;
	}

	@Override
	public ShoppingViewForChapterVo findShoppingViewForChapter(Long custId, Long mediaChapterId, String deviceType,String userName)
			throws Exception {
		// 校验参数
		if (custId == null || mediaChapterId == null || StringUtils.isBlank(deviceType)) {
			throw new MediaException("参数不完整！");
		}
		ShoppingViewForChapterVo shoppingViewForChapterVo = new ShoppingViewForChapterVo();
		shoppingViewForChapterVo.setMediaChapterId(mediaChapterId);
		shoppingViewForChapterVo.setCustId(custId);
		// 获取用户月
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setCustId(custId);
		vo.setUsername(userName);
		vo.setTradeType(Constans.TRADE_TYPE_QUERY);
		UserTradeBaseVo resultVo = null;
		try {
			resultVo = userService.tradeForUser(vo);
			if (resultVo == null || !resultVo.isTradeResult()) {
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_18005.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_18005.getErrorMessage());
			}
			shoppingViewForChapterVo.setMainBalance(resultVo.getMainBalance());
			shoppingViewForChapterVo.setSubBalance(resultVo.getSubBalance());
			shoppingViewForChapterVo.setMainBalanceIOS(resultVo.getMainBalanceIOS());
			shoppingViewForChapterVo.setSubBalanceIOS(resultVo.getSubBalanceIOS());
		} catch (Exception e) {
			LogUtil.error(logger, e, "");
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18005.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18005.getErrorMessage());
		}
		// 获取章节信息
		ChapterCacheBasicVo chapterCacheBasicVo = cacheService.getChapterBasicFromCache(mediaChapterId);
		if (chapterCacheBasicVo != null) {
			shoppingViewForChapterVo.setMediaId(chapterCacheBasicVo.getMediaId());
			if (Constans.IS_FREE_YES.equals(chapterCacheBasicVo.getIsFree())) {
				// 免费章节
				shoppingViewForChapterVo.setMediaChapterPrice(0);
			} else {
				// 收费章节
				shoppingViewForChapterVo
						.setMediaChapterPrice(DEVICE_TYPE_IOS.indexOf(deviceType.toLowerCase()) != -1 ? chapterCacheBasicVo
								.getIosPrice() : chapterCacheBasicVo.getPrice());
			}
			shoppingViewForChapterVo.setMediaChapterTitle(chapterCacheBasicVo.getTitle());
			shoppingViewForChapterVo.setWordCnt(chapterCacheBasicVo.getWordCnt());
			//获取销售实体信息
			MediaCacheBasicVo mediaCacheBasicVo = cacheService.getMediaBasicFromCache(chapterCacheBasicVo.getMediaId());
			if(mediaCacheBasicVo != null){
				shoppingViewForChapterVo.setMediaWordCnt(mediaCacheBasicVo.getWordCnt());
				shoppingViewForChapterVo.setSaleId(mediaCacheBasicVo.getSaleId());
				//检查是否参与限免活动
				if(checkTimeFree(mediaCacheBasicVo.getSaleId())){
					shoppingViewForChapterVo.setMediaChapterPrice(0);
				}
				MediaSaleCacheVo mediaSaleCacheVo = cacheService.getMediaSaleFromCache(mediaCacheBasicVo.getSaleId());
				if(mediaSaleCacheVo != null){
					shoppingViewForChapterVo.setSaleName(mediaSaleCacheVo.getName());
					shoppingViewForChapterVo.setSalePrice(mediaSaleCacheVo.getPrice());
					shoppingViewForChapterVo.setIsSupportFullBuy(mediaSaleCacheVo.getIsSupportFullBuy());
				}else{
					throw new MediaException("从缓存未获取到sale信息！saleId：" + mediaCacheBasicVo.getSaleId());
				}
			}else{
				throw new MediaException("从缓存未获取到media信息！mediaId：" + chapterCacheBasicVo.getMediaId());
			}
		} else {
			throw new MediaException("从缓存未获取到章节信息！mediaChapterId：" + mediaChapterId);
		}
		return shoppingViewForChapterVo;
	}

	@Override
	public List<Bought> getBoughtListByCustId(Long custId, Integer start, Integer count,String fromPaltform) {
		return boughtService.getBoughtListByCustId(custId, start, count,fromPaltform);

	}
	
	@Override
	public List<Long> getMyBoughtWholeMediaIds(Long custId, Date updateTime,String fromPaltForm) {
		if (custId == null) {
			return null;
		}
		return boughtService.getMyBoughtWholeMediaIds(custId, updateTime,fromPaltForm);

	}
	
	@Override
	public List<BoughtDetail> getBoughtDetailListByBoughtId(Long boughtId, Integer start, Integer count) {
		return boughtDetailService.getBoughtDetailListByBoughtId(boughtId, start, count);
	}

	@Override
	public List<BoughtChapter> getBoughtChapterListByBoughtDetailId(Long boughtDetailId, Integer start, Integer count) {
		return boughtChapterService.getBoughtChapterListByBoughtDetailId(boughtDetailId, start, count);
	}

	@Override
	public Integer getBoughtCountByCustId(Long custId,String fromPaltform) {
		return boughtService.getBoughtCountByCustId(custId,fromPaltform);
	}

	@Override
	public Integer getBoughtDetailCountByBoughtId(Long boughtId) {
		return boughtDetailService.getBoughtDetailCountByBoughtId(boughtId);
	}

	@Override
	public Integer getBoughtChapterCountByBoughtDetailId(Long boughtDetailId) {
		return boughtChapterService.getBoughtChapterCountByBoughtDetailId(boughtDetailId);
	}
	
	private boolean checkTimeFree(Long mediaSaleId){
		List<ActivitySaleCacheVo> activitySaleCacheVos = cacheService.getActivitySaleCacheWithActivityBySaleId(mediaSaleId);
		if(!CollectionUtils.isEmpty(activitySaleCacheVos)){
			long nowTime = new Date().getTime();
			for(ActivitySaleCacheVo vo : activitySaleCacheVos){
				//筛选限免活动
				if (vo.getActivityCacheVo() == null
						|| vo.getActivityCacheVo().getActivityTypeId() == null
						|| vo.getActivityCacheVo().getActivityTypeId() != ActivityTypeEnum.TIME_FREE
								.getKey()) {
					continue;
				}
				// 检查活动时间是否有效
				if (vo.getStartTime() != null
						&& vo.getStartTime().getTime() > nowTime) {
					continue;
				}
				if (vo.getEndTime() != null
						&& vo.getEndTime().getTime() < nowTime) {
					continue;
				}
				if (vo.getActivityCacheVo().getStartTime() != null
						&& vo.getActivityCacheVo().getStartTime().getTime() > nowTime) {
					continue;
				}
				if (vo.getActivityCacheVo().getEndTime() != null
						&& vo.getActivityCacheVo().getEndTime().getTime() < nowTime) {
					continue;
				}
				// 检查活动状态是否有效
				if (vo.getStatus() == null
						|| vo.getStatus() != Constans.ACTIVITYINFO_STATUS_VALID) {
					continue;
				}
				if (vo.getActivityCacheVo().getStatus() == null
						|| vo.getActivityCacheVo().getStatus() != Constans.ACTIVITYINFO_STATUS_VALID) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void createAndSaveBought(CreateBoughtVo createBoughtVo) throws Exception {
		Map<String,String> resultMap = boughtService.createAndSaveBought(createBoughtVo);
		if(!CollectionUtils.isEmpty(resultMap) && resultMap.containsKey("errorMsg")){
			throw new Exception(resultMap.get("errorMsg") + "，参数：" + JSON.toJSONString(createBoughtVo));
		}
	}

}
