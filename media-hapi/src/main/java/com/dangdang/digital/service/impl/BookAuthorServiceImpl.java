/**
 * Description: BookAuthorServiceImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午3:23:14  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBookAuthorDao;
import com.dangdang.digital.model.BookAuthor;
import com.dangdang.digital.service.IBookAuthorService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午3:23:14  by 代鹏（daipeng@dangdang.com）创建
 */
@Service("bookAuthorService")
public class BookAuthorServiceImpl extends BaseServiceImpl<BookAuthor, Long> implements IBookAuthorService {
	
	@Resource
	private IBookAuthorDao bookAuthorDao;
	
	@Override
	public IBaseDao<BookAuthor> getBaseDao() {
		return bookAuthorDao;
	}

	@Override
	public List<Long> queryDigestIdsByAuthorId(Long authorId) {
		return bookAuthorDao.queryDigestIdsByAuthorId(authorId);
	}

}
