/**
 * Description: BookAuthorDaoImpl.java
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午2:45:46  by 代鹏（daipeng@dangdang.com）创建
 */
package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IBookAuthorDao;
import com.dangdang.digital.model.BookAuthor;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月30日 下午2:45:46  by 代鹏（daipeng@dangdang.com）创建
 */
@Repository
public class BookAuthorDaoImpl extends BaseDaoImpl<BookAuthor> implements IBookAuthorDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> queryDigestIdsByAuthorId(Long authorId) {
		return (List<Long>)getSqlSessionQueryTemplate().selectList("BookAuthorMapper.queryDigestIdsByAuthorId", map("authorId", authorId));
	}
	
}
