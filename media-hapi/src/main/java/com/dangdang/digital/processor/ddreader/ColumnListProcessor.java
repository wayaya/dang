package com.dangdang.digital.processor.ddreader;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.mock.model.Channel;
import com.dangdang.digital.mock.model.Column;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午6:54:48
 * 书一级分类列表
 */
@Component("hapicolumnListprocessor")
public class ColumnListProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		/** 栏目类型 频道栏目还是书栏目  **/
		
		
		String columnCode ="" ;
		Column  column = new Column();
		column.setTitle("栏目标识");
		column.setType(1);//栏目类型,1:channel,2:book
		column.setColumnCode("channel_test");
		column.setDescription("如果是频道栏目,则显示频道列表channelList,如果是书栏目则直接显示书列表books");
		//如果是频道栏目,则显示频道列表,如果是书栏目则直接显示书列表
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
		channel2.setDescription("我是一个动漫爱好者");
		channelList.add(channel2);
		
		Channel channel3 = new Channel();
		channel3.setChannelId(3L);
		channel3.setTitle("白云的书单");
		channel3.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		channel3.setSubNumber(3);
		channel3.setDescription("我是一个白云爱好者");
		channelList.add(channel3);
		
		Channel channel4 = new Channel();
		channel4.setChannelId(4L);
		channel4.setTitle("乌云的书单");
		channel4.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		channel4.setSubNumber(4);
		channel4.setDescription("我是一个乌云爱好者");
		channelList.add(channel4);
		
		
		column.setTotal(channelList.size());
		sender.put("column", column);
		sender.put("channelList", channelList);
		sender.success(response);
	}

}
