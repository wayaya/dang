/**
 * Description: OrderMainServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:59:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.OrderFromPlatformEnum;
import com.dangdang.digital.constant.RechargeActivityCodeEnum;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IOrderMainDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.model.OrderDetail;
import com.dangdang.digital.model.OrderDetailChapter;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.model.UserAuthority;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IActivitySaleService;
import com.dangdang.digital.service.ICacheService;
import com.dangdang.digital.service.IMediaRelationService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.service.IOrderDetailChapterService;
import com.dangdang.digital.service.IOrderDetailService;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.service.IUserAuthorityService;
import com.dangdang.digital.service.IUserService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.MediaCacheBasicVo;
import com.dangdang.digital.vo.MediaCacheWholeVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.OrderQueryVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 主订单实体service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:59:51 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class OrderMainServiceImpl extends BaseServiceImpl<OrderMain, Long> implements IOrderMainService {
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate<String, Integer> masterRedisTemplate;
	@Resource
	private IOrderDetailService orderDetailService;
	@Resource
	private IOrderDetailChapterService orderDetailChapterService;
	@Resource
	private IMediaSaleService mediaSaleService;
	@Resource
	private IMediaRelationService mediaRelationService;
	@Resource
	private IMediaService mediaService;
	@Resource
	private IActivityInfoService activityInfoService;
	@Resource
	private IActivitySaleService activitySaleService;
	@Resource
	private ICacheService cacheService;
	@Resource
	private IUserService userService;
	@Resource
	private IUserAuthorityService userAuthorityService;
	@Resource
	private IOrderMainDao orderMainDao;
	@Resource
	RabbitTemplate rabbitTemplate;
	private DecimalFormat decimalFormat = new DecimalFormat("000000");
	private final String CREATE_ORDER_NO_SEQ = "create_order_no_seq";
	private final String DEVICE_TYPE_IOS = "ipad,iphone,ipadiap,iphoneiap";

	@Override
	public IBaseDao<OrderMain> getBaseDao() {
		return this.orderMainDao;
	}

	@Override
	public PageFinder<OrderMain> findPageFinderByOrderQueryVo(OrderQueryVo vo, Query query) {
		Map<String, Object> map = convertBeanToMap(vo);
		if (!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName") && !map.containsKey("mediaName")
				&& !map.containsKey("chapterNo")) {
			return this.findPageFinderObjs(map, query);
		}
		return orderMainDao.getPageFinderObjs(map, query, "OrderMainMapper.pageCountByOrderQuery",
				"OrderMainMapper.pageDataByOrderQuery");
	}

	@Override
	public List<OrderMain> findListByOrderQueryVo(OrderQueryVo vo) {
		Map<String, Object> map = convertBeanToMap(vo);
		if (!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName") && !map.containsKey("mediaName")
				&& !map.containsKey("chapterNo")) {
			return this.findListByParamsObjs(map);
		}
		return orderMainDao.selectList("OrderMainMapper.getAllByOrderQuery", map);
	}

	@Override
	public OrderMain findUniqueByOrderQueryVo(OrderQueryVo vo) {
		Map<String, Object> map = convertBeanToMap(vo);
		if (!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName") && !map.containsKey("mediaName")
				&& !map.containsKey("chapterNo")) {
			return this.findUniqueByParamsObjs(map);
		}
		return orderMainDao.selectOne("OrderMainMapper.getAllByOrderQuery", map);
	}

	@Override
	public String createOrderNo(String platform, Long custId) {
		String seqStr = null;
		String custIdSuffix = null;
		if (custId > 9) {
			custIdSuffix = custId.toString().substring(custId.toString().length() - 2);
		} else {
			custIdSuffix = "0" + custId;
		}
		try {
			long seq = masterRedisTemplate.opsForValue().increment(CREATE_ORDER_NO_SEQ, new Random().nextInt(10) + 1);
			if (seq < 100000) {
				seqStr = decimalFormat.format(seq);
			} else {
				seqStr = seq + "";
				seqStr = seqStr.substring(seqStr.length() - 6);
			}
		} catch (Exception e) {
			LogUtil.error(logger, e, "生成订单号时连接redis失败（key:create_order_no_seq）");
			seqStr = System.currentTimeMillis() + "";
			seqStr = seqStr.substring(seqStr.length() - 6) + "_1";
		}
		OrderFromPlatformEnum platformEnum = OrderFromPlatformEnum.getValueByKey(platform);
		return (platformEnum != null ? platformEnum.getPrefix() : "O") + DateUtil.getDate(new Date(), "yyMMddHHmm")
				+ custIdSuffix + seqStr;
	}

	@Override
	public OrderMain encapsulateOrderInfo(CreateOrderVo createOrderVo) throws Exception {
		// 设置回滚
		createOrderVo.setRollbackPoint(Constans.CREATE_ORDER_ROLLBACK_EOI);
		// 生成订单号
		String orderNo = this.createOrderNo(createOrderVo.getPlatform(), createOrderVo.getCustId());
		// 封装主订单基础信息
		OrderMain orderMain = new OrderMain();
		orderMain.setOrderNo(orderNo);
		orderMain.setCustId(createOrderVo.getCustId());
		orderMain.setFromPaltform(createOrderVo.getFromPaltform());
		orderMain.setDeviceVersion(createOrderVo.getDeviceVersion());
		orderMain.setChanelCode(createOrderVo.getChanelCode());
		orderMain.setOrderType(Constans.ORDER_TYPE_EBOOK);
		orderMain.setOrderStatus(Constans.ORDER_STATUS_PAID);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderNo(orderNo);
		// 章节购买订单
		if (createOrderVo.getSaleId() == null && createOrderVo.getMediaIds().size() == 1
				&& createOrderVo.getNeedPayChapterIds().size() > 0) {
			orderMain.setWholeFlag(Constans.ORDER_WHOLE_NOT);
			// 查询电子书信息
			MediaCacheBasicVo mediaCacheBasicVo = cacheService.getMediaBasicFromCache(createOrderVo.getMediaIds()
					.get(0));
			// 检查电子书是否已下架
			if (mediaCacheBasicVo == null || mediaCacheBasicVo.getShelfStatus() == null
					|| mediaCacheBasicVo.getShelfStatus() != Constans.MEDIA_SHELF_STATUS_UP) {
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
			}
			// 查询章节信息
			List<ChapterCacheBasicVo> chapterCacheBasicVos = cacheService.batchGetChapterBasicFromCache(createOrderVo
					.getNeedPayChapterIds());
			// 查询销售实体信息
			MediaSaleCacheVo mediaSaleCacheVo = cacheService.getMediaSaleFromCache(mediaCacheBasicVo.getSaleId());
			if (mediaSaleCacheVo != null && mediaCacheBasicVo != null && !CollectionUtils.isEmpty(chapterCacheBasicVos)) {
				// 封装订单详情和订单章节详情实体数据
				orderDetail.setSaleInfoId(mediaSaleCacheVo.getSaleId());
				orderDetail.setSaleInfoName(mediaSaleCacheVo.getName());
				int detailTotalPrice = 0;
				for (ChapterCacheBasicVo chapter : chapterCacheBasicVos) {
					OrderDetailChapter detailChapter = new OrderDetailChapter();
					detailChapter.setOrderNo(orderNo);
					detailChapter.setChapterNo(chapter.getIndex());
					detailChapter.setChapterId(chapter.getId());
					if (Constans.IS_FREE_YES.equals(chapter.getIsFree())) {
						// 免费章节
						detailChapter.setChapterPrice(0);
					} else {
						// 收费章节
						detailChapter.setChapterPrice(DEVICE_TYPE_IOS.indexOf(createOrderVo.getDeviceType()
								.toLowerCase()) != -1 ? chapter.getIosPrice() : chapter.getPrice());
					}
					detailChapter.setMediaId(mediaCacheBasicVo.getMediaId());
					detailChapter.setMediaName(mediaCacheBasicVo.getTitle());
					detailTotalPrice += detailChapter.getChapterPrice().intValue();
					orderDetail.getOrderDetailChapters().add(detailChapter);
				}
				orderDetail.setTotalPrice(detailTotalPrice);
				orderMain.setTotalPrice(detailTotalPrice);
			} else {
				LogUtil.error(logger, "订单号：{0}，创建订单时查询不到电子书相关信息，mediaId：{1}", orderNo, mediaCacheBasicVo.getMediaId());
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage());
			}
			orderMain.getOrderDetails().add(orderDetail);
			// 金额分摊
			apportionedAmount(orderMain, createOrderVo);
		} else if (createOrderVo.getSaleId() != null && createOrderVo.getNeedPayChapterIds().isEmpty()) {
			// 非章节购买
			orderMain.setWholeFlag(Constans.ORDER_WHOLE_YES);
			// 查询销售实体信息
			MediaSaleCacheVo mediaSaleCacheVo = cacheService.getMediaSaleFromCache(createOrderVo.getSaleId());
			if (mediaSaleCacheVo != null && !CollectionUtils.isEmpty(mediaSaleCacheVo.getMediaList())) {
				for(MediaCacheWholeVo mediaVo : mediaSaleCacheVo.getMediaList()){
					// meida是否支持全本购买
					if (mediaVo.getIsSupportFullBuy() == null
							|| mediaVo.getIsSupportFullBuy() != Constans.MEDIA_SALE_IS_SUPPORT_FULLBUY) {
						throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
								ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
					}
					// meida是否已下架
					if (mediaVo.getShelfStatus() == null
							|| mediaVo.getShelfStatus() != Constans.MEDIA_SHELF_STATUS_UP) {
						throw new MediaException(ErrorCodeEnum.ERROR_CODE_18002.getErrorCode() + "",
								ErrorCodeEnum.ERROR_CODE_18002.getErrorMessage());
					}
				}
				// 封装订单详情和订单章节详情实体数据
				orderDetail.setSaleInfoId(mediaSaleCacheVo.getSaleId());
				orderDetail.setSaleInfoName(mediaSaleCacheVo.getName());
				orderDetail.setTotalPrice(Integer.valueOf(mediaSaleCacheVo.getPrice() + ""));
				orderMain.setTotalPrice(orderDetail.getTotalPrice());
			} else {
				LogUtil.error(logger, "订单号：{0}，创建订单时查询不到电子书相关信息", orderNo);
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage());
			}
			orderMain.getOrderDetails().add(orderDetail);
			apportionedAmount(orderMain, createOrderVo);
		}
		createOrderVo.setOrderMain(orderMain);
		return createOrderVo.getOrderMain();
	}

	@Override
	public boolean checkParamsForCreateOrder(CreateOrderVo createOrderVo) {
		return false;
	}

	@Override
	public OrderMain saveOrderInfo(CreateOrderVo createOrderVo) throws Exception {
		// 设置回滚
		createOrderVo.setRollbackPoint(Constans.CREATE_ORDER_ROLLBACK_SOI);
		this.save(createOrderVo.getOrderMain());
		for (OrderDetail orderDetail : createOrderVo.getOrderMain().getOrderDetails()) {
			orderDetailService.save(orderDetail);
			for (OrderDetailChapter detailChapter : orderDetail.getOrderDetailChapters()) {
				detailChapter.setOrderDetailId(orderDetail.getOrderDetailId());
			}
			if (orderDetail.getOrderDetailChapters().size() > 0) {
				orderDetailChapterService.batchInsert(orderDetail.getOrderDetailChapters());
			}
		}
		return createOrderVo.getOrderMain();
	}

	/**
	 * 
	 * Description: 订单分摊金额
	 * 
	 * @Version1.0 2014年12月3日 下午4:58:06 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param orderMain
	 * @param createOrderVo
	 */
	private void apportionedAmount(OrderMain orderMain,
			CreateOrderVo createOrderVo) {
		Long activityPrice = null;
		String activeId = null;
		// 获取可用的活动列表
		List<ActivityInfo> activityList = activityInfoService.findListByParams(
				"withinPeriodValidity", "1", "status", 1);
		if (orderMain.getWholeFlag() == Constans.ORDER_WHOLE_YES) {
			// 全本购买
			if (activityList != null && !activityList.isEmpty()) {
				for (ActivityInfo activityInfo : activityList) {
					// 限免
					if (activityInfo.getActivityTypeId() == ActivityTypeEnum.TIME_FREE
							.getKey()) {
						List<ActivitySale> activitySaleList = activitySaleService
								.findListByParams("saleId", orderMain
										.getOrderDetails().get(0)
										.getSaleInfoId(),
										"withinPeriodValidity", "1",
										"activityId",
										activityInfo.getActivityId());
						activityPrice = (activitySaleList != null
								&& !activitySaleList.isEmpty() ? 0L : null);
						if (activityPrice != null && activityPrice == 0) {
							activeId = activityInfo.getActivityId() + "";
							break;
						}
					}
				}
				if (StringUtils.isBlank(activeId)) {
					for (ActivityInfo activityInfo : activityList) {
						// 一口价
						if (activityInfo.getActivityTypeId() == ActivityTypeEnum.A_PRICE
								.getKey()) {
							List<ActivitySale> activitySaleList = activitySaleService
									.findListByParams("saleId",
											createOrderVo.getSaleId(),
											"withinPeriodValidity", "1",
											"activityId",activityInfo.getActivityId());
							activityPrice = (activitySaleList != null
									&& !activitySaleList.isEmpty() ? activitySaleList
									.get(0).getSalePrice() : null);
							if(activityPrice != null){
								activeId = activityInfo.getActivityId() + "";
								break;
							}							
						}
					}
				}
			}
			if (activityPrice != null
					&& activityPrice.intValue() <= orderMain.getTotalPrice()
							.intValue()) {
				orderMain.setPrePrice(orderMain.getTotalPrice()
						- Integer.valueOf(activityPrice + ""));
				orderMain.setActiveId(activeId);
			} else {
				orderMain.setPrePrice(0);
			}
			orderMain.setCouponPrice(0);
			orderMain.setGivingPoint(0);
			OrderDetail orderDetail = orderMain.getOrderDetails().get(0);
			orderDetail.setPrePrice(orderMain.getPrePrice());
			orderDetail.setActiveId(orderMain.getActiveId());
			orderDetail.setCouponPrice(0);
			orderDetail.setGivingPoint(0);
		} else {
			// 章节购买
			if (activityList != null && !activityList.isEmpty()) {
				for (ActivityInfo activityInfo : activityList) {
					// 限免
					if (activityInfo.getActivityTypeId() == ActivityTypeEnum.TIME_FREE
							.getKey()) {
						List<ActivitySale> activitySaleList = activitySaleService
								.findListByParams("saleId", orderMain
										.getOrderDetails().get(0)
										.getSaleInfoId(),
										"withinPeriodValidity", "1",
										"activityId",
										activityInfo.getActivityId());
						activityPrice = (activitySaleList != null
								&& !activitySaleList.isEmpty() ? 0L : null);
						if (activityPrice != null && activityPrice == 0) {
							activeId = activityInfo.getActivityId() + "";
							break;
						}
					}
				}
			}
			if (activityPrice != null
					&& activityPrice.intValue() <= orderMain.getTotalPrice()
							.intValue()) {
				orderMain.setPrePrice(orderMain.getTotalPrice()
						- Integer.valueOf(activityPrice + ""));
				orderMain.setActiveId(activeId);
			} else {
				orderMain.setPrePrice(0);
			}
			orderMain.setCouponPrice(0);
			orderMain.setGivingPoint(0);
			OrderDetail orderDetail = orderMain.getOrderDetails().get(0);
			orderDetail.setPrePrice(orderMain.getPrePrice());
			orderDetail.setCouponPrice(0);
			orderDetail.setGivingPoint(0);
			orderDetail.setActiveId(orderMain.getActiveId());
			// 分摊订单章节明细优惠金额
			int totalPrePrice = orderDetail.getPrePrice();
			int surplusPrePrice = totalPrePrice;
			for (OrderDetailChapter chapter : orderDetail
					.getOrderDetailChapters()) {
				chapter.setGivingPoint(0);
				chapter.setCouponPrice(0);
				if (surplusPrePrice == 0) {
					chapter.setPrePrice(0);
					continue;
				}
				int chapterPrice = chapter.getChapterPrice();
				double coefficient = Double.valueOf(chapterPrice)
						/ Double.valueOf(orderDetail.getTotalPrice());
				int chapterPrePrice = new BigDecimal(totalPrePrice
						* coefficient).setScale(0, BigDecimal.ROUND_UP)
						.intValue();
				if (chapterPrePrice > chapterPrice) {
					chapterPrePrice = chapterPrice;
				}
				if (surplusPrePrice >= chapterPrePrice) {
					surplusPrePrice = surplusPrePrice - chapterPrePrice;
				} else {
					chapterPrePrice = surplusPrePrice;
					surplusPrePrice = 0;
				}
				chapter.setPrePrice(chapterPrePrice);
			}
		}
	}

	/**
	 * 
	 * Description: 预处理(扣减阅读币，礼券，积分，礼品卡等占用)
	 * 
	 * @Version1.0 2014年11月26日 下午2:23:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 */
	private void pretreatmentForCreateOrder(CreateOrderVo createOrderVo) throws Exception {
		OrderMain orderMain = createOrderVo.getOrderMain();
		if (orderMain.getTotalPrice().intValue() - orderMain.getPrePrice().intValue() < 1) {
			orderMain.setPaySubPrice(0);
			orderMain.setPayMainPrice(0);
			orderMain.setVspPrice(0);
			OrderDetail orderDetail = orderMain.getOrderDetails().get(0);
			orderDetail.setPayMainPrice(orderMain.getPayMainPrice());
			orderDetail.setPaySubPrice(orderMain.getPaySubPrice());
			orderDetail.setVspPrice(orderMain.getVspPrice());
			if (orderMain.getWholeFlag() == Constans.ORDER_WHOLE_NOT) {
				// 非全本订单
				for (OrderDetailChapter chapter : orderDetail.getOrderDetailChapters()) {
					chapter.setPayMainPrice(0);
					chapter.setPaySubPrice(0);
					chapter.setVspPrice(0);
				}
			}
			return;
		}
		// 用户账户扣减阅读币
		UserTradeBaseVo vo = new UserTradeBaseVo();
		vo.setTradeType(Constans.TRADE_TYPE_CONSUME);
		vo.setOnlyPayMainAmount(false);
		vo.setCustId(orderMain.getCustId());
		vo.setRequirePayAmount(orderMain.getTotalPrice().intValue() - orderMain.getPrePrice().intValue());
		vo.setConsumeSource(createOrderVo.getDeviceType());
		vo.setSourceOrderNo(orderMain.getOrderNo());
		vo.setUsername(createOrderVo.getUsername());
		UserTradeBaseVo resultVo = null;
		try {
			resultVo = userService.tradeForUser(vo);
			if (resultVo != null && resultVo.isTradeResult()) {
				// 设置回滚
				createOrderVo.setRollbackPoint(Constans.CREATE_ORDER_ROLLBACK_PFCO);
				orderMain.setPayTime(new Date());
				if (resultVo.getPaySubAmount() != null && resultVo.getPaySubAmount() > 0) {
					orderMain.setPayment(Constans.CONSUME_PAYMENT_SUB);
					orderMain.setPaySubPrice(resultVo.getPaySubAmount());
				} else {
					orderMain.setPaySubPrice(0);
				}
				if (resultVo.getPayMainAmount() != null && resultVo.getPayMainAmount() > 0) {
					if (StringUtils.isBlank(orderMain.getPayment())) {
						orderMain.setPayment(Constans.CONSUME_PAYMENT_MAIN);
					} else {
						orderMain.setPayment(Constans.CONSUME_PAYMENT_MIXED);
					}
					orderMain.setPayMainPrice(resultVo.getPayMainAmount());
				} else {
					orderMain.setPayMainPrice(0);
				}
				orderMain.setVspPrice(orderMain.getPayMainPrice());
				// 分摊主账户支付金额和副账户支付金额
				// 目前无购车功能，一个主订单一条订单明细
				if (orderMain.getOrderDetails().size() == 1) {
					OrderDetail orderDetail = orderMain.getOrderDetails().get(0);
					orderDetail.setPayMainPrice(orderMain.getPayMainPrice());
					orderDetail.setPaySubPrice(orderMain.getPaySubPrice());
					orderDetail.setVspPrice(orderMain.getVspPrice());
					if (orderMain.getWholeFlag() == Constans.ORDER_WHOLE_NOT) {
						// 非全本订单
						apportionAmountForOrder(orderMain);

					}
				}
			} else {
				LogUtil.error(logger, "订单号：{0}，创建订单时用户消费失败，custId：{1}", orderMain.getOrderNo(), orderMain.getCustId());
				throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode() + "",
						ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
			}
		} catch (Exception e) {
			LogUtil.error(logger, "订单号：{0}，创建订单时调用用户交易接口失败，custId：{1}", orderMain.getOrderNo(), orderMain.getCustId());
			throw new MediaException(ErrorCodeEnum.ERROR_CODE_18001.getErrorCode() + "",
					ErrorCodeEnum.ERROR_CODE_18001.getErrorMessage());
		}
	}

	/**
	 * 
	 * Description: 更新用户相关信息
	 * 
	 * @Version1.0 2014年11月26日 下午2:31:50 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @throws Exception
	 */
	private void updateUserInfoForCreateOrder(CreateOrderVo createOrderVo) throws Exception {
		// 设置回滚
		createOrderVo.setRollbackPoint(Constans.CREATE_ORDER_ROLLBACK_UUIFCO);
	}

	/**
	 * 
	 * Description: 后处理(发消息异步处理：记录日志，权限绑定，赠送礼券，积分等)
	 * 
	 * @Version1.0 2014年11月26日 下午2:41:42 by 张宪斌（zhangxianbin@dangdang.com）创建
	 */
	private UserAuthority aftertreatmentForCreateOrder(CreateOrderVo createOrderVo) throws Exception {
		// 绑定电子书权限
		UserAuthority result = null;
		try {
			result = userAuthorityService.bindUserAuthority(createOrderVo.getOrderMain());
		} catch (Exception e) {
			LogUtil.error(logger, "调用用户绑定电子书权限接口失败！custId:{0}", createOrderVo.getCustId());
			throw new MediaException("调用用户绑定电子书权限接口失败！");
		}
		// 设置回滚
		createOrderVo.setRollbackPoint(Constans.CREATE_ORDER_ROLLBACK_AFCO);
		// 发送后继处理消息
		try {
			rabbitTemplate.convertAndSend("media.create.order.subsequent.queue", createOrderVo);
			logger.info("发送创建订单后继处理消息成功:" + createOrderVo.getOrderMain().getOrderNo());
		} catch (Exception e) {
			logger.error("发送消息失败:" + createOrderVo.getOrderMain().getOrderNo(), e);
		}

		return result;
	}

	/**
	 * 
	 * Description: 异常回滚操作
	 * 
	 * @Version1.0 2014年11月26日 下午2:59:59 by 张宪斌（zhangxianbin@dangdang.com）创建
	 */
	private void rollbackForCreateOrder(CreateOrderVo createOrderVo) throws Exception {
		// 回滚用户阅读币扣减
		if (createOrderVo.getRollbackPoint() >= Constans.CREATE_ORDER_ROLLBACK_PFCO) {
			UserTradeBaseVo vo = new UserTradeBaseVo();
			vo.setTradeType(Constans.TRADE_TYPE_RECHARGE);
			vo.setCustId(createOrderVo.getCustId());
			vo.setFromPaltform(createOrderVo.getFromPaltform());
			vo.setConsumeSource(createOrderVo.getDeviceType());
			vo.setSourceOrderNo(createOrderVo.getOrderMain().getOrderNo());
			vo.setUsername(createOrderVo.getUsername());
			UserTradeBaseVo resultVo = null;
			try {
				if (createOrderVo.getOrderMain().getPayMainPrice() > 0) {
					vo.setRequireRechargeMainAmount(createOrderVo.getOrderMain().getPayMainPrice());
					vo.setRequireRechargeSubAmount(0);
					resultVo = userService.tradeForUser(vo);
					if (resultVo != null && resultVo.isTradeResult()) {
						LogUtil.info(logger, "用户扣减阅读币回滚操作成功！custId:{0}", createOrderVo.getCustId());
					} else {
						LogUtil.error(logger, "用户扣减阅读币回滚操作失败！custId:{0}", createOrderVo.getCustId());
						throw new MediaException("用户扣减阅读币回滚操作失败！");
					}
				}
				if (createOrderVo.getOrderMain().getPaySubPrice() > 0) {
					vo.setRequireRechargeMainAmount(0);
					vo.setRequireRechargeSubAmount(createOrderVo.getOrderMain().getPaySubPrice());
					vo.setActivityCode(RechargeActivityCodeEnum.BUDAN.getCode());
					resultVo = userService.tradeForUser(vo);
					if (resultVo != null && resultVo.isTradeResult()) {
						LogUtil.info(logger, "用户扣减阅读币回滚操作成功！custId:{0}", createOrderVo.getCustId());
					} else {
						LogUtil.error(logger, "用户扣减阅读币回滚操作失败！custId:{0}", createOrderVo.getCustId());
						throw new MediaException("用户扣减阅读币回滚操作失败！");
					}
				}

			} catch (Exception e) {
				LogUtil.error(logger, e, "用户扣减阅读币回滚操作调用接口失败！custId:{0}", createOrderVo.getCustId());
				throw new MediaException("用户扣减阅读币回滚操作调用接口失败！");
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserAuthority createAndSaveOrderOperation(CreateOrderVo createOrderVo) throws Exception {
		UserAuthority result = null;
		try {
			// 封装订单数据
			this.encapsulateOrderInfo(createOrderVo);
			// 预处理(扣减阅读币，礼券，积分，礼品卡等占用)
			pretreatmentForCreateOrder(createOrderVo);
			// 保存订单
			this.saveOrderInfo(createOrderVo);
			// 后处理(发消息异步处理：记录日志，权限绑定，赠送礼券，积分等)
			result = aftertreatmentForCreateOrder(createOrderVo);
			// 更新用户信息
			updateUserInfoForCreateOrder(createOrderVo);
		} catch (Exception e) {
			// 回滚操作
			rollbackForCreateOrder(createOrderVo);
			throw e;
		}
		return result;
	}

	/**
	 * 
	 * Description: 主副账户支付分摊
	 * 
	 * @Version1.0 2014年12月11日 上午10:50:19 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param orderMain
	 */
	private void apportionAmountForOrder(OrderMain orderMain) {
		for (OrderDetail orderDetail : orderMain.getOrderDetails()) {
			int totalPayMainPrice = orderDetail.getPayMainPrice();
			int surplusPayMainPrice = totalPayMainPrice;
			for (OrderDetailChapter chapter : orderDetail.getOrderDetailChapters()) {
				chapter.setGivingPoint(0);
				chapter.setCouponPrice(0);
				int chapterPrice = chapter.getChapterPrice();
				double coefficient = Double.valueOf(chapterPrice) / Double.valueOf(orderDetail.getTotalPrice());
				if (surplusPayMainPrice == 0) {
					chapter.setPayMainPrice(0);
					chapter.setVspPrice(0);
				} else {
					int chapterMainPrice = new BigDecimal(totalPayMainPrice * coefficient).setScale(0,
							BigDecimal.ROUND_UP).intValue();
					if (chapterMainPrice > chapterPrice - chapter.getPrePrice()) {
						chapterMainPrice = chapterPrice - chapter.getPrePrice();
					}
					if (surplusPayMainPrice >= chapterMainPrice) {
						surplusPayMainPrice = surplusPayMainPrice - chapterMainPrice;
					} else {
						chapterMainPrice = surplusPayMainPrice;
						surplusPayMainPrice = 0;
					}
					chapter.setPayMainPrice(chapterMainPrice);
					chapter.setVspPrice(chapterMainPrice);
				}
				chapter.setPaySubPrice(chapter.getChapterPrice() - chapter.getPrePrice() - chapter.getPayMainPrice());
			}
		}
	}
}
