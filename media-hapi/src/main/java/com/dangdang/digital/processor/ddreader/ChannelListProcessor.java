package com.dangdang.digital.processor.ddreader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.mock.model.Channel;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

@Component("hapichannelListprocessor")
public class ChannelListProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		List<Channel> channelList = new ArrayList<Channel>();
		Channel channel1 = new Channel();
		channel1.setChannelId(1L);
		channel1.setTitle("你说空灵，我说心境");
		channel1.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		channel1.setSubNumber(999);
		channel1.setDescription("我知道风里的声音，时间并没有教会我");
		channelList.add(channel1);
		Channel channel2 = new Channel();
		channel2.setChannelId(2L);
		channel2.setTitle("黄云的书单");
		channel2.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		channel2.setSubNumber(0);
		channel2.setOwnder("中信出版社");
		channel2.setDescription("我是一个动漫爱好者");
		channelList.add(channel2);
		sender.put("channelList", channelList);
		sender.success(response);
	}

}
