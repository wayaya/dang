package com.dangdang.digital.dao;

import java.util.List;
import java.util.Map;

import com.dangdang.digital.model.Catetory;
import com.dangdang.digital.model.ResourceDirectory;


public interface IResourceDirectoryDao extends IBaseDao<ResourceDirectory> {

	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<ResourceDirectory> getTreeByParentId(ResourceDirectory dir);
	
	public void deleteByPath(Map map);
}
