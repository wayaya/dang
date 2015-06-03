package com.dangdang.digital.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Digest;
import com.dangdang.digital.model.DigestLable;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;

public interface IDigestService extends IBaseService<Digest, Long> {
	
	public List<Map<String, Object>> getSign();
	
	public List<Map<String, Object>> getSignByDigestId();
	
	
	public PageFinder<Digest> findDigestByAuthorId(Query query, Long authorId, Integer isShow);
	
	public PageFinder<Digest> findDigestBySignId(Query query, Long signId, Integer isShow);
	
	public List<Digest> queryDigestsByChapterIds(List<Long> chapterIds) ;
	
	public int saveDigestLable(DigestLable dl) ;
	
	int insert(Digest record);
	
	int updateDigest(Digest record);
	
	int delete(Long id);
	
	Digest findDigestById(Long id);
	
	Digest findDigestContentById(Long id);
	
	List<Digest> queryDigestsByIdsAndSortByIds(Collection<Long> ids, Integer offSet, Integer pageSize);
	
	List<Digest> queryDigestsByIds(Collection<Long> ids, Date lastDate, Integer pageSize);
	
	List<Digest> queryDigestsByMood(Integer mood, Date lastDate, Integer pageSize);
	
	/**
	 * Description: 从DB中取某一日期，白天黑夜的精品列表
	 * @Version1.0 2015年1月26日 下午5:47:00 by 代鹏（daipeng@dangdang.com）创建
	 * @param currentPageDate:yyyy-MM-dd
	 * @param dayOrNight
	 * @return
	 */
	List<Digest> queryDigestsListForDB(String currentPageDate, Integer dayOrNight)throws Exception;
	
	/**
	 * Description: 先从缓存中取某一日期，白天黑夜的精品列表，若没有再取DB并入缓存
	 * @Version1.0 2015年1月26日 下午5:47:45 by 代鹏（daipeng@dangdang.com）创建
	 * @param currentPageDate:yyyy-MM-dd
	 * @param dayOrNight
	 * @return
	 */
	List<Digest> queryDigestHomePageList(String currentPageDate, Integer dayOrNight)throws Exception;
	
	/**
	 * Description: 根据时间段以及白天黑夜查询
	 * @Version1.0 2015年1月28日 下午5:06:59 by 代鹏（daipeng@dangdang.com）创建
	 * @param startDate
	 * @param endDate
	 * @param dayOrNight
	 * @return
	 */
	List<Digest> queryDigestListByDateSlotAndDayOrNight(Date startDate, Date endDate, Integer dayOrNight);
	
	/**
	 * Description: 小于展示时间showEndDate，且取最新一条记录
	 * @Version1.0 2015年1月29日 下午2:28:27 by 代鹏（daipeng@dangdang.com）创建
	 * @param showEndDate
	 * @param dayOrNight
	 * @return
	 */
	Digest getDigestByShowEndDateLimitOne(Date showEndDate, Integer dayOrNight);
	
	/**
	 * Description: 根据id取精品正文内容以及展示类型
	 * @Version1.0 2015年1月31日 下午2:51:49 by 代鹏（daipeng@dangdang.com）创建
	 * @param id
	 * @return
	 */
	Digest getDigestContentById(Long id);
	
	/**
	 * Description: 根据标签id查询对应的精品列表
	 * @Version1.0 2015年2月9日 下午4:46:21 by 代鹏（daipeng@dangdang.com）创建
	 * @param signId
	 * @param lasteDate
	 * @param pageSize
	 * @return
	 */
	List<Digest> queryDigestsBySignId(Long signId, Date lasteDate, Integer pageSize);
	
	/**
	 * Description: 根据作者id查询对应的精品列表
	 * @Version1.0 2015年2月9日 下午4:46:11 by 代鹏（daipeng@dangdang.com）创建
	 * @param authorId
	 * @param lasteDate
	 * @param pageSize
	 * @return
	 */
	List<Digest> queryDigestsByAuthorId(Long authorId, Date lasteDate, Integer pageSize);
	
	/**
	 * Description: 获取个人收藏精品文章列表
	 * @Version1.0 2015年3月11日 下午3:31:50 by 代鹏（daipeng@dangdang.com）创建
	 * @param custId
	 * @param storeType
	 * @param platForm
	 * @param offSet
	 * @param pageSize
	 * @return
	 */
	List<Digest> queryDigestsByStoreUpAndCustId(Long custId, String storeType, String platForm, Integer offSet, Integer pageSize);
	
	/**
	 * 根据多种类型查询文章
	 * Description: 
	 * @Version1.0 2015年5月29日 下午3:24:21 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param sortPage
	 * @param dayOrNight
	 * @param pageSize
	 * @param types
	 * @param version  V1 上拉  V2下拉
	 * @return
	 */
	List<Digest> queryDigestsHomePage(Long sortPage, Integer dayOrNight, Integer pageSize, String types, String version);
	
	/**
	 * Description: 一篇儿文章id
	 * @Version1.0 2015年4月13日 下午3:53:19 by 代鹏（daipeng@dangdang.com）创建
	 * @param dayOrNight
	 * @param start
	 * @param end
	 * @return
	 */
	Long getDigestIdtWithOne(Integer dayOrNight, Date start, Date end) throws Exception;
	
	/**
	 * 查询频道文章列表
	 * Description: 
	 * @Version1.0 2015年6月1日 下午3:10:27 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param param 参数集
	 * @return
	 */
	List<Digest> queryChannelDigests(Map<String, Object> param);
	
	/**
	 * 查询书吧文章列表
	 * Description: 
	 * @Version1.0 2015年6月1日 下午3:10:27 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param param 参数集
	 * @return
	 */
	List<Digest> queryBarDigests(Map<String, Object> param);
}
