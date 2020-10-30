package com.aidoudong.service.system;

import java.util.List;

import com.aidoudong.entity.system.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysPermissionService extends IService<SysPermission> {
	
	/**
	 * 通过用户id查询所拥有的权限
	 * @param userId
	 * @return
	 */
	List<SysPermission> findByUserId(Long userId);
	
	/**
	 * 通过权限id删除权限资源
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);
	
}
