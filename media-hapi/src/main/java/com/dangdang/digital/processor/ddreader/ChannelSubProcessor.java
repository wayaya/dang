package com.dangdang.digital.processor.ddreader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/***
 * 频道订阅接口
 * @author lvxiang
 * @date   2015年6月1日 下午1:00:06
 */
@Component("hapichannelSubprocessor")
public class ChannelSubProcessor extends BaseApiProcessor {
	//模拟订阅数
	private int subNumber =0;
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//用户token
		String token ="";
		//订阅频道编号
		String channelId="";
		//动作类型,订阅或 取消
		String opType="sub";//sub/canel
		//订阅数,客户端主动+1
		sender.success(response);
	}


}
