package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.model.IWant;
import com.dangdang.digital.service.IIWantService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

/**
 * 
 * Description: 我想要 All Rights Reserved.
 * 
 * @version 1.0 2015年3月17日 下午7:34:57 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Controller
@RequestMapping("iwant")
public class IWantController extends BaseController {
	@Resource
	IIWantService iWantService;

	@Resource
	ICacheApi cacheApi;

	@RequestMapping(value = "/list")
	public String getLabel(String pageIndex, final Model model, final IWant iwant) {
		Query query = new Query();
		if (StringUtils.isNotBlank(pageIndex)) {
			query.setPage(Integer.parseInt(pageIndex));
		} else {
			query.setPage(1);
		}
		PageFinder<IWant> pageFinder = iWantService.findPageFinderObjs(iwant, query);

		if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
			for (IWant want : pageFinder.getData()) {
				if (StringUtils.isNotBlank(want.getPlatform())
						&& PlatformSourceEnum.getBySource(want.getPlatform()) != null) {
					want.setPlatformName(PlatformSourceEnum.getBySource(want.getPlatform()).getName());
				}
				if (want.getCustId() == null) {
					want.setCustIdStr("未登录用户");
				} else {
					want.setCustIdStr(String.valueOf(want.getCustId()));
				}

			}
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("iwant", iwant);
		return "iwant/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam final long id) {
		this.iWantService.deleteById(id);
		return "redirect:list.go";
	}

}
