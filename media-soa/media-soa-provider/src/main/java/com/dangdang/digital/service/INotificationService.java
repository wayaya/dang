package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.Notification;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface INotificationService extends IBaseService<Notification, Long>{

	List<Notification> getNotificationListByCustIdsAndDeviceType(Integer integer, List<Long> usingUserIds, List<Integer> deviceTypes);

	PageFinder<Notification> getPageFinderObjsByMutliChannel(Object params, Query query);
}
