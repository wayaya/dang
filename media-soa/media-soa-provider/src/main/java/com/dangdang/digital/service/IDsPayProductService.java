package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.DsPayProduct;


/**
 * DsPayProduct Manager.
 */
public interface IDsPayProductService extends IBaseService<DsPayProduct, Integer> {
	
	/**
	 * 
	 * Description: 根据需要支付的金额（单位：分）获取对应的虚拟商品id集合
	 * @Version1.0 2015年3月30日 下午6:49:53 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param money
	 * @return
	 * @throws MediaException
	 */
	public List<String> getDSPayRelationProductIdByMoney(Integer money)throws MediaException;
	
	public List<String> getDepositRelationProduct(List<String> productIds);
}
