package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IDigestDao extends IBaseDao<Digest> {
	public List<Map<String, Object>> getSign();
	
	public List<Map<String, Object>> getSignByDigestId();
	
	/**
	 * 根据authorId获取文章
	 * Description: 
	 * @param query
	 * @param AuthorId
	 * @param isShow
	 * @return
	 */
	public PageFinder<Digest> findDigestByAuthorId(Query query, Long AuthorId, Integer isShow);
	
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds);
	
	public PageFinder<Digest> findDigestBySignId(Query query, Long signId, Integer isShow);
		
	public int saveDigestLable(DigestLable dl) ;
	
	/**
	 * Description: 保存精品章节
	 * @Version1.0 2015年1月21日 上午10:13:33 by 代鹏（daipeng@dangdang.com）创建
	 * @param record
	 * @return
	 */
	int insert(Digest record);
	
	/**
	 * Description: 更新精品章节
	 * @Version1.0 2015年1月21日 上午10:15:09 by 代鹏（daipeng@dangdang.com）创建
	 * @param record
	 * @return
	 */
	int update(Digest record);
	
	/**
	 * Description: 删除精品章节
	 * @Version1.0 2015年1月21日 上午10:15:24 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	int delete(Long id);
	
	/**
	 * Description: 根据主键查询精品章节
	 * @Version1.0 2015年1月28日 下午4:11:21 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	Digest findDigestById(Long id);
	
	/**
	 * 
	 * Description: 根据主键查询精品章节及内容
	 * @Version1.0 2015年6月1日 下午5:34:55 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param id
	 * @return
	 */
	Digest findDigestContentById(Long id);
	
	/**
	 * Description: 根据章节id批量获取章节列表, 并按照ids顺序排序且分页
	 * @Version1.0 2015年2月3日 下午2:42:21 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	List<Digest> queryDigestsByIdsAndSortByIds(Map<String, Object> paramObj);
	
	/**
	 * Description: 根据章节id批量获取章节列表
	 * @Version1.0 2015年1月21日 上午10:30:12 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	List<Digest> queryDigestsByIds(Map<String, Object> paramObj);
	
	/**
	 * Description: 根据心情类别查询章节列表
	 * @Version1.0 2015年1月21日 上午10:35:01 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	List<Digest> queryDigestsByMood(Map<String, Object> paramObj);
	
	/**
	 * Description: 精品首页列表查询
	 * @Version1.0 2015年1月26日 上午11:49:30 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	List<Digest> queryDigestsForHomePage(Map<String, Object> paramObj);
	
	/**
	 * Description: 取展示时间小于某时间值，且仅一条数据
	 * @Version1.0 2015年1月29日 下午2:31:17 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramObj
	 * @return
	 */
	Digest getDigestByShowEndDateLimitOne(Map<String, Object> paramObj);
	
	/**
	 * Description: 根据id取精品正文内容以及展示类型
	 * @Version1.0 2015年1月31日 下午2:51:49 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	Digest getDigestContentById(Long id);
	
	/**
	 * Description: 根据标签id查询对应的精品列表
	 * @Version1.0 2015年2月9日 下午4:59:24 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	List<Digest> queryDigestsBySignId(Map<String, Object> paramMap);
	
	/**
	 * Description: 根据作者id查询对应的精品列表
	 * @Version1.0 2015年2月9日 下午4:59:46 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	List<Digest> queryDigestsByAuthorId(Map<String, Object> paramMap);
	
	/**
	 * Description: 个人收藏精品列表页
	 * @Version1.0 2015年3月11日 下午3:40:00 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	List<Digest> queryDigestsByStoreUpAndCustId(Map<String, Object> paramMap);
	
	/**
	 * Description: 下拉获取最新的首页内容
	 * @Version1.0 2015年3月17日 下午3:33:04 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	List<Digest> queryDigestsHomePageNew(Map<String, Object> paramMap, String version);
	
	/**
	 * Description: 上拉获取首页旧内容
	 * @Version1.0 2015年3月17日 下午3:33:08 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	List<Digest> queryDigestsHomePageOld(Map<String, Object> paramMap);
	
	/**
	 * Description: 一篇儿文章id
	 * @Version1.0 2015年4月13日 下午4:03:42 by 代鹏（daipeng@dangdang.com）创建
	 * @param paramMap
	 * @return
	 */
	Long getDigestIdtWithOne(Map<String, Object> paramMap);
	
	/**
	 * 根据参数集查询频道文章列表
	 * Description: 
	 * @Version1.0 2015年6月1日 下午3:12:15 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param param
	 * @return
	 */
	List<Digest> queryChannelDigests(Map<String, Object> param);
	
	/**
	 * 根据参数集查询书吧文章列表
	 * Description: 
	 * @Version1.0 2015年6月1日 下午3:12:15 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param param
	 * @return
	 */
	List<Digest> queryBarDigests(Map<String, Object> param);
}
