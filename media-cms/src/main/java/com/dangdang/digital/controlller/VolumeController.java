package com.dangdang.digital.controlller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dangdang.digital.model.Volume;
import com.dangdang.digital.service.IVolumeService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.UsercmsUtils;

@Controller
@RequestMapping("volume")
public class VolumeController extends BaseController {

	@Resource
	public IVolumeService volumeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VolumeController.class);
	
	@RequestMapping("toEdit")
	public String toEdit(@RequestParam Long id,final Model model){
		Volume volume = volumeService.get(id);
		model.addAttribute("volume", volume);
		return "volume/modify";
	}
	
	@RequestMapping("update")
	public String update(Volume volume){
		try {
			volumeService.update(volume);
			LogUtil.info(LOGGER,"管理员:{0}在修改分卷【{1}】", UsercmsUtils.getCurrentUser().getLoginName(),
					volume.getVolumeId());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"管理员:{0}在修改分卷【{1}】失败",e, UsercmsUtils.getCurrentUser().getLoginName(),
					volume.getVolumeId());
		}
		
		return "redirect:/media/toChapterVolume.go?mediaId="+volume.getMediaId();
	}
}
