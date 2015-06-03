package com.dangdang.digital.processor.ddreader;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.mock.model.Channel;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * 频道基本接口
 * @author lvxiang
 * @date   2015年5月19日 上午11:40:45
 */
@Component("hapichannelprocessor")
public class ChannelProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		Channel channel = new Channel();
		channel.setChannelId(1L);
		channel.setBookListId(1L);
		channel.setHasArtical(0);
		channel.setTitle("你说空灵，我说心境");
		channel.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		channel.setSubNumber(999);
		channel.setDescription("我知道风里的声音，时间并没有教会我");
		channel.setOwnder("中信出版社");
		/** 频道基本信息 */
		sender.put("channel", channel);
		sender.success(response);
	}

}
