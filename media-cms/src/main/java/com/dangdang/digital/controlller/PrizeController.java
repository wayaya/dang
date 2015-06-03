package com.dangdang.digital.controlller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.Prize;
import com.dangdang.digital.model.PrizeRecord;
import com.dangdang.digital.service.IPrizeRecordService;
import com.dangdang.digital.service.IPrizeService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

/**
 * Description: 奖品controller
 * All Rights Reserved.
 * @version 1.0  2014年11月27日 下午2:22:49  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Controller
@RequestMapping("prize")
public class PrizeController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrizeController.class);
	
	@Resource IPrizeService prizeService;
	@Resource IPrizeRecordService recordService;
	@Resource
	private ICacheApi cacheApi;
	/**
	 * Description: 分页列表
	 * @Version1.0 2014年11月27日 下午2:23:55 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param page
	 * @param prize
	 * @return
	 */
	@RequestMapping("list")
	public String list(Model model, String page,Prize prize){
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		PageFinder<Prize> pageFinder = prizeService.findPageFinderObjs(prize, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		Double totalPro = 0.0;
		totalPro = prizeService.getTotalPro(1);
		Integer amount = prizeService.getPrizeAmounts(1);
		model.addAttribute("prizeAmount", amount==null?0:amount);
		model.addAttribute("prize", prize);
		model.addAttribute("totalPro", totalPro);
		return "prize/list";
	}
	
	/**
	 * Description: 单个查询
	 * @Version1.0 2014年11月27日 下午2:24:17 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("selectOne")
	public String selectOne(Model model,String id){
		if(id != null){
			Prize prize = prizeService.findMasterById(Long.parseLong(id));
			model.addAttribute("prize", prize);
			//获取当前概率值
			Double total = 0.0;
			total = prizeService.getTotalPro(1);
			model.addAttribute("total", total==null?0.0:total);
		}
		return "prize/update";
	}
	
	
	/**
	 * Description: 更新跳转页
	 * @Version1.0 2014年11月27日 下午2:24:31 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @return
	 */
	@RequestMapping("updatePre")
	public String updatePre(Model model){
		return "prize/add";
	}
	
	/**
	 * Description: 更新
	 * @Version1.0 2014年11月27日 下午2:26:25 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param prize
	 * @return
	 */
	@RequestMapping(value="update")  
	public String update(Model model,Prize prize){
		prize.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
		prize.setLastModifiedDate(new Date());
		int result = prizeService.update(prize);
		if(1==result){
			model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
			model.addAttribute("successFlag", 0);
			cacheApi.deletePrizeListCache(prize.getVestType());
			LogUtil.info(LOGGER,"更改【奖品信息】成功", UsercmsUtils.getCurrentUser().getLoginName(), "[prizeInfo:"+prize.toString()+"]");
		}else{
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>:更新失败，更新条数为："+result);
			LogUtil.info(LOGGER,"更改【奖品信息】失败，更新结果："+result, UsercmsUtils.getCurrentUser().getLoginName(), "[prizeInfo:"+prize.toString()+"]");
		}
		return "prize/update";
	}
	
	
	@RequestMapping("addPre")
	public String addPre(Model model){
		//获取当前概率值
		Double total = 0.0;
		total = prizeService.getTotalPro(1);
		model.addAttribute("total", total==null?0.0:total);
		Integer amount = prizeService.getPrizeAmounts(1);
		model.addAttribute("prizeAmount", amount==null?0:amount);
		return "prize/add";
	}
	
	/**
	 * Description: 增加奖品
	 * @Version1.0 2014年11月27日 下午2:48:23 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param prize
	 * @return
	 */
	@RequestMapping("add")
	public String addOne(Model model,Prize prize){
		if(null==prize){
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "提交信息不能为空");
		}else{
			StringBuffer returnInfo = new StringBuffer();
			if(StringUtils.isBlank(prize.getPrizeName())){
				returnInfo.append("奖品名称不能为空|");
			}
			if(StringUtils.isBlank(prize.getIntroduce()+"")){
				returnInfo.append("奖品介绍不能为空|");
			}
			Double pro = prize.getProbability();
			if(StringUtils.isBlank(pro+"")){
				returnInfo.append("奖品概率不能为空|");
			}
			if(pro<0||pro>1){
				returnInfo.append("奖品概率只能在【0-1】之间包括0,1|");
			}
			if(StringUtils.isBlank(prize.getDayLimit()+"")){
				returnInfo.append("每日上限不能为空|");
			}
			if(StringUtils.isBlank(prize.getTotalLimit()+"")){
				returnInfo.append("总上线不能为空|");
			}
			if(StringUtils.isBlank(prize.getPrizeType()+"")){
				returnInfo.append("奖品类型不能为空|");
			}
			if(StringUtils.isBlank(prize.getPrizeId()+"")){
				returnInfo.append("奖品ID不能为空|");
			}
			if(StringUtils.isBlank(prize.getAmount()+"")){
				returnInfo.append("奖品数量不能为空|");
			}
			if(StringUtils.isBlank(prize.getVestType()+"")){
				returnInfo.append("奖品归属不能为空|");
			}
			if(returnInfo.length() > 0){
				model.addAttribute("successFlag", 1);
				model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>:"+returnInfo.toString().substring(0, returnInfo.length() -1));
			}else{
				prize.setCreationDate(new Date());
				prize.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
				prizeService.save(prize);
				model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
				model.addAttribute("successFlag", 0);
			}
		}
		model.addAttribute("prize", prize);
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		cacheApi.deletePrizeListCache(prize.getVestType());
		LogUtil.info(LOGGER,"增加【奖品】成功", UsercmsUtils.getCurrentUser().getLoginName(), "[prizeInfo:"+prize.toString()+"]");
		return "prize/add";
	}
	
//	@RequestMapping("delete")
//	public String deleteOne(Model model,Long id){
//		if(StringUtils.isNotBlank(id+"")){
//			prizeService.deleteById(id);
//		}
//		return "prize/list";
//	}
	
	/**
	 * Description: 用户抽奖记录明细
	 * @Version1.0 2014年11月20日 下午3:10:09 by 周伟洋（zhouweiyang@dangdang.com）创建
	 * @param model
	 * @param page
	 * @param record
	 * @return
	 */
	@RequestMapping("lotDetail")
	public String detail(Model model, String page,PrizeRecord record){
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		PageFinder<PrizeRecord> pageFinder = recordService.findPageFinderObjs(record, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("limit", record);
		return "prize/lotDetail";
	}
	
}