package com.dangdang.digital.service;


import java.util.List;

import com.dangdang.digital.model.AnnouncementsCategory;
import com.dangdang.digital.model.AnnouncementsContent;
import com.dangdang.digital.service.IBaseService;


/**
 * AnnouncementsContent Manager.
 */
public interface IAnnouncementsContentService extends IBaseService<AnnouncementsContent, Long> {
	
	List<AnnouncementsContent> getAll();

	
}
