package com.dangdang.digital.controlller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ActivityTypeEnum;
import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.constant.OperateTargetTypeEnum;
import com.dangdang.digital.model.ActivityInfo;
import com.dangdang.digital.model.ActivitySale;
import com.dangdang.digital.model.ActivityType;
import com.dangdang.digital.model.ManagerOperateLog;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.model.MediaSale;
import com.dangdang.digital.service.IActivityInfoService;
import com.dangdang.digital.service.IActivitySaleService;
import com.dangdang.digital.service.IActivityTypeService;
import com.dangdang.digital.service.IManagerOperateLogService;
import com.dangdang.digital.service.IMediaSaleService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

@Controller
@RequestMapping("activity")
public class ActivityController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActivityController.class);

	@Resource
	IActivityTypeService activityTypeService;
	@Resource
	IActivityInfoService activityInfoService;
	@Resource
	IActivitySaleService activitySaleService;
	@Resource
	IMediaService mediaService;
	@Resource
	IMediaSaleService mediaSaleService;
	@Resource
	private ICacheApi cacheApi;
	@Resource
	private IManagerOperateLogService managerOperateLogService;

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月18日 下午3:17:46 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param page
	 * @param activityType
	 * @return
	 */
	@RequestMapping("/type/list")
	public String activityTypeList(Model model, String page,
			ActivityType activityType) {
		Query query = new Query();
		if (StringUtils.isNotBlank(page)) {
			query.setPage(Integer.parseInt(page));
		} else {
			query.setPage(1);
		}
		PageFinder<ActivityType> pageFinder = activityTypeService
				.findPageFinderObjs(activityType, query);
		if (pageFinder != null) {
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("activityType", activityType);
		return "activity/type/list";
	}

	@RequestMapping("/type/add")
	public String activityTypeAdd(Model model) {
		return "activity/type/add";
	}

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月18日 下午3:17:41 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param activityType
	 * @return
	 */
	@RequestMapping("/type/goadd")
	public String activityTypeGoAdd(Model model, ActivityType activityType) {
		if (activityType == null) {
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "提交信息不能为空");
		} else {
			StringBuffer returnInfo = new StringBuffer();
			if (StringUtils.isBlank(activityType.getActivityTypeCode())) {
				returnInfo.append("活动编码不能为空|");
			}
			if (StringUtils.isBlank(activityType.getActivityTypeName())) {
				returnInfo.append("活动名称不能为空|");
			}
			if (null == activityType.getIsPromptlyToAccount()) {
				returnInfo.append("是否实时到账必须选择|");
			}
			if (null == activityType.getStatus()) {
				returnInfo.append("是否有效必须选择|");
			}

			if (returnInfo.length() > 0) {
				model.addAttribute("successFlag", 1);
				model.addAttribute("returnInfo", "<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>:"+ returnInfo.toString().substring(0,returnInfo.length() - 1));
			} else {
				activityType.setCreationDate(new Date());
				activityType.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
				activityType.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
				activityType.setLastChangedDate(new Date());
				try {
					activityTypeService.insert(activityType);
				} catch (RuntimeException e) {
					// 插入操作日志
					managerOperateLogService
							.insertOperateLog(new ManagerOperateLog(
									null,
									Constans.MANAGER_OPERATE_RESULT_FAIL,
									OperateTargetTypeEnum.ACTIVITY_TYPE
											.getType(),
									null,
									UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
											.getCurrentUser().getLoginName()
											: "system", new Date(),
									"添加活动类型，添加内容:"
											+ JSON.toJSONString(activityType)));
					e.printStackTrace();
					throw e;
				}
				// 插入操作日志
				managerOperateLogService
						.insertOperateLog(new ManagerOperateLog(
								null,
								Constans.MANAGER_OPERATE_RESULT_SUCCESS,
								OperateTargetTypeEnum.ACTIVITY_TYPE.getType(),
								Long.valueOf(activityType.getActivityTypeId()
										+ ""),
								UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
										.getCurrentUser().getLoginName()
										: "system", new Date(), "添加活动类型，添加内容:"
										+ JSON.toJSONString(activityType)));
				model.addAttribute("returnInfo",
						"<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
				model.addAttribute("successFlag", 0);
			}
		}

		model.addAttribute("activityType", activityType);
		return "activity/type/add";
	}

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月18日 下午3:17:36 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param activityType
	 * @return
	 */
	@RequestMapping("/type/modify")
	public String activityTypeModify(Model model, ActivityType activityType) {
		StringBuffer returnInfo = new StringBuffer();
		if (activityType != null && activityType.getActivityTypeId() != null) {
			Integer activityTypeId = activityType.getActivityTypeId();
			activityType = activityTypeService.get(activityTypeId);
			model.addAttribute("activityType", activityType);
		} else {
			returnInfo.append("修改对象不能为空");
		}

		return "activity/type/modify";
	}

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月18日 下午3:17:27 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param activityType
	 * @return
	 */
	@RequestMapping("/type/gomodify")
	public String activityTypeGoModify(Model model, ActivityType activityType) {
		if (activityType == null || activityType.getActivityTypeId() == null) {
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "提交信息不能为空");
		} else {
			Integer activityTypeId = activityType.getActivityTypeId();
			ActivityType dataActivityType = activityTypeService
					.get(activityTypeId);
			String oldActivityTypeStr = JSON.toJSONString(dataActivityType);
			if (dataActivityType != null
					&& dataActivityType.getActivityTypeId() != null) {
				model.addAttribute("activityType", activityType);

				StringBuffer returnInfo = new StringBuffer();
				if (StringUtils.isBlank(activityType.getActivityTypeCode())) {
					returnInfo.append("活动编码不能为空|");
				}
				if (StringUtils.isBlank(activityType.getActivityTypeName())) {
					returnInfo.append("活动名称不能为空|");
				}
				if (null == activityType.getIsPromptlyToAccount()) {
					returnInfo.append("是否实时到账必须选择|");
				}
				if (null == activityType.getStatus()) {
					returnInfo.append("是否有效必须选择|");
				}

				if (returnInfo.length() > 0) {
					model.addAttribute("successFlag", 1);
					model.addAttribute(
							"returnInfo",
							"<img src='/images/wrong.jpg' style='width: 15px; padding-bottom: 0px;'>:"
									+ returnInfo.toString().substring(0,
											returnInfo.length() - 1));
				} else {
					dataActivityType
							.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
									.getCurrentUser().getLoginName() : "system");
					dataActivityType.setLastChangedDate(new Date());
					dataActivityType.setActivityTypeCode(activityType
							.getActivityTypeCode());
					dataActivityType.setActivityTypeName(activityType
							.getActivityTypeName());
					dataActivityType.setIsPromptlyToAccount(activityType
							.getIsPromptlyToAccount());
					dataActivityType.setStatus(activityType.getStatus());
					try {
						activityTypeService.update(dataActivityType);
					} catch (RuntimeException e) {
						e.printStackTrace();
						// 插入操作日志
						managerOperateLogService
								.insertOperateLog(new ManagerOperateLog(
										null,
										Constans.MANAGER_OPERATE_RESULT_FAIL,
										OperateTargetTypeEnum.ACTIVITY_TYPE
												.getType(),
										Long.valueOf(dataActivityType
												.getActivityTypeId() + ""),
										UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
												.getCurrentUser()
												.getLoginName() : "system",
										new Date(),
										"修改活动类型，修改前内容："
												+ oldActivityTypeStr
												+ "，修改后内容："
												+ JSON.toJSONString(dataActivityType)));
						throw e;
					}
					// 插入操作日志
					managerOperateLogService
							.insertOperateLog(new ManagerOperateLog(
									null,
									Constans.MANAGER_OPERATE_RESULT_SUCCESS,
									OperateTargetTypeEnum.ACTIVITY_TYPE
											.getType(),
									Long.valueOf(dataActivityType
											.getActivityTypeId() + ""),
									UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
											.getCurrentUser().getLoginName()
											: "system",
									new Date(),
									"修改活动类型，修改前内容："
											+ oldActivityTypeStr
											+ "，修改后内容："
											+ JSON.toJSONString(dataActivityType)));
					model.addAttribute("returnInfo",
							"<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
					model.addAttribute("successFlag", 0);
				}
			} else {
				model.addAttribute("successFlag", 1);
				model.addAttribute("returnInfo", "该ID找到对应的对象");
			}
		}

		model.addAttribute("activityType", activityType);
		return "activity/type/modify";
	}

	@RequestMapping("/info/list")
	public String activityInfoList(Model model, String page,
			ActivityInfo activityInfo) {
		List<ActivityType> activityTypeList = activityTypeService.findListByParams("status", 1);
		model.addAttribute("activityTypeList", activityTypeList);
		Query query = new Query();
		if (StringUtils.isNotBlank(page)) {
			query.setPage(Integer.parseInt(page));
		} else {
			query.setPage(1);
		}
		PageFinder<ActivityInfo> pageFinder = activityInfoService.findPageFinderObjs(activityInfo, query);
		if (pageFinder != null) {
			model.addAttribute("pageFinder", pageFinder);
		}
		model.addAttribute("activityInfo", activityInfo);
		return "activity/info/list";
	}

	@RequestMapping("/info/add")
	public String activityInfoAdd(Model model) {
		List<ActivityType> activityTypeList = activityTypeService.findListByParams("status", 1);
		model.addAttribute("activityTypeList", activityTypeList);
		return "activity/info/add";
	}

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月20日 下午7:57:24 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param activityType
	 * @return
	 */
	@RequestMapping("/info/goadd")
	public String activityInfoGoAdd(Model model, ActivityInfo activityInfo) {
		List<ActivityType> activityTypeList = activityTypeService
				.findListByParams("status", 1);
		model.addAttribute("activityTypeList", activityTypeList);
		if (activityInfo == null) {
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "提交信息不能为空");
		} else {
			if (StringUtils.isNotBlank(activityInfo.getActivityTypeCode())) {
				activityInfo.setActivityTypeId(Integer.parseInt(activityInfo.getActivityTypeCode().split("_")[0]));
				activityInfo.setActivityTypeCode(activityInfo.getActivityTypeCode().split("_")[1]);
			}
			activityInfo.setCreationDate(new Date());
			activityInfo.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
			activityInfo.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
			activityInfo.setLastChangedDate(new Date());
			try {
				activityInfoService.insert(activityInfo);
			} catch (RuntimeException e) {
				// 插入操作日志
				managerOperateLogService
						.insertOperateLog(new ManagerOperateLog(
								null,
								Constans.MANAGER_OPERATE_RESULT_FAIL,
								OperateTargetTypeEnum.ACTIVITY_INFO
										.getType(),
								null,
								UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
										.getCurrentUser().getLoginName()
										: "system", new Date(),
								"添加活动，添加内容:"
										+ JSON.toJSONString(activityInfo)));
				e.printStackTrace();
				throw e;
			}
			// 插入操作日志
			managerOperateLogService
					.insertOperateLog(new ManagerOperateLog(
							null,
							Constans.MANAGER_OPERATE_RESULT_SUCCESS,
							OperateTargetTypeEnum.ACTIVITY_INFO
									.getType(),
							Long.valueOf(activityInfo.getActivityId() + ""),
							UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
									.getCurrentUser().getLoginName()
									: "system", new Date(),
							"添加活动，添加内容:"
									+ JSON.toJSONString(activityInfo)));
			model.addAttribute("returnInfo","<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
			model.addAttribute("successFlag", 0);
		}

		model.addAttribute("activityInfo", activityInfo);
		return "activity/info/add";
	}

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月22日 下午4:13:58 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param activityInfo
	 * @return
	 */
	@RequestMapping("/info/modify")
	public String activityInfoModify(Model model, ActivityInfo activityInfo) {
		List<ActivityType> activityTypeList = activityTypeService
				.findListByParams("status", 1);
		model.addAttribute("activityTypeList", activityTypeList);
		StringBuffer returnInfo = new StringBuffer();
		if (activityInfo != null && activityInfo.getActivityId() != null) {
			Integer activityId = activityInfo.getActivityId();
			activityInfo = activityInfoService.get(activityId);
			if (activityInfo != null) {
				if (activityInfo.getStartTime() != null) {
					activityInfo.setStartTimeString(DateUtil.format(activityInfo.getStartTime(),DateUtil.DATE_PATTERN));
				}
				if (activityInfo.getEndTime() != null) {
					activityInfo.setEndTimeString(DateUtil.format(activityInfo.getEndTime(), DateUtil.DATE_PATTERN));
				}
			}
			model.addAttribute("activityInfo", activityInfo);
		} else {
			returnInfo.append("修改对象不能为空");
		}

		return "activity/info/modify";
	}

	/**
	 * Description:
	 * 
	 * @Version1.0 2014年11月22日 上午11:20:07 by
	 *             wang.zhiwei（wangzhiwei@dangdang.com）创建
	 * @param model
	 * @param activityInfo
	 * @return
	 */
	@RequestMapping("/info/gomodify")
	public String activityInfoGoModify(Model model, ActivityInfo activityInfo) {
		List<ActivityType> activityTypeList = activityTypeService
				.findListByParams("status", 1);
		model.addAttribute("activityTypeList", activityTypeList);
		if (activityInfo == null || activityInfo.getActivityId() == null) {
			model.addAttribute("successFlag", 1);
			model.addAttribute("returnInfo", "提交信息不能为空");
		} else {
			Integer activityId = activityInfo.getActivityId();
			ActivityInfo dataActivityInfo = activityInfoService.get(activityId);
			String oldActivityInfoStr = JSON.toJSONString(dataActivityInfo);
			if (dataActivityInfo != null && dataActivityInfo.getActivityTypeId() != null) {
				if (StringUtils.isNotBlank(activityInfo.getActivityTypeCode())) {
					activityInfo.setActivityTypeId(Integer .parseInt(activityInfo.getActivityTypeCode().split("_")[0]));
					activityInfo.setActivityTypeCode(activityInfo.getActivityTypeCode().split("_")[1]);
				}
				activityInfo.setLastChangedDate(new Date());
				activityInfo.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils.getCurrentUser().getLoginName() : "system");
				activityInfo.setActivityId(dataActivityInfo.getActivityId());
				try {
					activityInfoService.update(activityInfo);
				} catch (RuntimeException e1) {
					// 插入操作日志
					managerOperateLogService
							.insertOperateLog(new ManagerOperateLog(
									null,
									Constans.MANAGER_OPERATE_RESULT_FAIL,
									OperateTargetTypeEnum.ACTIVITY_INFO
											.getType(),
									Long.valueOf(activityInfo
											.getActivityId() + ""),
									UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
											.getCurrentUser()
											.getLoginName() : "system",
									new Date(),
									"修改活动，修改前内容："
											+ oldActivityInfoStr
											+ "，修改后内容："
											+ JSON.toJSONString(activityInfo)));
					e1.printStackTrace();
					throw e1;
				}
				// 插入操作日志
				managerOperateLogService
						.insertOperateLog(new ManagerOperateLog(
								null,
								Constans.MANAGER_OPERATE_RESULT_SUCCESS,
								OperateTargetTypeEnum.ACTIVITY_INFO
										.getType(),
								Long.valueOf(activityInfo
										.getActivityId() + ""),
								UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
										.getCurrentUser()
										.getLoginName() : "system",
								new Date(),
								"修改活动，修改前内容："
										+ oldActivityInfoStr
										+ "，修改后内容："
										+ JSON.toJSONString(activityInfo)));
				model.addAttribute("returnInfo","<img src='/images/right.jpg' style='width: 15px; padding-bottom: 0px;'>");
				model.addAttribute("successFlag", 0);
				// 刷新缓存
				try {
					cacheApi.refreshActivityCache(activityId);
				} catch (Exception e) {
					LogUtil.error(LOGGER, e, "刷新活动缓存失败！activityId：{0}", activityId);
				}
			} else {
				model.addAttribute("successFlag", 1);
				model.addAttribute("returnInfo", "该ID找到对应的对象");
			}
		}

		model.addAttribute("activityType", activityInfo);
		return "activity/info/modify";
	}

	@RequestMapping("/info/manageproduct")
	public String activityInfoManageProduct(Model model, String page,
			Integer activityId, ActivitySale activitySale) {
		StringBuffer returnInfo = new StringBuffer();
		if (activityId != null) {
			ActivityInfo activityInfo = activityInfoService.get(activityId);
			if (activityInfo != null) {
				if (activityInfo.getStartTime() != null) {
					activityInfo.setStartTimeString(DateUtil.format(activityInfo.getStartTime(), DateUtil.DATE_PATTERN));
				}
				if (activityInfo.getEndTime() != null) {
					activityInfo.setEndTimeString(DateUtil.format(activityInfo.getEndTime(), DateUtil.DATE_PATTERN));
				}
			}

			Query query = new Query();
			if (StringUtils.isNotBlank(page)) {
				query.setPage(Integer.parseInt(page));
			} else {
				query.setPage(1);
			}
			activitySale.setActivityId(activityId);
			PageFinder<ActivitySale> pageFinder = activitySaleService.findPageFinderObjs(activitySale, query);
			if (pageFinder != null) {
				model.addAttribute("pageFinder", pageFinder);

				List<Long> saleIdList = new ArrayList<Long>();

				if (pageFinder.getData() != null
						&& pageFinder.getData().size() > 0) {
					for (ActivitySale activitySale2 : pageFinder.getData()) {
						if (!saleIdList.contains(activitySale2.getSaleId())) {
							saleIdList.add(activitySale2.getSaleId());
						}
					}
				}

				List<MediaSale> mediaSales = new ArrayList<MediaSale>();
				if (saleIdList.size() > 0) {
					mediaSales = mediaSaleService.findByIds(saleIdList);
				}

				if (mediaSales.size() > 0) {
					for (ActivitySale activitySale2 : pageFinder.getData()) {
						for (MediaSale mediaSale : mediaSales) {
							if (mediaSale.getSaleId().longValue() == activitySale2
									.getSaleId().longValue()) {
								activitySale2.setSaleName(mediaSale.getName());
								activitySale2.setShelfStatus(mediaSale.getShelfStatus());
							}
						}
					}
				}
			}

			model.addAttribute("activityInfo", activityInfo);
		} else {
			returnInfo.append("修改对象不能为空");
		}

		return "activity/info/manage_product";
	}

	@RequestMapping("/info/manageproductimportexcel")
	@ResponseBody
	public void activityInfoManageProductExcel(Integer activityIdExcel,
			String useDefaultPrice, @RequestParam MultipartFile fileUpload) {
		Workbook workBook = null;
		try {
			workBook = new HSSFWorkbook(fileUpload.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workBook.getSheetAt(0);
		final int lastRow = sheet.getLastRowNum();
		List<ActivitySale> activitySales = new ArrayList<ActivitySale>();
		ActivityInfo activityInfo = activityInfoService.get(activityIdExcel);
		StringBuffer repeatActivitySalesNames = new StringBuffer();
		if (activityInfo != null) {
			// 需要更新缓存的ActivitySale的saleId
			List<Long> saleIdList = new ArrayList<Long>();
			// 第一行表头
			try {
				for (int i = 1; i <= lastRow; i++) {
					Row row = sheet.getRow(i);
					String saleId = getCellValue(row.getCell(0));
					String startTime = getCellValue(row.getCell(1));
					String endTime = getCellValue(row.getCell(2));
					String status = getCellValue(row.getCell(3));
					String price = getCellValue(row.getCell(4));
					ActivitySale activitySale = new ActivitySale();
					MediaSale mediaSale = mediaSaleService.get(Long.parseLong(saleId.trim()));
					Media media = mediaService.findMasterUniqueByParams("saleId", Long.parseLong(saleId.trim()));

					if(activityInfo.getActivityTypeId().intValue() == ActivityTypeEnum.A_PRICE.getKey() && media.getIsSupportFullBuy().intValue() == 0){
						repeatActivitySalesNames.append("|"+ saleId+ "-该销售实体不支持全本购买<br>");
					}else{
						if (mediaSale != null && mediaSale.getSaleId().intValue() > 0 && media != null && media.getMediaId().intValue() > 0) {
							if(mediaSale.getShelfStatus().intValue() == 1 && media.getShelfStatus().intValue() == 1){
								List<ActivitySale> activitySalesList = activitySaleService.findListByParams("saleId", saleId);
								if (activitySalesList != null && activitySalesList.size() > 0) {
									for (ActivitySale activitySale2 : activitySalesList) {
										if (activitySale2.getActivityId().intValue() != activityIdExcel.intValue()) {
											repeatActivitySalesNames.append("|"+ activitySale2.getSaleId()+ "-该销售实体已经在其他限免或者一口价活动中存在<br>");
										} else if (activitySale2.getActivityId().intValue() == activityIdExcel.intValue()) {
											activitySale2.setStartTime(DateUtil.getDateByFormat(startTime,DateUtil.DATE_PATTERN));
											activitySale2.setEndTime(DateUtil.getDateByFormat(endTime,DateUtil.DATE_PATTERN));
											activitySale2.setStatus(Integer.parseInt(status));
											activitySale2.setSalePrice(StringUtils.isNotBlank(useDefaultPrice) ? activityInfo.getFixedPrice() : Long.parseLong(price));
											activitySaleService.update(activitySale2);
											saleIdList.add(activitySale2.getSaleId());
										}
									}
								} else {
									activitySale.setActivityId(activityIdExcel);
									activitySale.setSaleId(Long.parseLong(saleId));
									activitySale.setSaleName(mediaSale.getName());
									activitySale.setStartTime(DateUtil.getDateByFormat(startTime, DateUtil.DATE_PATTERN));
									activitySale.setEndTime(DateUtil.getDateByFormat(endTime, DateUtil.DATE_PATTERN));
									activitySale.setStatus(Integer.parseInt(status));
									activitySale.setSort(0);
									activitySale.setSalePrice(StringUtils.isNotBlank(useDefaultPrice) ? activityInfo.getFixedPrice() : Long.parseLong(price));
									activitySales.add(activitySale);
								}
							}else{
								repeatActivitySalesNames.append("|" + saleId+ "-销售实体下架或者作品下架<br>");
							}
						} else {
							repeatActivitySalesNames.append("|" + saleId+ "-不存在<br>");
						}
					}
				}
			} finally {
				if(!CollectionUtils.isEmpty(saleIdList)){
					try {
						cacheApi.mDeleteActivitySaleCacheBySaleId(saleIdList);
					} catch (Exception e) {
						LogUtil.error(LOGGER, e, "批量更新缓存失败！");
					}
				}
				
				try {
					cacheApi.cleanCacheByKey(Constans.HOT_FREE_CACHE_KEY+activityIdExcel);
				} catch (Exception e) {
					LogUtil.error(LOGGER, e, "更新限免或一口价活动商品缓存失败！activityId："+activityIdExcel);
				}
			}

			if (activitySales.size() > 0) {
				activitySaleService.batchInsert(activitySales);
			}

			try {
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				JSONObject json = new JSONObject();

				if (repeatActivitySalesNames.length() > 0) {
					json.put("flag", "1");
					json.put("message", repeatActivitySalesNames.toString()
							.replace("|", ""));
					response.getWriter().write(json.toString());
				} else {
					json.put("flag", "0");
					response.getWriter().write(json.toString());
				}
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case 0:
			String value = String.valueOf(cell.getNumericCellValue());
			int dot = value.indexOf(".");
			if (dot != -1) {
				return value.substring(0, dot);
			} else {
				return value;
			}
		case 1:
			return cell.getStringCellValue();
		default:
			return cell.getStringCellValue();
		}
	}

	@RequestMapping("/info/manageproductexportexcel")
	public void batchExportActivitySale(ActivitySale activitySale,
			HttpServletRequest request, HttpServletResponse response) {
		ActivityInfo activityInfo = activityInfoService.get(activitySale
				.getActivityId());
		try {
			List<ActivitySale> activitySales = new ArrayList<ActivitySale>();
			List<Integer> idList = new ArrayList<Integer>();
			if (StringUtils.isNotBlank(activitySale.getIds())) {
				String[] idsSplit = activitySale.getIds().split("\\|");
				for (String id : idsSplit) {
					idList.add(Integer.parseInt(id));
				}

				if (idList.size() > 0) {
					activitySales = activitySaleService.findByIds(idList);
				}
			} else {
				activitySales = activitySaleService
						.findListByParamsObjs(activitySale);
			}

			XSSFWorkbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet(activityInfo.getActivityName());
			Row titleRow = sheet.createRow(0);
			String title[] = { "销售主体ID", "开始时间", "结束时间", "状态", "价格" };
			for (int i = 0; i < title.length; i++) {
				titleRow.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(
						title[i]);
			}
			int i = 0;
			for (ActivitySale activitySaleT : activitySales) {
				Row currentRow = sheet.createRow(i + 1);
				currentRow.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(
						activitySaleT.getSaleId());
				currentRow.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(
						DateUtil.format(activitySaleT.getStartTime(),
								DateUtil.DATE_PATTERN));
				currentRow.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(
						DateUtil.format(activitySaleT.getEndTime(),
								DateUtil.DATE_PATTERN));
				currentRow.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(
						activitySaleT.getStatus());
				currentRow.createCell(4, Cell.CELL_TYPE_STRING).setCellValue(
						activitySaleT.getSalePrice());
				i++;
			}
			LogUtil.info(LOGGER, "管理员:{0}在导出一口价商品【{1}】", UsercmsUtils
					.getCurrentUser().getLoginName(), activityInfo
					.getActivityName());
			response.reset();
			response.addHeader(
					"Content-Disposition",
					"attachment;filename="
							+ URLEncoder.encode(
									DateUtil.format(new Date(),
											"yyyy-MM-dd_hh_mm_ss") + ".xlsx",
									"UTF-8"));
			response.setContentType("application/x-msdownload");
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			wb.write(toClient);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "管理员:{0}在导出一口价商品出现异常", UsercmsUtils
					.getCurrentUser().getLoginName());
		}
	}

	@ResponseBody
	@RequestMapping("/info/deleteactivitysale")
	public void deleteActivitySale(HttpServletRequest request,
			HttpServletResponse response) {
		String activitySaleId = request.getParameter("activitySaleId");
		if (StringUtils.isNotBlank(activitySaleId)) {
			ActivitySale activitySale = activitySaleService.get(Integer
					.parseInt(activitySaleId));
			if (activitySale != null) {
				Integer deleteCount = activitySaleService.deleteById(Integer
						.parseInt(activitySaleId));
				if (deleteCount.intValue() > 0) {
					LogUtil.info(LOGGER, "管理员:{0}删除一口价商品【{1}】成功", UsercmsUtils
							.getCurrentUser().getLoginName(), activitySaleId);
					try {
						cacheApi.deleteActivitySaleCacheBySaleId(activitySale.getSaleId());
					} catch (Exception e) {
						LogUtil.error(LOGGER, e, "更新缓存失败！activitySaleId：{0}",
								activitySaleId);
					}
					try {
						cacheApi.cleanCacheByKey(Constans.HOT_FREE_CACHE_KEY+activitySale.getActivityId());
					} catch (Exception e) {
						LogUtil.error(LOGGER, e, "更新限免或一口价活动商品缓存失败！activityId："+activitySale.getActivityId());
					}
				} else {
					LogUtil.info(LOGGER,"管理员:{0}删除一口价商品【{1}】失败",UsercmsUtils.getCurrentUser().getLoginName(),activitySale.getSaleId() + ":"+ activitySale.getSaleName());
				}
			}
			
			
		}
	}

	@ResponseBody
	@RequestMapping("/info/updateactivitysale")
	public void updateActivitySale(HttpServletRequest request,
			HttpServletResponse response) {
		String activitySaleId = request.getParameter("activitySaleId");
		String status = request.getParameter("status");
		if (StringUtils.isNotBlank(activitySaleId)
				&& StringUtils.isNotBlank(status)) {
			ActivitySale activitySale = activitySaleService.get(Integer
					.parseInt(activitySaleId));
			if (activitySale != null) {
				activitySale.setStatus(Integer.parseInt(status));
				Integer updateCount = activitySaleService.update(activitySale);
				if (updateCount.intValue() > 0) {
					LogUtil.info(LOGGER, "管理员:{0}修改一口价商品【{1}】状态成功",UsercmsUtils.getCurrentUser().getLoginName(),activitySaleId);
					try {
						cacheApi.deleteActivitySaleCacheBySaleId(activitySale.getSaleId());
					} catch (Exception e) {
						LogUtil.error(LOGGER, e, "更新缓存失败！activitySaleId：{0}",activitySaleId);
					}
				} else {
					LogUtil.info(LOGGER,"管理员:{0}修改一口价商品【{1}】状态失败",UsercmsUtils.getCurrentUser().getLoginName(), activitySale.getSaleId() + ":" + activitySale.getSaleName());
				}
				try {
					cacheApi.cleanCacheByKey(Constans.HOT_FREE_CACHE_KEY+activitySale.getActivityId());
				} catch (Exception e) {
					LogUtil.error(LOGGER, e, "更新限免或一口价活动商品缓存失败！activityId："+activitySale.getActivityId());
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/info/updateactivitysalesort")
	public void updateActivitySaleSort(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String activitySaleIds = request.getParameter("activitySaleIds");
			if(StringUtils.isNotBlank(activitySaleIds)){
				String[] activitySaleInfos = activitySaleIds.split("\\|");
				if(activitySaleInfos.length > 0){
					Integer activityId = null;
					for(String activitySaleInfo : activitySaleInfos){
						String[] activitySaleInfoIdSort = activitySaleInfo.split("::");
						if(activitySaleInfoIdSort.length == 2){
							Integer activitySaleId = Integer.parseInt(activitySaleInfoIdSort[0]);
							Integer activitySaleSort = Integer.parseInt(activitySaleInfoIdSort[1]);
							
							ActivitySale activitySale = activitySaleService.get(activitySaleId);
							activitySale.setSort(activitySaleSort);
							activitySaleService.update(activitySale);
							
							activityId = activitySale.getActivityId();
						}
					}
					
					LogUtil.info(LOGGER, "管理员:{0}更新一口价商品排序成功{1}", UsercmsUtils.getCurrentUser().getLoginName(), activitySaleIds);
					
					json.put("flag", "1");
					
					if(activityId != null && activityId.intValue() > 0){
						try {
							cacheApi.cleanCacheByKey(Constans.HOT_FREE_CACHE_KEY + activityId);
						} catch (Exception e) {
							LogUtil.error(LOGGER, e, "更新限免或一口价活动商品缓存失败！activityId：" + activityId);
						}
					}
				}
			}else{
				json.put("flag", "2");
				LogUtil.info(LOGGER,"管理员:{0}修改更新一口价商品排序失败:{1}",UsercmsUtils.getCurrentUser().getLoginName(),activitySaleIds);
			}
		} catch (Exception e) {
			json.put("flag", "2");
			LogUtil.info(LOGGER,"管理员:{0}修改更新一口价商品排序失败:{1}",UsercmsUtils.getCurrentUser().getLoginName(), e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try {
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}