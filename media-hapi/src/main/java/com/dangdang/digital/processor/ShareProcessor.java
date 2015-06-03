package com.dangdang.digital.processor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ActivityEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.service.IActivityUserService;
import com.dangdang.digital.service.IRedPacketService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ActivityVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * Description: 分享接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午5:17:41  by 周伟洋（zhouweiyang@dangdang.com）创建
 */
@Component("hapishareprocessor")
public class ShareProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShareProcessor.class);
	
	@Resource
	private IActivityUserService activityUserService;
	@Resource
	private IRedPacketService redPacketService;
	@Resource
	private ICacheApi cacheApi;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String token = request.getParameter("token");
		UserTradeBaseVo userVo = cacheApi.getUserWholeInfoByToken(token);
		if(null==userVo||!checkParams(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
			return;
		}
		try {
			//获取用户id
			Long custId = userVo.getCustId();
			//获取昵称
			String nickname = userVo.getNickname();
			if(!checkParams(nickname)||custId==0L||!checkParams(custId+"")){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
				return;
			}
			ActivityVo activityVo = new ActivityVo(); 
			activityVo.setCustId(Long.valueOf(custId));
			activityVo.setUsername(nickname);
			activityVo.setActivityType(ActivityEnum.SHARE_TYPE.getKey());
			String prizeResult = activityUserService.share(activityVo);
//			String redPacketResult = redPacketService.creatRedPacket(custId,DesUtils.decryptRedPacketId(recordId));
			sender.put("prize", prizeResult);
			
	/**此处又在去掉，拆分开来。。。。坑爹啊。。。。。。。。。。 */
//			if(!"0".equals(redPacketResult)&&null!=redPacketResult){
//				redPacketResult = DesUtils.encryptRedPacketId(Long.parseLong(redPacketResult));
//			}
//			sender.put("redPacket",redPacketResult);
			sender.success(response);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, e, "参数错误");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}	
	}
}
