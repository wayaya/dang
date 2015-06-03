/**
 * Description: OrderBaseController.java
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午3:28:41  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.controlller;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dangdang.digital.model.OrderDetailChapter;
import com.dangdang.digital.model.OrderMain;
import com.dangdang.digital.service.IOrderDetailChapterService;
import com.dangdang.digital.service.IOrderDetailService;
import com.dangdang.digital.service.IOrderMainService;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.vo.OrderQueryVo;

/**
 * Description: 订单基础controller
 * All Rights Reserved.
 * @version 1.0  2014年11月17日 下午3:28:41  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Controller
@RequestMapping("orderBase")
public class OrderBaseController extends BaseController {
	
	@Resource
	private IOrderMainService orderMainService;
	@Resource
	private IOrderDetailService orderDetailService;
	@Resource
	private IOrderDetailChapterService orderDetailChapterService;
	
	@RequestMapping("list")
	public String list(Model model,Query query,OrderQueryVo vo){
		model.addAttribute("pageFinder", orderMainService.findPageFinderByOrderQueryVo(vo, query));
		model.addAttribute("vo", vo);
		return "order/orderList";
	}
	
	@RequestMapping("detail")
	public String detail(Model model,Long orderId){
		String errorMsg = "";
		if(orderId != null){
			OrderMain orderMain = orderMainService.get(orderId);
			if(orderMain != null){
				orderMain.setOrderDetails(orderDetailService.findListWithDetailByOrderNo(orderMain.getOrderNo()));				
			}else{
				errorMsg += "[主订单信息不存在！]";
			}
			model.addAttribute("orderMain", orderMain);
		}else{
			errorMsg += "[订单id为null!]";
		}
		if(StringUtils.isNotBlank(errorMsg)){
			model.addAttribute("errorMsg", errorMsg);
		}
		return "order/detail";
	}
	
	@RequestMapping("detailChapter")
	public String detailChapter(Model model,OrderDetailChapter detailChapter){
		String errorMsg = "";
		if(detailChapter != null && detailChapter.getOrderDetailId() != null && detailChapter.getMediaId() != null){			
			model.addAttribute("detailChapters", orderDetailChapterService.findListByParamsObjs(detailChapter));
		}else{
			errorMsg += "[参数不全!]";
		}
		if(StringUtils.isNotBlank(errorMsg)){
			model.addAttribute("errorMsg", errorMsg);
		}
		return "order/detailChapter";
	}

}
