package com.aidoudong.service.system.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aidoudong.dao.system.mapper.SysPermissionMapper;
import com.aidoudong.dao.system.mapper.SysRoleMapper;
import com.aidoudong.entity.system.SysPermission;
import com.aidoudong.entity.system.SysRole;
import com.aidoudong.service.system.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>implements SysRoleService {
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Override
	public IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole) {
		return baseMapper.selectPage(page,sysRole);
	}

	@Override
	public SysRole findById(Long id) {
		if(id == null) {
			return null;
		}
		// 1，通过角色id查询对应的角色信息
		SysRole sysRole = baseMapper.selectById(id);
		// 2，通过角色id查询所拥有的权限
		List<SysPermission> permissionList = sysPermissionMapper.findByRoleId(id);
		// 3，将查询到的权限set到角色对象中SysRole
		sysRole.setPerList(permissionList);
		return sysRole;
	}
	
	@Transactional("systemTransactionManager")
	@Override
	public boolean saveOrUpdate(SysRole entity) {
		entity.setUpdateDate(new Date());
		// 1，更新角色表中的数据
		boolean flag = super.saveOrUpdate(entity);
		if(flag) {
			// 2，更新角色权限表中的数据（删除>新增）
			// 这里解释下为什么要先删后增
			// 因为数据库是行级锁，这里又有加事务，
			// 而删除=id非批量操作效率较高故先删，而新增因为是批量新增效率相对较低
			// 因为事务的特性，应该把效率最低的放到最后，所以先删后增
			
			// 更新角色权限表中的数据（删除）
			baseMapper.deleteRolePermissionByRoleId(entity.getId());
			
			if(CollectionUtils.isNotEmpty(entity.getPerIds())) {
				// 更新角色权限表中的数据（新增）
				baseMapper.saveRolePermissionByRoleId(entity.getId(), entity.getPerIds());
			}
		}
		
		return flag;
	}

	@Transactional("systemTransactionManager")
	@Override
	public boolean deleteById(Long id) {
		// 1，通过id删除角色信息表数据
		baseMapper.deleteById(id);
		// 2，通过id删除角色权限管关系表数据
		baseMapper.deleteRolePermissionByRoleId(id);
		return true;
	}

}
