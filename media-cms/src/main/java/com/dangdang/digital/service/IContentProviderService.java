package com.dangdang.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.ContentProvider;
import com.dangdang.digital.service.IBaseService;


/**
 * ContentProvider Manager.
 */
public interface IContentProviderService extends IBaseService<ContentProvider, Integer> {}
