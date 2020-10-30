package com.aidoudong.service.business;

import java.util.List;

import com.aidoudong.entity.business.ClientPermission;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ClientPermissionService extends IService<ClientPermission> {
	
	/**
	 * 通过用户id查询所拥有的权限
	 * @param userId
	 * @return
	 */
	List<ClientPermission> findByUserId(Long userId);
	
}
