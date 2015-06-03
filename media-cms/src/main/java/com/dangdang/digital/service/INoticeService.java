package com.dangdang.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.Notice;
import com.dangdang.digital.service.IBaseService;


/**
 * Notice Manager.
 */
public interface INoticeService extends IBaseService<Notice, Long> {}
