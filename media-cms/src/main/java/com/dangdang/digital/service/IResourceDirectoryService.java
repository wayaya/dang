package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.model.ResourceDirectory;

public interface IResourceDirectoryService extends IBaseService<ResourceDirectory, Integer> {

	/**获取树节点
	 * @param id
	 * @return
	 */
	public List<ResourceDirectory> getTreeByParentId(ResourceDirectory dir);
}
