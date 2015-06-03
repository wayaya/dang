package com.dangdang.digital.mock.model;

import java.io.Serializable;
import java.util.List;

/**
 * 频道详情
 * @author lvxiang
 * @date   2015年5月15日 下午2:52:04
 */
public class ChannelDetail implements Serializable{
	
	private Channel channel;
	
	private List<ChannelArticlePackage> articleContenPackagetList;
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public List<ChannelArticlePackage> getArticleContenPackagetList() {
		return articleContenPackagetList;
	}

	public void setArticleContentList(List<ChannelArticlePackage> articleContenPackagetList) {
		this.articleContenPackagetList = articleContenPackagetList;
	}


}