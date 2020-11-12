package com.aidoudong.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aidoudong.dao.system.mapper.SysPermissionMapper;
import com.aidoudong.entity.system.SysPermission;
import com.aidoudong.service.system.SysPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

	@Override
	public List<SysPermission> findByUserId(Long userId) {
		if(userId == null) {
			return null;
		}
		List<SysPermission> permissionList = baseMapper.selectPermissionByUserId(userId);
		// 如果没有权限，则将集合中的数据null移除
		permissionList.remove(null);
		return permissionList;
	}

	@Override
	public boolean deleteById(Long id) {
		LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysPermission::getParentId, id).or().eq(SysPermission::getId, id);
		baseMapper.delete(queryWrapper);
		return true;
	}

}
