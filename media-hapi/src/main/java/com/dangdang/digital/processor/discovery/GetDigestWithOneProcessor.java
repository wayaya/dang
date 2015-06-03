package com.dangdang.digital.processor.discovery;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.DigestDayOrNightEnum;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Digest;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IDigestService;
import com.dangdang.digital.utils.AuthorUtils;
import com.dangdang.digital.utils.BookUtil;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.FormatUtils;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.MediaCoverPicUrlUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.vo.RetrunDigestDetailVo;
import com.dangdang.digital.vo.RetrunDigestDetailVo.EbookInfo;
import com.dangdang.ebook.api.api.IEbookApi;
import com.dangdang.ebook.api.vo.EbookAllInfoVo;
import com.dangdang.ebook.api.vo.EbookInfoVo;

/**
 * Description: 一篇儿接口
 * All Rights Reserved.
 * @version 1.0  2015年4月13日 下午3:28:12  by 代鹏（daipeng@dangdang.com）创建
 */
@Component("hapigetDigestWithOneprocessor")
public class GetDigestWithOneProcessor extends BaseApiProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDigestWithOneProcessor.class);
	
	@Resource
	private IDigestService digestService;
	
	@Resource
	private ReturnBeanUtils returnBeanUtils;
	
	@Resource
	private IEbookApi iEbookApi;
	
	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
		try {
			//只取白天第一篇儿
			Long digestId = this.loopDigestIdWithOne(DigestDayOrNightEnum.DAY.getKey(), new Date());
			if(digestId == null){
				logger.info("loop digest id with one return null....");
				sender.fail(ErrorCodeEnum.ERROR_CODE_22001.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22001.getErrorMessage(), response);
				return;
			}
			Digest digest = digestService.findDigestById(digestId);
			if(digest == null){
				sender.fail(ErrorCodeEnum.ERROR_CODE_22001.getErrorCode(), ErrorCodeEnum.ERROR_CODE_22001.getErrorMessage(), response);
				return;
			}
			sender.put("digestDetail", this.buildDigestDetailVo(digest));
			sender.success(response);
			return;
		} catch (Exception e) {
			LogUtil.error(logger, e, "获取一篇儿失败...");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10000.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10000.getErrorMessage(), response);
			return;
		}
	}
	
	private Long loopDigestIdWithOne(Integer dayOrNight, Date now) throws Exception{
		String ymdDate = DateUtil.format(now, "yyyy-MM-dd");
		Long digestId = digestService.getDigestIdtWithOne(dayOrNight, DateUtil.getMinTimeByStringDate(ymdDate), now);
		if(digestId == null){
			int count = 0;
			while(true){
				count++;
				if(count > ConfigPropertieUtils.getInteger("digest.id.with.one.loop.depth", 7)){
					return null;
				}
				Date newDate = DateUtil.addDate(now, -count);
				String ymdFormat = DateUtil.format(newDate, "yyyy-MM-dd");
				Date start = DateUtil.getMinTimeByStringDate(ymdFormat);
				Date end = DateUtil.getMaxTimeByStringDate(ymdFormat);
				Long loopDigestId = digestService.getDigestIdtWithOne(dayOrNight, start, end);
				if(loopDigestId != null){
					return loopDigestId;
				}
			}
		}
		return digestId;
	}
	
	private RetrunDigestDetailVo buildDigestDetailVo(Digest digest){
		RetrunDigestDetailVo vo = new RetrunDigestDetailVo();
		//单品id
		Long mediaId = digest.getMediaId();
		vo.setDigestId(digest.getId());
		vo.setCardTitle(digest.getCardTitle());
		vo.setAuthorList(digest.getAuthorList());
		vo.setCardUrl("/media/api2.go?action=getDigestContentForH5&digestId=" + digest.getId());
		vo.setMediaId(mediaId);
		//获取单品基本信息
		try {
			EbookAllInfoVo ebookAllInfoVo = iEbookApi.ebookAllInfoVoByProductId(mediaId);
			if(ebookAllInfoVo != null){
				EbookInfoVo ebookInfoVo = ebookAllInfoVo.getEbookInfoVo();
				if(ebookInfoVo != null){
					EbookInfo ebookInfo = vo.getEbookInfo();
					String editorRecommend = FormatUtils.Html2Text(ebookAllInfoVo.getEditorRecommend());
					//编辑推荐语
					if(StringUtils.isNotBlank(editorRecommend)){
						ebookInfo.setEditorRecommend(editorRecommend);
					}else{//编辑推荐语为空时，使用摘要
						ebookInfo.setEditorRecommend(FormatUtils.Html2Text(ebookInfoVo.getDescn()));
					}
					ebookInfo.setProductId(mediaId);
					ebookInfo.setBookName(BookUtil.filterEbookForBookName(ebookInfoVo.getTitle()));
					ebookInfo.setBookAuthor(AuthorUtils.getAuthors(ebookInfoVo.getAuthor()));
					ebookInfo.setBookImgUrl(MediaCoverPicUrlUtil.K.getEpubUrl(mediaId));
					vo.setEbookInfo(ebookInfo);
				}
			}
		} catch (Exception e) {
			logger.info("获取精品对应单品基本信息失败, 单品mediaId:" + mediaId);
		}
		return vo;
	}
}
