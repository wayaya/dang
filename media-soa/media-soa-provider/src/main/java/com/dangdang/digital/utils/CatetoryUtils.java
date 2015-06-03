package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.List;

import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.vo.CatetoryMediaSaleVo;

/**
 * 
 * Description: media分类工具类,将catetory组成上
 * All Rights Reserved.
 * @version 1.0  2014年12月25日 下午4:10:24  by 吕翔  (lvxiang@dangdang.com) 创建
 */
public class CatetoryUtils {
	
	private static List<CatetoryMediaSaleVo> copyAttribute(List<Catetory> catetoryList){
		List<CatetoryMediaSaleVo> cvList = new ArrayList<CatetoryMediaSaleVo> ();
		for(Catetory catetory:catetoryList){
			CatetoryMediaSaleVo temp = new CatetoryMediaSaleVo();
			temp.setId(catetory.getId());
			temp.setCode(catetory.getCode());
			temp.setName(catetory.getName());
			temp.setParentId(catetory.getParentId());
			temp.setPath(catetory.getPath());
			temp.setImage(catetory.getImage());
			cvList.add(temp);
		}
		return cvList;
	}
	/** 
	 * 
	 * Description: 判断分类是否有子分类
	 * @Version1.0 2014年12月19日 下午2:40:44 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param catetory	
	 * @param catetoryList
	 * @return
	 */
	private static void addSubCatetory(CatetoryMediaSaleVo cv,List<CatetoryMediaSaleVo> cvList){
		cv.setParsed(true);
		List<CatetoryMediaSaleVo> subList = new ArrayList<CatetoryMediaSaleVo>();
		for(CatetoryMediaSaleVo temp:cvList){
			if(temp.isParsed()){
				continue;
			}
			if(temp.getParentId()==cv.getId()){
				CatetoryMediaSaleVo catetoryVo = new CatetoryMediaSaleVo();
				copyAttribute(temp,catetoryVo);
				addSubCatetory(catetoryVo,cvList);//递归解析
				subList.add(catetoryVo);
			}
		}
		if(subList.size()>0){
			cv.setCatetoryList(subList);
		}
	}
	
	/**
	 * 
	 * Description: 属性复制
	 * @Version1.0 2014年12月25日 下午4:26:26 by 吕翔  (lvxiang@dangdang.com) 创建
	 * @param src
	 * @param des
	 */
	private static void copyAttribute(CatetoryMediaSaleVo src, CatetoryMediaSaleVo des){
		des.setId(src.getId());
		des.setCode(src.getCode());
		des.setName(src.getName());
		des.setParentId(src.getParentId());
		//des.setPath(src.getPath());
	}
	
	
	/**
	 * 
	 * Description: 将原始的 catetory集合解析成树形结构
	 * 
	 * @Version1.0 2014年12月25日 下午4:16:08 by 吕翔 (lvxiang@dangdang.com) 创建
	 * @param catetoryList
	 * @return
	 */
	public static List<CatetoryMediaSaleVo> parse(Catetory parentCatetory, List<Catetory> catetoryList,
			String imageHttpRoot) {
		List<CatetoryMediaSaleVo> cvList = copyAttribute(catetoryList);
		List<CatetoryMediaSaleVo> resultList = new ArrayList<CatetoryMediaSaleVo>();
		for (CatetoryMediaSaleVo cv : cvList) {
			if (cv.isParsed() || cv.getParentId().intValue() != parentCatetory.getId().intValue()) {
				// 如果已经解析过,或都当前分类不是parentCatetory的直接子类时,跳过
				continue;
			}
			CatetoryMediaSaleVo temp = new CatetoryMediaSaleVo();
			copyAttribute(cv, temp);
			temp.setImage(imageHttpRoot + cv.getImage());
			addSubCatetory(temp, cvList);
			temp.setParsed(null);
			resultList.add(temp);
		}
		return resultList;
	}
}
