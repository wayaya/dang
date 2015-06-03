package com.dangdang.digital.service;

import java.util.List;

import com.dangdang.digital.exception.MediaException;
import com.dangdang.digital.model.Role;
/**
 * 
* @Description: 角色管理
* @author 魏嵩 weisong@dangdang.com  
* @date 2014年11月14日 下午12:20:00
* @version V1.0
 */
public interface IRoleService extends IBaseService<Role,Long> {

	void deleteRoleByIds(List<Long> ids) throws MediaException;
}
