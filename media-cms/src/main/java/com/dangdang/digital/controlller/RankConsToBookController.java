package com.dangdang.digital.controlller;


import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.model.RankConsToBook;
import com.dangdang.digital.service.IRankConsToBookService;
import com.dangdang.digital.utils.AppUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

@Controller
@RequestMapping("rankConsToBook")
public class RankConsToBookController extends BaseController {
	
	@Resource IRankConsToBookService rankService;
	@Resource
	private ICacheApi cacheApi;
	
	@RequestMapping("list")
	public String list(Model model, String page,RankConsToBook rankOne){
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		Query query = new Query();
		if(StringUtils.isNotBlank(page)){
			query.setPage(Integer.parseInt(page));
		}else{
			query.setPage(1);
		}
		rankOne.setStatus(0);
		PageFinder<RankConsToBook> pageFinder = rankService.findPageFinderObjs(rankOne, query);
		if(pageFinder != null){
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("rankOne", rankOne);
		return "rank/rankConsToBook/list";
	}
	
	@RequestMapping("selectOne")
	public String selectOne(Model model,String id){
		if(id != null){
			RankConsToBook rank = rankService.findMasterById(Long.parseLong(id));
			model.addAttribute("rank", rank);
		}
		return "rank/rankConsToBook/update";
	}
	
	
	@RequestMapping(value="update")  
	public String update(Model model,RankConsToBook rankOne){
		rankOne.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
		rankService.update(rankOne);
		model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
		model.addAttribute("successFlag", 0);
		model.addAttribute("rankOne", rankOne);
		cacheApi.deleteRankConsToBookByCodeCache(rankOne.getCode());
		return "rank/rankConsToBook/update";
	}
	
	@RequestMapping("addPre")
	public String addPre(Model model){
		return "rank/rankConsToBook/add";
	}
	@RequestMapping("add")
	public String addOne(Model model,RankConsToBook rankOne){
		if(null==rankOne){
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "提交信息不能为空");
		}else{
			StringBuffer returnInfo = new StringBuffer();
			if(StringUtils.isBlank(rankOne.getUsername())){
				returnInfo.append("用户昵称不能为空|");
			}
			if(StringUtils.isBlank(rankOne.getCustId()+"")){
				returnInfo.append("用户ID不能为空|");
			}
//			if(StringUtils.isNumeric(rankOne.getCustId()+"")){
//				returnInfo.append("用户ID应该为纯数字|");
//			}
			if(StringUtils.isBlank(rankOne.getMediaId()+"")){
				returnInfo.append("作品ID不能为空|");
			}
			if(StringUtils.isBlank(rankOne.getUserImgUrl())){
				returnInfo.append("用户头像不能为空|");
			}
			if(StringUtils.isBlank(rankOne.getSaleId()+"")){
				returnInfo.append("销售主体ID为空|");
			}
			if(StringUtils.isBlank(rankOne.getMediaId()+"")){
				returnInfo.append("作品ID不能为空|");
			}
			if(StringUtils.isBlank(rankOne.getMediaName())){
				returnInfo.append("打赏书名不能为空|");
			}
			if(StringUtils.isBlank(rankOne.getCons()+"")){
				returnInfo.append("打赏金币不能为空|");
			}
			if(StringUtils.isBlank(rankOne.getType()+"")){
				returnInfo.append("榜单类型不能为空|");
			}
			//获取标识
			String code = rankOne.getChannel().toUpperCase();
			switch (rankOne.getType()) {
			case 1:
				code = code+"_DAY";
				break;
			case 2:
				code = code+"_WEEK";
				break;
			case 3:
				code = code+"_MONTH";
				break;
			case 4:
				code = code+"_TOTAL";
				break;	
			default:
				returnInfo.append("榜单类型不对，只能为1,2,3,4。|");
				break;
			}
			if(StringUtils.isBlank(rankOne.getType()+"")){
				returnInfo.append("频道和类型错误|");
			}
			if(returnInfo.length() > 0){
				model.addAttribute("successFlag", 1);
				model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>:"+returnInfo.toString().substring(0, returnInfo.length() -1));
			}else{
				rankOne.setCreationDate(new Date());
				//rankOne.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
				rankOne.setCode(code);
				rankOne.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName(): "system");
				rankService.save(rankOne);
				model.addAttribute("returnInfo", "<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
				model.addAttribute("successFlag", 0);
			}
		}
		model.addAttribute("rankOne", rankOne);
		model.addAttribute("lefttab", AppUtil.getRequest().getParameter("lefttab"));
		cacheApi.deleteRankConsToBookByCodeCache(rankOne.getCode());
		return "rank/rankConsToBook/add";
	}
	
	
	
	
	@RequestMapping("delete")
	public String deleteOne(Model model,String id,String code){
		if(StringUtils.isNotBlank(id)){
			Long idLong = Long.parseLong(id);
			RankConsToBook rankOne = new RankConsToBook();
			rankOne.setMediaEbookConsRanklistId(idLong);
			rankOne.setStatus(2);
			rankService.update(rankOne);
			cacheApi.deleteRankConsToBookByCodeCache(code);
		}

		return "redirect:list.go";
	}
}