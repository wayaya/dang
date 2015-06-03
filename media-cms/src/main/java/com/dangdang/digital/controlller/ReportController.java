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
import com.dangdang.digital.constant.ReportTypeEnum;
import com.dangdang.digital.model.Report;
import com.dangdang.digital.service.IReportService;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

/**
 * 
 * Description: 举报 All Rights Reserved.
 * 
 * @version 1.0 2015年3月17日 下午9:19:36 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Controller
@RequestMapping("report")
public class ReportController extends BaseController {

	@Resource
	IReportService reportService;

	@Resource
	ICacheApi cacheApi;

	@RequestMapping(value = "/list")
	public String getLabel(String pageIndex, final Model model, final Report report) throws Exception {
		Query query = new Query();
		if (StringUtils.isNotBlank(pageIndex)) {
			query.setPage(Integer.parseInt(pageIndex));
		} else {
			query.setPage(1);
		}
		PageFinder<Report> pageFinder = reportService.findPageFinderObjs(report, query);
		if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
			for (Report reportNew : pageFinder.getData()) {
				if (StringUtils.isNotBlank(reportNew.getPlatform())
						&& PlatformSourceEnum.getBySource(reportNew.getPlatform()) != null) {
					reportNew.setPlatformName(PlatformSourceEnum.getBySource(reportNew.getPlatform()).getName());
				}

				if (StringUtils.isNotBlank(reportNew.getReportType())
						&& ReportTypeEnum.getByCode(reportNew.getReportType()) != null) {
					reportNew.setReportTypeName(ReportTypeEnum.getByCode(reportNew.getReportType()).getName());
				}

				if (reportNew.getMediaId() != null && cacheApi.getMediaBasicFromCache(reportNew.getMediaId()) != null) {
					reportNew.setMediaTitle(cacheApi.getMediaBasicFromCache(reportNew.getMediaId()).getTitle());
				}

				if (reportNew.getChapterId() != null
						&& cacheApi.getChapterBasicFromCache(reportNew.getChapterId()) != null) {
					reportNew.setChapterTitle(cacheApi.getChapterBasicFromCache(reportNew.getChapterId()).getTitle());

				}

				if (reportNew.getCustId() == null) {
					reportNew.setCustIdStr("未登录用户");
				} else {
					reportNew.setCustIdStr(String.valueOf(reportNew.getCustId()));
				}

			}
			model.addAttribute("pageFinder", pageFinder);
		}

		return "report/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam final long id) {
		this.reportService.deleteById(id);
		return "redirect:list.go";
	}

}
