package com.dangdang.digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.model.BookshelfCategory;
import com.dangdang.digital.service.IBaseService;


/**
 * BookshelfCategory Manager.
 */
public interface IBookshelfCategoryService extends IBaseService<BookshelfCategory, Long> {}
