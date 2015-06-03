/**
 * Description: GetDigestHomePageProcessor.java
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午9:38:34  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.processor.discovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.dangdang.digital.constant.DigestDayOrNightEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.dao.IDigestCacheDao;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 精品主页
 * All Rights Reserved.
 * @version 1.0  2015年1月22日 下午9:38:34  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestHomePageprocessor")
@Deprecated
public class GetDigestHomePageProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDigestHomePageProcessor.class);
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	//日期起始整点
	public static final String DATE_SUFFIX = " 00:00:00";
	//首页下翻递归深度
	public static final Integer DIGEST_HOME_PAGE_RECURSION_DEPTH = ConfigPropertieUtils.getInteger("digest.home.page.recursion.depth", 31);
	
	private static final Set<String> includeFields = new HashSet<String>();
	
	static{
		includeFields.add("id");
		includeFields.add("signList");
		includeFields.add("cardRemark");
		includeFields.add("cardType");
		includeFields.add("cardTitle");
		includeFields.add("pic1Path");
		includeFields.add("createTime");
		includeFields.add("authorList");
		includeFields.add("showStartDateYmdLong");
	}
	
	@Resource
	private IDigestService digestService;
	
	@Resource
	private IDigestCacheDao digestCacheDao;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		//必传，需校验
		String dayOrNight = request.getParameter("dayOrNight");
		//必传，需校验
		String action= request.getParameter("act");
		//非必传，每一页第一条或者最后一条精品主键id，取决于action参数
		String digestId = request.getParameter("digestId");
		//非必传，年-月-日对应的long型
		String pageTime = request.getParameter("pageTime");
		//非必传
		String pageSizeStr = StringUtils.isNotBlank(request.getParameter("pageSize"))?request.getParameter("pageSize"):String.valueOf(DIGEST_DEFAULT_PAGE_SIZE);
		
		if(!checkParams(dayOrNight, action)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//校验dayOrNight合法性
		if(!DigestDayOrNightEnum.getDayOrNightNames().contains(dayOrNight)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		//校验action合法性
		if(!(Digest.NEW_ACTION.equals(action) || Digest.OLD_ACTION.equals(action))){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		Integer dayNight = DigestDayOrNightEnum.getByName(dayOrNight).getKey();
		Integer pageSize = Integer.parseInt(pageSizeStr);
		try {
			if(Digest.NEW_ACTION.equals(action)){
				Date now = new Date();
				String nowYmd = DateUtil.format(now, DATE_PATTERN);
				List<Digest> allDigests = this.recursiveDigestList(nowYmd, dayNight);
				if(StringUtils.isNotBlank(digestId)){
					Digest digest = digestService.findDigestById(Long.parseLong(digestId));
					if(digest != null){
						Date show = digest.getShowStartDate();
						String showYmd = DateUtil.format(show, DATE_PATTERN);
						//不夸天情况
						if(nowYmd.equals(showYmd)){
							List<Digest> newDigests = digestService.queryDigestListByDateSlotAndDayOrNight(DateUtil.addMillSecond(show, 1000L), DateUtil.getMaxTimeByStringDate(showYmd), dayNight);
							if(CollectionUtils.isNotEmpty(newDigests)){
								//将新获取的数据插入到旧列表前
								if(CollectionUtils.isNotEmpty(allDigests)){
									List<Digest> needUpdateDigests = new ArrayList<Digest>(newDigests);
									needUpdateDigests.addAll(allDigests);
									//刷新缓存内容:newDigests
									digestCacheDao.setDigestListToCache(dayOrNight, nowYmd, needUpdateDigests);
								}else{
									digestCacheDao.setDigestListToCache(dayOrNight, nowYmd, newDigests);
								}
							}
							List<Digest> subList = this.subPageDigest(newDigests, pageSize, null);
							sender.put("digestList", this.filterDigestField(subList));
							sender.put("crossDayNew", false);
							this.wrapperResultSender(newDigests, subList, nowYmd, sender, dayNight);
						}else{//跨天情况
							List<Digest> newDigests = digestService.queryDigestListByDateSlotAndDayOrNight(DateUtil.getMinTimeByStringDate(nowYmd), new Date(), dayNight);
							if(CollectionUtils.isNotEmpty(newDigests)){
								digestCacheDao.setDigestListToCache(dayOrNight, nowYmd, newDigests);
							}
							List<Digest> subList = this.subPageDigest(newDigests, pageSize, null);
							sender.put("digestList", this.filterDigestField(subList));
							sender.put("crossDayNew", true);
							this.wrapperResultSender(allDigests, subList, nowYmd, sender, dayNight);
						}
					}
				}else{//第一次进APP首页
					List<Digest> subList = this.subPageDigest(allDigests, pageSize, null);
					sender.put("digestList", this.filterDigestField(subList));
					sender.put("crossDayNew", true);
					this.wrapperResultSender(allDigests, subList, nowYmd, sender, dayNight);
				}
			}else if(Digest.OLD_ACTION.equals(action)){
				//上拉请求中digestId需必传
				if(!checkParams(digestId, pageTime)){
					sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
					return;
				}
				
				Date pageDate = new Date(Long.parseLong(pageTime));
				String pageYmd = DateUtil.format(pageDate, DATE_PATTERN);
				//pageYmd天对应的全部精品内容
				List<Digest> allDigestList = this.recursiveDigestList(pageYmd, dayNight);
				if(CollectionUtils.isNotEmpty(allDigestList)){
					List<Digest> subDigestList = null;
					List<Long> digestPrimaryKeys = new ArrayList<Long>();
					for(Digest d:allDigestList){
						digestPrimaryKeys.add(d.getId());
					}
					if(!digestPrimaryKeys.contains(Long.parseLong(digestId))){
						subDigestList = this.subPageDigest(allDigestList, pageSize, null);
					}else{
						subDigestList = this.subPageDigest(allDigestList, pageSize, Long.parseLong(digestId));
					}
					
					boolean needcrossDay = this.shouldChangePageTime(allDigestList, subDigestList);
					if(needcrossDay){
						Date endDate = null;
						if(CollectionUtils.isEmpty(allDigestList)){
							endDate = pageDate;
						}else{
							int allSize = allDigestList.size();
							Digest lastDigest = allDigestList.get(allSize-1);
							endDate = lastDigest.getShowStartDateYmd();
						}
						Digest digest = digestService.getDigestByShowEndDateLimitOne(endDate, dayNight);
						if(digest != null){
							sender.put("pageTime", digest.getShowStartDateYmdLong());
							sender.put("crossDayOld", true);
							//若上一页子集为空时，跨天则展示跨天后的第一页
							if(CollectionUtils.isEmpty(subDigestList)){
								String ymd = digest.getShowStartDateYmdString();
								List<Digest> newAllDigestList = digestService.queryDigestHomePageList(ymd, dayNight);
								sender.put("digestList", this.filterDigestField(this.subPageDigest(newAllDigestList, pageSize, null)));
							}else{
								sender.put("digestList", this.filterDigestField(subDigestList));
							}
						}else{
							sender.put("pageTime", endDate.getTime());
							sender.put("digestList", new ArrayList<Digest>());
							sender.put("crossDayOld", false);
						}
					}else{
						sender.put("pageTime", pageDate.getTime());
						sender.put("digestList", this.filterDigestField(subDigestList));
						sender.put("crossDayOld", false);
					}
				}else{
					sender.put("pageTime", pageDate.getTime());
					sender.put("digestList", new ArrayList<Digest>());
					sender.put("crossDayOld", false);
				}
			}
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "获取精品首页内容失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}
	
	
	/**
	 * Description: 包装ResultSender上拉是否跨天以及pageTime属性
	 * @Version1.0 2015年2月9日 下午3:41:23 by 代鹏（daipeng@dangdang.com）创建
	 * @param allDigests
	 * @param subList
	 * @param ymd
	 * @param sender
	 */
	private void wrapperResultSender(List<Digest> allDigests, List<Digest> subList, String ymd, ResultSender sender, Integer dayNight){
		boolean crossDayOld = this.shouldChangePageTime(allDigests, subList);
		sender.put("crossDayOld", crossDayOld);
		if(crossDayOld){
			sender.put("pageTime", this.getCrossDayOldPageTime(allDigests, dayNight, ymd));
		}else{
			sender.put("pageTime", DateUtil.parseStringToDate(ymd + DATE_SUFFIX).getTime());
		}
	}
	
	
	/**
	 * Description: 上拉分页跨天时，获取下一页跨天的pageTime
	 * @Version1.0 2015年2月9日 下午3:33:18 by 代鹏（daipeng@dangdang.com）创建
	 * @param allDigests
	 * @return
	 */
	private Long getCrossDayOldPageTime(List<Digest> allDigests, Integer dayNight, String ymd){
		Date endDate = null;
		if(CollectionUtils.isEmpty(allDigests)){
			endDate = DateUtil.parseStringToDate(ymd);
		}else{
			int size = allDigests.size();
			Digest lastDigest = allDigests.get(size - 1);
			endDate = lastDigest.getShowStartDate();
		}
		Digest nextDigest = digestService.getDigestByShowEndDateLimitOne(endDate, dayNight);
		if(nextDigest != null){
			return nextDigest.getShowStartDate().getTime();
		}
		return null;
	}
	
	
	/**
	 * Description: 过滤无用字段
	 * @Version1.0 2015年2月9日 下午3:33:06 by 代鹏（daipeng@dangdang.com）创建
	 * @param digestList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Digest> filterDigestField(List<Digest> digestList){
		final int size = includeFields.size();
		String[] arr = (String[])includeFields.toArray(new String[size]);
		SimplePropertyPreFilter spp = new SimplePropertyPreFilter(Digest.class, arr);
		String json = JSON.toJSONString(digestList, spp);
		return JSON.parseObject(json, List.class);
	}
	

	/**
	 * Description: 尝试递归往下取精品记录
	 * @Version1.0 2015年1月29日 上午10:28:14 by 代鹏（daipeng@dangdang.com）创建
	 * @param ymd
	 * @param dayNight
	 * @return
	 * @throws Exception 
	 */
	private List<Digest> recursiveDigestList(String ymd, Integer dayNight) throws Exception{
		List<Digest> digestList = digestService.queryDigestHomePageList(ymd, dayNight);
		if(CollectionUtils.isEmpty(digestList)){
			int count = 0;
			while(true){
				count++;
				if(count > DIGEST_HOME_PAGE_RECURSION_DEPTH){
					return new ArrayList<Digest>();
				}
				Date date = DateUtil.parseStringToDate(ymd + DATE_SUFFIX);
				Date newDate = DateUtil.addDate(date, -count);
				String newYmd = DateUtil.format(newDate, DATE_PATTERN);
				List<Digest> newDigestList = digestService.queryDigestHomePageList(newYmd, dayNight);
				if(CollectionUtils.isNotEmpty(newDigestList)){
					return newDigestList;
				}
			}
		}
		return digestList;
	}
	
	
	/**
	 * Description: 取大于digestId对应索引，且长度<=limit的精品内容
	 * @Version1.0 2015年1月29日 上午10:33:11 by 代鹏（daipeng@dangdang.com）创建
	 * @param list
	 * @param limit
	 * @param digestId
	 * @return
	 */
	private List<Digest> subPageDigest(List<Digest> list, Integer limit, Long digestId){
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<Digest>();
		}else if(limit >= list.size()){
			return list;
		}else if(digestId == null){
			return list.subList(0, limit);
		}else{
			int fromIndex = 0;
			for(Digest item:list){
				if(item.getId().equals(digestId)){
					//下一条数据的索引位置
					fromIndex = list.indexOf(item) + 1;
					break;
				}
			}
			if(fromIndex >= list.size() || fromIndex == 0){
				return new ArrayList<Digest>();
			}
			int toIndex = fromIndex + limit;
			if(toIndex > list.size()){
				return list.subList(fromIndex, list.size());
			}else{
				return list.subList(fromIndex, toIndex);
			}
		}
	}
	
	
	/**
	 * Description: 判断时间是否需要推移
	 * @Version1.0 2015年1月29日 上午11:08:39 by 代鹏（daipeng@dangdang.com）创建
	 * @param totalList
	 * @param subList
	 * @return
	 */
	private boolean shouldChangePageTime(List<Digest> totalList, List<Digest> subList){
		if(CollectionUtils.isEmpty(totalList) || CollectionUtils.isEmpty(subList)){
			return true;
		}else if(CollectionUtils.isNotEmpty(totalList) && CollectionUtils.isNotEmpty(subList)){
			int totalSize = totalList.size();
			int subSize = subList.size();
			Digest totalLastDigest = totalList.get(totalSize-1);
			Digest subLastDigest = subList.get(subSize-1);
			if(totalLastDigest.getId().equals(subLastDigest.getId())){
				return true;
			}
		}
		return false;
	}
	
}
