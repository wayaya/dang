package com.dangdang.digital.dao.impl;


import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.IEpubImportDao;
import com.dangdang.digital.model.EpubImport;

/**
 * epub导入任务表
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2015年1月14日 上午11:47:08  by 汪明晖（wangminghui@dangdang.com）创建
 */
@Repository(value="epubImportDao")
public class EpubImportDaoImpl extends BaseDaoImpl<EpubImport> implements
		IEpubImportDao {

}
