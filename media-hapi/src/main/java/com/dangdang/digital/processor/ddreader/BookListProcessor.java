package com.dangdang.digital.processor.ddreader;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ReturnBookListVo;
/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午3:53:58
 * 书单接口
 */
@Component("hapibookListprocessor")
public class BookListProcessor extends BaseApiProcessor {
	@Resource
	ICacheApi  cacheApi; 
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		ReturnBookListVo bookList = new ReturnBookListVo();
		bookList.setBooklistId(123L);
		bookList.setChannelId(23123123L);
		bookList.setName("在路上");
		bookList.setDescription("一个夏天,小男孩经理了只为他一个人遥开的画展.....");
		bookList.setImageUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		bookList.setBookNumber(200);
		
		sender.put("bookList", bookList);
		sender.success(response);
	}

}
