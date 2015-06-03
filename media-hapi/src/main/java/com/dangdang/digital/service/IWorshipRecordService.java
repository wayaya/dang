package com.dangdang.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.model.WorshipRecord;
import com.dangdang.digital.service.IBaseService;


/**
 * WorshipRecord Manager.
 */
public interface IWorshipRecordService extends IBaseService<WorshipRecord, Long> {}
