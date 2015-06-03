package com.dangdang.digital.processor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.bookreview.api.IBookReviewApi;
import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.api.IUserAuthorityApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.constant.PlatformSourceEnum;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.service.IStoreUpService;
import com.dangdang.digital.service.IUserBehaviorService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnMediaFactory;
import com.dangdang.digital.utils.SafeConvert;
import com.dangdang.digital.vo.ReturnMediaBaseVo;
import com.dangdang.digital.vo.ReturnPaperMediaVo;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 通过productId获取纸书media信息接口 All Rights Reserved.
 * 
 * @version 1.0 2015年5月21日 下午6:28:57 by 魏嵩（weisong@dangdang.com）创建
 */
@Component("hapigetPaperMediaprocessor")
public class GetPaperMediaProcessor extends BaseApiProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetPaperMediaProcessor.class);

	@Resource
	private ICacheApi cacheApi;

	@Resource
	private IBookReviewApi bookReviewApi;

	@Resource
	IStoreUpService storeUpService;

	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;

	@Resource(name = "userAuthorityApi")
	IUserAuthorityApi userAuthorityApi;

	@Resource
	IUserBehaviorService userBehaviorService;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		// 入参： 销售主体id
		String productIdStr = request.getParameter("productId");
		// 入参：是否来源与搜索
		String fromSearch = request.getParameter("fromSearch");
		// 平台来源
		String platformSource = request.getParameter("platformSource");
		if (!checkParams(productIdStr, platformSource)) {
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
			
			// 入参： token
			String token = request.getParameter("token");
			Long custId = null;
			if (StringUtils.isNotBlank(token)) {
				UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
				if (custVo != null) {
					custId = custVo.getCustId();
				}
			}
			
			ReturnPaperMediaVo mediaBaseVo = new ReturnPaperMediaVo();
			
			mediaBaseVo.setAuthorHeadPic("http://img37.ddimg.cn/imghead/32/26/7658402885627-1_e.png?1432185399146");
			mediaBaseVo.setAuthorId(213L);
			mediaBaseVo.setAuthorIntroduction("作者曾任中国劳动关系学院教师。主业：政治经济学，偏重政治学方面。1967年自愿赴内蒙古额仑草原插队。1978年返城。");
			mediaBaseVo.setAuthorPenname("姜戎");
			//精装
			mediaBaseVo.setBookReviewCount(89L);
			mediaBaseVo.setCategoryIds("XHQH");
			mediaBaseVo.setCategorys("小说");
			mediaBaseVo.setChannelId(1000023L);
			mediaBaseVo.setCoverPic("http://a.hiphotos.baidu.com/baike/g%3D0%3Bw%3D268/sign=a4cada3496dda144ca0969b9c58ae294/a50f4bfbfbedab64ab5233e7f636afc379311ef0.jpg");
			mediaBaseVo.setDescs("《狼图腾》是一部以狼为叙述主体的小说，讲述了上个世纪六七十年代一位知青在内蒙古草原插队时与草原狼、游牧民族相依相存的故事。1971年起腹稿于内蒙古锡盟东乌珠穆沁草原，1997年初稿于北京，2003年岁末定稿于北京，2004年4月出版。该书在中国出版后，被译为30种语言，在全球110个国家和地区发行。截至2014年4月，在中国大陆再版150多次，正版发行近500万册，连续6年蝉联文学图书畅销榜的前十名，获得各种奖项几十余种。海内外报刊和网络新媒体对《狼图腾》的研究论文和论著有上千篇、种。");
			mediaBaseVo.setMediaType(3);
			mediaBaseVo.setRecommandWords("故事的背景发生在20世纪60年代末，中国大陆内蒙古最后一块靠近边境的原始草原。这里的蒙古牧民还保留着游牧民族的生态特点，他们自由而浪漫地在草原上放养着牛、羊，与成群的强悍的草原狼共同维护着草原的生态平衡。他们憎恨着狼――狼是侵犯他们家园的敌人；他们同时也敬畏着狼――草原狼帮助蒙古牧民猎杀着草原上不能够过多承载的食草动物：黄羊、兔子和大大小小的草原鼠。草原狼是蒙古民族的原始图腾。狼的凶悍、残忍、智慧和团队精神，狼的军事才能和组织分工，曾经是13世纪蒙古军队征战欧亚大陆的天然教官和进化的发动机。");
			mediaBaseVo.setScore(5F);
			mediaBaseVo.setSubTitle("");
			mediaBaseVo.setTitle("狼图腾");
			mediaBaseVo.setWordCnt(585858L);
				
			//精装
			mediaBaseVo.setBindingType(1);
			mediaBaseVo.setChannelId(1000023L);
			mediaBaseVo.setEbookSaleId(1980000221L);
			mediaBaseVo.setHasEbook(1);
			mediaBaseVo.setIsbn("SDIOWFLS-SDFOI123");
			mediaBaseVo.setMediaType(3);
			
//			mediaBaseVo.getOrderInfo().setProvince("北京市");
//			mediaBaseVo.getOrderInfo().setCity("北京市"); 
//			mediaBaseVo.getOrderInfo().setDistrict("朝阳区");
//			
//			mediaBaseVo.getOrderInfo().setPredictOrderTime(100000L);
//			mediaBaseVo.getOrderInfo().setPredictArrivalTime(1431302704000L);
//			mediaBaseVo.getOrderInfo().setPostalfee("5");
//			mediaBaseVo.getOrderInfo().setFreeLimit("58");
			
			
			mediaBaseVo.setOriginalPrice(1899L);
			
			mediaBaseVo.setPublisher("机械工业出版社");
			mediaBaseVo.setPrice(999L);
			mediaBaseVo.setProductId("52164589");
			mediaBaseVo.setPublishDate(1431302704000L);
			
			sender.put("media", mediaBaseVo);
			sender.success(response);
		} catch (NumberFormatException e) {
			LOGGER.error("参数错误,mediaIds:" + request.getParameter("mediaIds"), e);
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
		}
	}


}
