package com.dangdang.digital.processor.bookbar;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dangdang.base.bookbar.client.vo.ArticleVo;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 帖子列表接口
 * All Rights Reserved.
 * @version 1.0  2015-5-23 下午4:22:20  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapiqueryArticleListprocessor")
public class QueryArticleListProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//barId 吧ID（必填） 
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isBlank(pageNo)) {
			LogUtil.info(LOGGER, "参数：pageNo为空！");
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		int pageNoTemp = Integer.parseInt( pageNo);
		
		List<ArticleVo> result = new ArrayList<ArticleVo>();
		
		List<String> pictureList = new ArrayList<String>();
		pictureList.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		pictureList.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		pictureList.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		
		for ( int i = 0; i < 5; i++){
			ArticleVo vo = new ArticleVo();
			vo.setArticleId( 111111L);
			vo.setCustId( 8000001L);
			vo.setNickName( "皓哥");
			vo.setLastModifiedDateStr( "2015-05-23 16:55:23");
			vo.setHeadPhoto( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
			vo.setTitle( "第"+pageNoTemp+"页的帖子，这书写的是时间");
			vo.setContent( "一本书，时光深处的拾荒之旅，六百夜沉淀。");
			vo.setImgList( pictureList);
			vo.setPraiseNum( 8888);
			vo.setCommentNum( 6666);
			vo.setIsPraise( 1);
			result.add( vo);
		}
		
		
		sender.put( "articleList", result);
		sender.send(response);
	}

}
