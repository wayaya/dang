package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDsPayProductDao;
import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.DsPayProduct;
import com.dangdang.digital.service.IDsPayProductService;
/**
 * DsPayProduct Manager.
 */
@Service
public class DsPayProductServiceImpl extends BaseServiceImpl<DsPayProduct, Integer> implements IDsPayProductService {

	@Resource
	IDsPayProductDao dao;
	
	public IBaseDao<DsPayProduct> getBaseDao() {
		return dao;
	}

	@Override
	public List<String> getDSPayRelationProductIdByMoney(Integer money)
			throws MediaException {
		Integer moneySub1 = money/100;
		if(moneySub1 > 10000){
			return null;
		}
		Integer moneySub2 = money%100;
		List<Integer> param = new ArrayList<Integer>();
		if(moneySub1 > 0){
			param.add(moneySub1*100);
		}
		if(moneySub2 > 0){
			param.add(moneySub2);
		}
		List<DsPayProduct> dsPayProductList = this.dao.selectList("DsPayProduct.selectByMoneys", param);
		if(CollectionUtils.isEmpty(dsPayProductList)){
			return null;
		}
		List<String> result = new ArrayList<String>();
		Map<Integer,String> tempResult = new HashMap<Integer,String>();
		for(DsPayProduct dsPayProduct : dsPayProductList){
			if(dsPayProduct.getMoney() != null && !tempResult.containsKey(dsPayProduct.getMoney())){
				result.add(dsPayProduct.getProductId());
			}
		}
		if(result.size() == param.size()){
			return result;
		}
		return null;
	}
	
}
