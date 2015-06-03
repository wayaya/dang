package com.dangdang.digital.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Bought;
import com.dangdang.digital.vo.AddBoughtMessage;
import com.dangdang.digital.vo.CreateBoughtVo;
import com.dangdang.digital.vo.CreateOrderVo;

/**
 * 
 * Description: 已购信息service接口 All Rights Reserved.
 * 
 * @version 1.0 2014年12月26日 上午10:50:39 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public interface IBoughtService extends IBaseService<Bought, Long> {

	/**
	 * 
	 * Description: 获取已购信息列表
	 * 
	 * @Version1.0 2014年12月29日 上午10:06:24 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return
	 */
	List<Bought> getBoughtListByCustId(Long custId, Integer start, Integer count,String fromPaltform);

	/**
	 * 
	 * Description: 通过mediaId获取已购主表信息
	 * @Version1.0 2014年12月30日 下午3:05:04 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param mediaId
	 * @return
	 */
	public Bought getBoughtByMediaId(Long mediaId,Long custId);

	/**
	 * 
	 * Description: 订单添加已购信息
	 * @Version1.0 2014年12月31日 下午2:26:45 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param createOrderVo
	 * @throws Exception 
	 */
	public void addBought(CreateOrderVo createOrderVo) throws Exception;
	
	/**
	 * 
	 * Description: 除订单外其它方式添加已购信息
	 * @Version1.0 2015年1月7日 上午11:25:39 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param addBoughtMessage
	 * @throws Exception
	 */
	public void addBought(AddBoughtMessage addBoughtMessage) throws Exception;

	/**
	 * 
	 * Description: 根据custId查询已购书籍数量
	 * @Version1.0 2015年1月5日 下午2:26:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public Integer getBoughtCountByCustId(Long custId,String fromPaltform);

	/**
	 * 
	 * Description: 获取已购全本的mediaId集合
	 * @Version1.0 2015年2月6日 下午6:01:57 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param custId
	 * @param updateTime
	 * @return
	 */
	public List<Long> getMyBoughtWholeMediaIds(Long custId, Date updateTime,String fromPaltform);
	
	/**
	 * 
	 * Description: 创建已购记录（非当读小说平台）
	 * @Version1.0 2015年3月12日 上午10:16:10 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param createBoughtVo
	 */
	public Map<String,String> createAndSaveBought(CreateBoughtVo createBoughtVo);
	
	public String mergeUserBought(Long oldCustId,Long newCustId,String boughtId);

}
