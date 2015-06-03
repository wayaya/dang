/**
 * Description: OrderMainServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2014年11月14日 上午10:59:51  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.dangdang.digital.constant.PayFromPaltform;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IOrderMainDao;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.OrderQueryVo;

/**
 * Description: 主订单实体service实现类 All Rights Reserved.
 * 
 * @version 1.0 2014年11月14日 上午10:59:51 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Service
public class OrderMainServiceImpl extends BaseServiceImpl<OrderMain, Long>
		implements IOrderMainService {
	@Resource(name = "masterRedisTemplate")
	protected RedisTemplate<String, Integer> masterRedisTemplate;
	@Resource
	IOrderMainDao orderMainDao;
	private DecimalFormat decimalFormat = new DecimalFormat("000000");
	private final String CREATE_ORDER_NO_SEQ = "create_order_no_seq";

	@Override
	public IBaseDao<OrderMain> getBaseDao() {
		return this.orderMainDao;
	}

	@Override
	public PageFinder<OrderMain> findPageFinderByOrderQueryVo(OrderQueryVo vo,
			Query query) {
		Map<String, Object> map = convertBeanToMap(vo);
		if (!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName")
				&& !map.containsKey("mediaName")
				&& !map.containsKey("chapterNo")) {
			return this.findPageFinderObjs(map, query);
		}
		return orderMainDao.getPageFinderObjs(map, query,
				"OrderMainMapper.pageCountByOrderQuery",
				"OrderMainMapper.pageDataByOrderQuery");
	}

	@Override
	public List<OrderMain> findListByOrderQueryVo(OrderQueryVo vo) {
		Map<String, Object> map = convertBeanToMap(vo);
		if (!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName")
				&& !map.containsKey("mediaName")
				&& !map.containsKey("chapterNo")) {
			return this.findListByParamsObjs(map);
		}
		return orderMainDao.selectList("OrderMainMapper.getAllByOrderQuery",
				map);
	}

	@Override
	public OrderMain findUniqueByOrderQueryVo(OrderQueryVo vo) {
		Map<String, Object> map = convertBeanToMap(vo);
		if (!map.containsKey("saleInfoId") && !map.containsKey("saleInfoName")
				&& !map.containsKey("mediaName")
				&& !map.containsKey("chapterNo")) {
			return this.findUniqueByParamsObjs(map);
		}
		return orderMainDao
				.selectOne("OrderMainMapper.getAllByOrderQuery", map);
	}

	@Override
	public String createOrderNo(String chanelCode) {
		if (StringUtils.isBlank(chanelCode)) {
			LogUtil.error(logger, "生成订单号时chanelCode为空！");
			chanelCode = "OT";
		}
		String seqStr = null;
		try {
			long seq = masterRedisTemplate.opsForValue().increment(
					CREATE_ORDER_NO_SEQ, new Random().nextInt(10) + 1);
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
		return chanelCode + DateUtil.getDate(new Date(), "yyMMddhhmm") + seqStr;
	}

	@Override
	public OrderMain createOrderInfo(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender, String orderNo)
			throws Exception {
		return null;
	}

	@Override
	public boolean checkAndEncapsulateParams(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender,
			CreateOrderVo createOrderVo,ChapterCacheBasicVo chapter) {
		String fromPaltform = request.getParameter("fromPaltform");
		if(StringUtils.isNotBlank(fromPaltform)){
			if(PayFromPaltform.getBySource(fromPaltform) == null){
				return false;
			}
		}else{
			fromPaltform = PayFromPaltform.YC_ANDROID.getSource();
		}
		createOrderVo.setFromPaltform(fromPaltform);
		String platform = request.getParameter("platform");
		if (StringUtils.isNotBlank(platform)) {
			createOrderVo.setPlatform(platform);
		} else {
			return false;
		}
		String saleId = request.getParameter("saleId");
		if (StringUtils.isNotBlank(saleId) && StringUtils.isNumeric(saleId)) {
			createOrderVo.setSaleId(Long.valueOf(saleId));
		}
		String clientVersionNo = request.getParameter("clientVersionNo");
		if (StringUtils.isNotBlank(clientVersionNo)) {
			createOrderVo.setDeviceVersion(clientVersionNo);
		}
		String deviceType = request.getParameter("deviceType");
		if (StringUtils.isNotBlank(deviceType)) {
			createOrderVo.setDeviceType(deviceType);
		} else {
			return false;
		}
		String channelId = request.getParameter("channelId");
		if (StringUtils.isNotBlank(channelId)) {
			createOrderVo.setChanelCode(channelId);
		} else {
			return false;
		}
//		String payment = request.getParameter("payment");
//		if (StringUtils.isNotBlank(payment)) {
//			createOrderVo.setPayment(payment);
//		} else {
//			return false;
//		}
		if (chapter != null) {
			createOrderVo.getMediaIds().add(chapter.getMediaId());
		} else {
			String mediaId = request.getParameter("mediaId");
			if (StringUtils.isNotBlank(mediaId) && StringUtils.isNumeric(mediaId)) {
				createOrderVo.getMediaIds().add(Long.valueOf(mediaId));
			}
		}
		if (chapter != null) {
			createOrderVo.getChapterIds().add(chapter.getId());
		} else {
			String chapterIds = request.getParameter("chapterIds");
			if (StringUtils.isNotBlank(chapterIds)) {
				String[] arrayIds = chapterIds.split(",");
				for (String id : arrayIds) {
					if (StringUtils.isNumeric(id)) {
						createOrderVo.getChapterIds().add(Long.valueOf(id));
					} else {
						return false;
					}
				}
			}
		}
		
		String activityIds = request.getParameter("activityIds");
		if (StringUtils.isNotBlank(activityIds)) {
			String[] arrayIds = activityIds.split(",");
			for (String id : arrayIds) {
				if (StringUtils.isNumeric(id)) {
					createOrderVo.getActivityIds().add(Integer.valueOf(id));
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public OrderMain saveOrderInfo(OrderMain orderMain) throws Exception {
		return null;
	}

}
