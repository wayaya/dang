package com.dangdang.digital.processor.discovery.nearby;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.BookshelfCategory;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IBookshelfBookService;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;
/**
 * 
 * Description: 下载书架信息
 * All Rights Reserved.
 * @version 1.0  2014年7月16日 下午2:51:28  by 林永奇（linyongqi@dangdang.com）创建
 */
@Component("mobiledownloadBookListprocessor")
public class DownloadBookListProcessor extends BaseApiProcessor {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(UploadBookListProcessor.class);
    @Autowired
	private IBookshelfBookService service;
    @Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
        String uId=request.getParameter("uId");
        String token=request.getParameter("token");
        checkParams(token, uId);   
        //未登录用户直接返回
  		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(token);
  		if (custVo == null || custVo.getCustId() == null) {
  			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
  			return;
  		}

        LOG.info("DownloadBookListProcessor:uId->{0}",uId);
        List<BookshelfCategory> categorys=service.searchBookList(Long.parseLong(uId));
        sender.put("bookList", categorys);
        sender.send(response);
    }
}
