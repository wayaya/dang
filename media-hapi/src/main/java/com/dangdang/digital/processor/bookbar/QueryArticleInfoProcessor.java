package com.dangdang.digital.processor.bookbar;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.base.bookbar.client.vo.ArticleVo;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 帖子详情接口
 * All Rights Reserved.
 * @version 1.0  2015-5-23 下午6:01:47  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapiqueryArticleInfoprocessor")
public class QueryArticleInfoProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//	articleId 帖子ID（必填）
		
		List<String> pictureList = new ArrayList<String>();
		pictureList.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		pictureList.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		pictureList.add( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		
		ArticleVo vo = new ArticleVo();
		vo.setCustId( 690999L);
		vo.setNickName( "渡边纯衣");
		vo.setLastModifiedDateStr( "2015-05-23 18:01:01");
		vo.setHeadPhoto( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		vo.setTitle( "这书写的是时间");
		vo.setContent( "一本书，时光深处的拾荒之旅，六百夜沉淀。<img>http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg</img>");
//		vo.setImgList( pictureList);
		
		sender.put( "article", vo);
		sender.send(response);
	}

}
