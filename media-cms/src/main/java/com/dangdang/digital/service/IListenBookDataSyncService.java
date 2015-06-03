package com.dangdang.digital.service;

import com.dangdang.digital.listenbook.model.ListenBookMedia;

public interface IListenBookDataSyncService {
	
	public int saveMedia(ListenBookMedia lbMedia);
	
	public int saveMediaOfChapter(ListenBookMedia lbMedia);
	
	public int saveChapter(ListenBookMedia lbMedia);
	
	public int deleteMedia(ListenBookMedia lbMedia);
	
	public int updateChapter(ListenBookMedia lbMedia);
}
