package com.dangdang.digital.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IBookshelfBookDao;
import com.dangdang.digital.dao.IBookshelfCategoryDao;
import com.dangdang.digital.model.BookshelfBook;
import com.dangdang.digital.model.BookshelfCategory;
import com.dangdang.digital.service.IBookshelfBookService;
import com.dangdang.ebook.api.vo.ConciseEbookVo;
/**
 * BookshelfBook Manager.
 */
@Service
public class BookshelfBookServiceImpl extends BaseServiceImpl<BookshelfBook, Long> implements IBookshelfBookService {

	@Resource
	IBookshelfBookDao bookshelfBookDao;
	
	@Resource
	IBookshelfCategoryDao bookshelfCategoryDao;
	
	public IBaseDao<BookshelfBook> getBaseDao() {
		return bookshelfBookDao;
	}
	
	/**
	 * 
	 * Description: 插入书架分类以及批量图书
	 * 
	 * @Version1.0 2014年7月18日 下午7:34:08 by 林永奇（linyongqi@dangdang.com）创建
	 * @param categorys
	 */
	public void insertBookshelf(final List<BookshelfCategory> categorys,
			Long custId) {
		bookshelfBookDao.delete("BookshelfBookMapper.deleteByCustId", custId);
		bookshelfCategoryDao.delete("BookshelfCategoryMapper.deleteByCustId", custId);
		for (final BookshelfCategory category : categorys) {
			if (category.getBooks().size() == 0) {
				continue;
			}
			final List<BookshelfBook> books = category.getBooks();
			category.setCustId(custId);
			final int categoryId = bookshelfCategoryDao.insert("", category);
			bookshelfBookDao.insert("BookshelfBookMapper.insertSelective", books);
		}
	}

	/**
	 * 
	 * Description: 获取书架信息
	 * 
	 * @Version1.0 2014年7月18日 下午6:06:31 by 林永奇（linyongqi@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<BookshelfCategory> searchBookList(Long custId) {
		List<BookshelfCategory> categorys = bookshelfCategoryDao.selectList(
				"custId", custId);
//		if (CollectionUtils.isNotEmpty(categorys)) {
//			Iterator<BookshelfCategory> iterator = categorys.iterator();
//			while (iterator.hasNext()) {
//				BookshelfCategory category = iterator.next();
//				List<BookshelfBook> tmpBooks = new ArrayList<BookshelfBook>();
//				List<BookshelfBook> books = bookshelfBookDao.findBy(
//						"categoryId", category.getcId());
//				List<Long> productIdList = new ArrayList<Long>();
//				Map<Long, BookshelfBook> bookMap = new HashMap<Long, BookshelfBook>();
//				for (BookshelfBook book : books) {
//					productIdList.add(book.getProductId());
//					bookMap.put(book.getProductId(), book);
//				}
//
//				List<ConciseEbookVo> rmEbooks = iEbookApi
//						.conciseEbookVoListByProductIds(productIdList);
//				for (ConciseEbookVo vo : rmEbooks) {
//					BookshelfBook book = bookMap.get(vo.getProductId());
//					String uri = ProductImgUrlUtil.C.getUrl(vo.getProductId());
//					book.setCoverPic(uri);
//					book.setTitle(vo.getTitle());
//					tmpBooks.add(book);
//				}
//				if (CollectionUtils.isEmpty(tmpBooks)) {
//					iterator.remove();
//					continue;
//				}
//				category.setBooks(tmpBooks);
//			}
//		}
		return categorys;
	}

	/**
	 * 
	 * Description: 获取最近阅读的几本书
	 * 
	 * @Version1.0 2014年7月18日 下午8:30:22 by 林永奇（linyongqi@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<BookshelfBook> searchRecentlyBooks(Long custId, int bookLimit) {
//		List<BookshelfBook> books = bookshelfBookDao.searchRecentlyBooks(
//				custId, bookLimit);
		List<BookshelfBook> result = new ArrayList<BookshelfBook>();
//		List<Long> productIdList = new ArrayList<Long>();
//		for (BookshelfBook book : books) {
//			productIdList.add(book.getProductId());
//		}
//		List<ConciseEbookVo> rmEbooks = iEbookApi
//				.conciseEbookVoListByProductIds(productIdList);
//		for (Iterator<ConciseEbookVo> it = rmEbooks.iterator(); it.hasNext();) {
//			ConciseEbookVo book = it.next();
//			BookshelfBook bb = new BookshelfBook();
//			bb.setProductId(book.getProductId());
//			String uri = ProductImgUrlUtil.C.getUrl(book.getProductId());
//			bb.setCoverPic(uri);
//			bb.setTitle(book.getTitle());
//			result.add(bb);
//		}
		return result;
	}

	public Integer countUserBooks(Long custId) {
//		return bookshelfBookDao.countUserBooks(custId);
		return 0;
	}
}
