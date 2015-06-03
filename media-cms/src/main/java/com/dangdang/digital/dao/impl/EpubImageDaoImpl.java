package com.dangdang.digital.dao.impl;

import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IEpubImageDao;
import com.dangdang.digital.dao.IEpubImportDao;
import com.dangdang.digital.model.EpubImage;
import com.dangdang.digital.model.EpubImport;

/**
 * 导入书的图片地址
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月14日 上午11:57:39  by 汪明晖（wangminghui@dangdang.com）创建
 */
@Repository(value="epubImageDao")
public class EpubImageDaoImpl extends BaseDaoImpl<EpubImage> implements
		IEpubImageDao {
	
}
