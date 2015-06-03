package com.dangdang.digital.processor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.constant.ReportTypeEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.Report;
import com.dangdang.digital.service.IReportService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ChapterCacheBasicVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 添加举报信息 All Rights Reserved.
 * 
 * @version 1.0 2015年1月28日 下午9:21:30 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapiaddReportprocessor")
public class AddReportProcessor extends BaseApiProcessor {

	@Resource
	IReportService reportService;
	@Resource
	ICacheApi cacheApi;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参：token
		String token = request.getParameter("token");
		// 入参：chapterId
		String chapterIdStr = request.getParameter("chapterId");
		// 举报类型
		String reportType = request.getParameter("reportType");
		// 平台来源
		String platformSource = request.getParameter("platformSource");
		// 具体说明 选填
		String reportContent = request.getParameter("reportContent");
		// 联系方式 选填
		String contactInfo = request.getParameter("contactInfo");

		if (!checkParams(reportType, chapterIdStr, platformSource)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 验证举报类型
		if (ReportTypeEnum.getByCode(reportType) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		// 验证平台来源
		if (PlatformSourceEnum.getBySource(platformSource) == null) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try {
			Long chapterId = Long.valueOf(chapterIdStr);
			ChapterCacheBasicVo chapter = cacheApi.getChapterBasicFromCache(chapterId);
			if (chapter == null) {
				sender.fail(ErrorCodeEnum.ERROR_CODE_12002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_12002.getErrorMessage(), response);
				return;
			}

			// 获取token
			UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
			Report report = new Report();
			report.setChapterId(chapterId);
			report.setMediaId(chapter.getMediaId());
			report.setCreateTime(new Date());
			report.setContactInfo(contactInfo);
			if (custVo != null) {
				report.setCustId(custVo.getCustId());
			}
			report.setPlatform(platformSource);
			report.setReportContent(reportContent);
			report.setReportType(reportType);
			reportService.save(report);

			sender.success(response);

		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}

	}
}
