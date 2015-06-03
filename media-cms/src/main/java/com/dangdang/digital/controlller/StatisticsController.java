package com.dangdang.digital.controlller;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.dao.IMediaDao;
import com.dangdang.digital.model.RewardTop;
import com.dangdang.digital.service.IDDClickService;
import com.dangdang.digital.service.IStatisticsService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.vo.IncomeVo;

/**
 * Description: 数据统计 All Rights Reserved.
 * 
 * @version 1.0 2015年3月30日 下午6:45:22 by
 *          yangzhenping（yangzhenping@dangdang.com）创建
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController {
	@Resource
	IStatisticsService statisticsService;
	@Resource
	private IDDClickService ddClickService;

	@Resource
	private IMediaDao mediaDao;

	/**
	 * 
	 * Description: 单品浏览前100
	 * @Version1.0 2015年3月31日 下午2:35:02 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="/singleview")
	public String getSingleViewTopn(Integer topn,@DateTimeFormat( pattern ="yyyy-MM-dd HH:mm:ss")Date startTime,@DateTimeFormat( pattern ="yyyy-MM-dd HH:mm:ss") Date endTime,final Model model){
		if(null == startTime){
			startTime  = DateUtil.getOnlyDay(new Date());
		}
		if(null == endTime){
			endTime = DateUtil.addDaysOnToday(startTime,1);
		}
		if(null ==topn ||topn.intValue()<=0){
			topn =100;
		}
		List<LinkedHashMap<String, Object>> ddClicks = ddClickService.getTopNSalePageBrowseMedia(startTime, endTime,topn.intValue());
		List<Map<String, Object>> browseMediaList = new LinkedList<Map<String, Object>>();
		if(null != ddClicks){
			List<Long> saleIdList = new ArrayList<Long>();
			Map<String, String> countMap = new HashMap<String, String>();

			for (LinkedHashMap<String, Object> map : ddClicks) {
				saleIdList.add(Long.valueOf(map.get("saleId").toString()));
				countMap.put(map.get("saleId").toString(), map.get("count").toString()+"_"+map.get("freeViewCount").toString());
			}
			browseMediaList = mediaDao.statisticsBySaleIds(startTime,endTime,saleIdList);
			for (Map<String, Object> media : browseMediaList) {
				if (media.get("sale_id") != null && countMap.containsKey(media.get("sale_id").toString())) {
					String count = countMap.get(media.get("sale_id").toString());
					if(null != count){
						String[]countList = count.split("_");
						media.put("count", countList[0]);
						media.put("freeViewCount", countList[1]);
					}
				} else {
					media.put("count", 0);
					media.put("freeViewCount", 0);
				}
			}
			Collections.sort(browseMediaList, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return (Integer.valueOf(o2.get("count").toString()) - Integer.valueOf(o1.get("count").toString()));
				}
			});
		}
		model.addAttribute("dataList" , browseMediaList);
		return "statistics/singleview";
	}
	
	
	/**
	 * 
	 * Description: 获取免费试读前100
	 * @Version1.0 2015年3月31日 下午2:35:45 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="/freeview")
	public String getFreeViewTopn(Integer topn,@DateTimeFormat( pattern ="yyyy-MM-dd HH:mm:ss")Date startTime,@DateTimeFormat( pattern ="yyyy-MM-dd HH:mm:ss") Date endTime,final Model model){
		if(null == startTime){
			startTime  = DateUtil.getOnlyDay(new Date());
		}
		if(null == endTime){
			endTime = DateUtil.addDaysOnToday(startTime,1);
		}
		if(null ==topn ||topn.intValue()<=0){
			topn =100;
		}
		List<LinkedHashMap<String, Object>> ddClicks = ddClickService.getTopNFreeTryBrowseMedia(startTime, endTime, topn.intValue());
		List<Map<String, Object>> freeTryMediaList =  new LinkedList<Map<String, Object>>();
		if(null != ddClicks){
			List<Long> mediaIdList = new ArrayList<Long>();
			Map<String, String> countMap = new HashMap<String, String>();

			for (LinkedHashMap<String, Object> map : ddClicks) {
				mediaIdList.add(Long.valueOf(map.get("mediaId").toString()));
				countMap.put(map.get("mediaId").toString(), map.get("count").toString()+"_"+map.get("pageViewCount").toString());
			}
			 freeTryMediaList = mediaDao.statisticsByMediaIds(startTime,endTime,mediaIdList);
			for (Map<String, Object> media : freeTryMediaList) {
				if (media.get("media_id") != null && countMap.containsKey(media.get("media_id").toString())) {
					String count = countMap.get(media.get("media_id").toString());
					if(null!=count){
						String[]countList =count.split("_");
						media.put("count", countList[0]);
						media.put("pageViewCount", countList[1]);
					}
				} else {
					media.put("count", 0);
					media.put("pageViewCount", 0);
				}
			}
			Collections.sort(freeTryMediaList, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return (Integer.valueOf(o2.get("count").toString()) - Integer.valueOf(o1.get("count").toString()));
				}
			});
		}
		
		model.addAttribute("dataList", freeTryMediaList);
		return "statistics/freeview";
	}
	
	/**
	 * Description: 查询数据 All Rights Reserved.
	 * 
	 * @version 1.0 2015年3月30日 下午6:47:11 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String getIncomeData(Model model, @RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate) {
		IncomeVo incomeVo = new IncomeVo();
		if (!"".equals(endDate) && !"".equals(startDate)) {
			endDate += " 23:59:59";
			incomeVo = statisticsService.getIncomeVo(startDate, endDate);
		}
		model.addAttribute("income", incomeVo);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistics/list";
	}

	/**
	 * Description: 打赏Top100 All Rights Reserved.
	 * 
	 * @version 1.0 2015年4月2日 下午5:00:00 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/rewardTop")
	public String getRewardTop(Model model, @RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate, Integer count) {
		List<RewardTop> rewardTopList = new ArrayList<RewardTop>();
		if (!"".equals(endDate)) {
			endDate += " 23:59:59";
		}
		rewardTopList = statisticsService.getRewardTop(startDate, endDate, count);
		model.addAttribute("rewardTopList", rewardTopList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("count", count);
		return "statistics/rewardTop";
	}

	/**
	 * Description: 单章购买金铃铛Top100 All Rights Reserved.
	 * 
	 * @version 1.0 2015年4月2日 下午5:00:21 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/buyChapterGlobTop")
	public String getBuyChapterGlobTop(Model model, @RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate, Integer count) {
		List<RewardTop> rewardTopList = new ArrayList<RewardTop>();
		if (!"".equals(endDate)) {
			endDate += " 23:59:59";
		}
		rewardTopList = statisticsService.getBuyChapterGlobTop(startDate, endDate, count);
		model.addAttribute("rewardTopList", rewardTopList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("count", count);
		return "statistics/buyChapterGlobTop";
	}

	/**
	 * Description: 单章购买银铃铛Top100 All Rights Reserved.
	 * 
	 * @version 1.0 2015年4月2日 下午5:00:56 by
	 *          yangzhenping（yangzhenping@dangdang.com）创建
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/buyChapterSilverTop")
	public String getBuyChapterSilverTop(Model model, @RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate, Integer count) {
		List<RewardTop> rewardTopList = new ArrayList<RewardTop>();
		if (!"".equals(endDate)) {
			endDate += " 23:59:59";
		}
		rewardTopList = statisticsService.getBuyChapterSilverTop(startDate, endDate, count);
		model.addAttribute("rewardTopList", rewardTopList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("count", count);
		return "statistics/buyChapterSilverTop";
	}
}
