/**
 * Description: IOrderApi.java
 * All Rights Reserved.
 * @version 1.0  2014年11月29日 下午5:00:49  by 张宪斌（zhangxianbin@dangdang.com）创建
 */
package com.dangdang.digital.api;

import java.util.Date;
import java.util.List;

import com.dangdang.digital.model.Bought;
import com.dangdang.digital.model.BoughtChapter;
import com.dangdang.digital.model.BoughtDetail;
import com.dangdang.digital.vo.CreateBoughtVo;
import com.dangdang.digital.vo.CreateOrderVo;
import com.dangdang.digital.vo.ShoppingViewForChapterVo;

/**
 * Description: 订单接口 All Rights Reserved.
 * 
 * @version 1.0 2014年11月29日 下午5:00:49 by 张宪斌（zhangxianbin@dangdang.com）创建
 */
public interface IOrderApi {

	/**
	 * 
	 * Description: 创建订单
	 * 
	 * @Version1.0 2014年11月29日 下午5:05:38 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param createOrderVo
	 * @return
	 * @throws Exception
	 */
	public CreateOrderVo createAndSaveOrder(CreateOrderVo createOrderVo) throws Exception;

	public ShoppingViewForChapterVo findShoppingViewForChapter(Long custId, Long mediaChapterId, String deviceType,
			String userName) throws Exception;

	/**
	 * 
	 * Description: 获取已购列表
	 * 
	 * @Version1.0 2014年12月29日 下午2:53:39 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Bought> getBoughtListByCustId(Long custId, Integer start, Integer count,String fromPaltform);

	/**
	 * 
	 * Description: 获取已购详情列表
	 * 
	 * @Version1.0 2014年12月29日 下午2:53:50 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<BoughtDetail> getBoughtDetailListByBoughtId(Long boughtId, Integer start, Integer count);

	/**
	 * 
	 * Description: 获取已购章节列表
	 * 
	 * @Version1.0 2014年12月29日 下午2:54:00 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtDetailId
	 * @param start
	 * @param count
	 * @return
	 */
	public List<BoughtChapter> getBoughtChapterListByBoughtDetailId(Long boughtDetailId, Integer start, Integer count);

	/**
	 * 
	 * Description: 根据custId查询已购书籍数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:26:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public Integer getBoughtCountByCustId(Long custId,String fromPaltform);

	/**
	 * 
	 * Description: 根据boughtId获取boughtDetail数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:49:37 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtId
	 * @return
	 */
	public Integer getBoughtDetailCountByBoughtId(Long boughtId);

	/**
	 * 
	 * Description: 根据boughtDetailId获取boughtChapter数量
	 * 
	 * @Version1.0 2015年1月5日 下午2:50:58 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param boughtDetailId
	 * @return
	 */
	public Integer getBoughtChapterCountByBoughtDetailId(Long boughtDetailId);

	/**
	 * 
	 * Description: 获取已购全本的mediaId列表
	 * 
	 * @Version1.0 2015年2月6日 下午6:04:10 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param updateTime
	 * @return
	 */
	public List<Long> getMyBoughtWholeMediaIds(Long custId, Date updateTime,String fromPaltForm);
	
	/**
	 * 
	 * Description: 创建已购记录（非当读小说平台）
	 * @Version1.0 2015年3月12日 上午9:58:00 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param createBoughtVo
	 */
	public void createAndSaveBought(CreateBoughtVo createBoughtVo) throws Exception;
	
}
