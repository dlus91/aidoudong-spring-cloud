package com.aidoudong.service.business.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aidoudong.dao.business.mapper.ClientPermissionMapper;
import com.aidoudong.entity.business.ClientPermission;
import com.aidoudong.service.business.ClientPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ClientPermissionImpl extends ServiceImpl<ClientPermissionMapper, ClientPermission> implements ClientPermissionService {

	@Override
	public List<ClientPermission> findByUserId(Long userId) {
		if(userId == null) {
			return null;
		}
		List<ClientPermission> permissionList = baseMapper.selectPermissionByUserId(userId);
		// 如果没有权限，则将集合中的数据null移除
		permissionList.remove(null);
		return permissionList;
	}

}
