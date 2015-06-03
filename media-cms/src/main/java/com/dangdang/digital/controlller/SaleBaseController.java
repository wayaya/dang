/**
 * Description: SaleBaseController.java
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午4:30:01  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.controlller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.constant.Constans;
import com.dangdang.digital.model.ConsumerConsume;
import com.dangdang.digital.model.Prop;
import com.dangdang.digital.service.IConsumerConsumeService;
import com.dangdang.digital.service.IPropService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.Query;
import com.dangdang.digital.utils.UsercmsUtils;

/**
 * Description: 销售管理controller
 * All Rights Reserved.
 * @version 1.0  2014年11月19日 下午4:30:01  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
@Controller
@RequestMapping("saleBase")
public class SaleBaseController extends BaseController {
	
	@Resource
	private IPropService propService;
	@Resource
	private IConsumerConsumeService consumerConsumeService;
	
	private String COVER_DIR_PATH = File.separator + "prop" + File.separator
			+ "coverPic" + File.separator;
	
	@RequestMapping("/prop/list")
	public String propList(Model model,Query query,Prop prop,String creationDateStart,String creationDateEnd){
		Map<String, Object> map = this.convertBeanToMap(prop);
		if(StringUtils.isNotBlank(creationDateStart)){
			map.put("creationDateStart", creationDateStart);
		}
		if(StringUtils.isNotBlank(creationDateEnd)){
			map.put("creationDateEnd", creationDateEnd);
		}				
		model.addAttribute("pageFinder",propService.findPageFinderObjs(map, query));
		model.addAttribute("vo",map);
		return "sale/prop/list";
	}
	
	@RequestMapping("/sale/list")
	public String list(Model model,Query query,ConsumerConsume consumerConsume,String creationDateStart,String creationDateEnd){
		Map<String, Object> map = this.convertBeanToMap(consumerConsume);
		if(StringUtils.isNotBlank(creationDateStart)){
			map.put("creationDateStart", creationDateStart);
		}
		if(StringUtils.isNotBlank(creationDateEnd)){
			map.put("creationDateEnd", creationDateEnd);
		}	
		model.addAttribute("pageFinder",consumerConsumeService.findPageFinderObjs(map, query));
		model.addAttribute("vo",map);
		return "sale/list";
	}
	
	@RequestMapping("/sale/detail")
	public String saleDetail(Model model,Long consumerConsumeId,Integer showFlag){
		model.addAttribute("consumerConsume",consumerConsumeService.findWithDetailByConsumerConsumeId(consumerConsumeId));
		return showFlag == null ? "sale/detail" : "sale/saleDetail";
	}
	
	@RequestMapping("/prop/detail")
	public String propDetail(Model model,Integer propId,String operateFlag){
		if(propId != null){
			model.addAttribute("prop",propService.get(propId));
			model.addAttribute("coverPath",ConfigPropertieUtils.getString("media.resource.http.path"));
		}		
		model.addAttribute("operateFlag",operateFlag);
		return "sale/prop/detail";
	}
	
	@RequestMapping("/prop/merge")
	public String propMerge(Model model, Prop prop, String operateFlag,
			@RequestParam(value = "cover", required = false) MultipartFile file)
			throws FileNotFoundException, IOException {
		String operateMsg = "";
		if (prop == null) {
			operateMsg += "[参数为空！]";
		} else {
			if (StringUtils.isBlank(prop.getPropName())) {
				operateMsg += "[道具名称为空！]";
			}
			if (prop.getPropPurposeId() == null || prop.getPropPurposeId().intValue() < 1) {
				operateMsg += "[道具功能id不合法！]";
			}
			if (prop.getPropMainGoldPrice() == null
					&& prop.getPropSubGoldPrice() == null) {
				operateMsg += "[道具主账户购买价格和副账户购买价格不可同时为空！]";
			}
			if (prop.getPropMainGoldPrice() != null && prop.getPropMainGoldPrice().intValue() < 1) {
				operateMsg += "[道具主账户购买价格不合法！]";
			}
			if (prop.getPropSubGoldPrice() != null && prop.getPropSubGoldPrice().intValue() < 1) {
				operateMsg += "[道具副账户购买价格不合法！]";
			}
		}
		if (StringUtils.isBlank(operateMsg)
				&& StringUtils.isNotBlank(operateFlag)) {
			int result = 0;
			if (file != null && file.getSize() > 0) {
				String path = "";
				if (prop.getCoverPic() == null || "".equals(prop.getCoverPic())) {
					path = COVER_DIR_PATH + System.currentTimeMillis()
							+ "_cover.jpg";
					prop.setCoverPic(path);
				} else {
					path = prop.getCoverPic();
				}
				File dir=new File(ConfigPropertieUtils.getString("media.resource.root.path") + COVER_DIR_PATH);
				if(!dir.exists()){
					dir.mkdirs();
				}
				File f = new File(
						ConfigPropertieUtils.getString("media.resource.root.path") + "/" + path);
				if (!f.exists()) {
					f.createNewFile();
				}
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(
						f));
			}
			if (operateFlag.equals(Constans.OPERATE_FLAG_UPDATE)
					&& prop.getPropId() != null) {
				prop.setLastModifiedDate(new Date());
				prop.setModifier(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
						.getCurrentUser().getLoginName() : "system");
				result = propService.update(prop);
			} else if (operateFlag.equals(Constans.OPERATE_FLAG_INSERT)
					&& prop.getPropId() == null) {
				prop.setCreationDate(new Date());
				prop.setCreator(UsercmsUtils.getCurrentUser() != null ? UsercmsUtils
						.getCurrentUser().getLoginName() : "system");
				result = propService.save(prop);
			}
			if (result == 0) {
				operateMsg += "[保存道具数据失败！]";
				model.addAttribute("operateMsg", operateMsg);
				model.addAttribute("prop", prop);
			} else {
				operateMsg += "ok";
				model.addAttribute("operateMsg", operateMsg);
			}
		} else {
			model.addAttribute("operateMsg", operateMsg);
			model.addAttribute("prop", prop);
		}
		model.addAttribute("operateFlag", operateFlag);
		return "sale/prop/detail";
	}

}
