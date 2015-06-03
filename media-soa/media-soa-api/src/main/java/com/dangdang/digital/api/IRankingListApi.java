package com.dangdang.digital.api;

import java.util.List;

import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;

/**
 * 
 * Description: 榜单接口
 * All Rights Reserved.
 * @version 1.0  2014年12月9日 下午5:43:54  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public interface IRankingListApi {
	
	/**
	 * 
	 * Description: 通过榜单类型标识查询该榜单下一安数据的Media的编号集合
	 * 榜单只针对单品销售主体
	 * @Version1.0 2014年12月12日 下午5:10:59 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param offset
	 * @param count
	 * @param type
	 * @return
	 */
	public ResultTwoTuple<Long,List<Long>> getSaleIdListByType(int offset,int count,String type);
	
}	
