package com.aidoudong.service.business.client.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aidoudong.dao.business.mapper.ClientPermissionMapper;
import com.aidoudong.dao.business.mapper.ClientRoleMapper;
import com.aidoudong.entity.business.ClientPermission;
import com.aidoudong.entity.business.ClientRole;
import com.aidoudong.service.business.client.ClientRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ClientRoleServiceImpl extends ServiceImpl<ClientRoleMapper, ClientRole>implements ClientRoleService {
	
	@Autowired
	private ClientPermissionMapper clientPermissionMapper;
	
	@Override
	public IPage<ClientRole> selectPage(Page<ClientRole> page, ClientRole clientRole) {
		return baseMapper.selectPage(page,clientRole);
	}

	@Override
	public ClientRole findById(Long id) {
		if(id == null) {
			return null;
		}
		// 1，通过角色id查询对应的角色信息
		ClientRole clientRole = baseMapper.selectById(id);
		// 2，通过角色id查询所拥有的权限
		List<ClientPermission> permissionList = clientPermissionMapper.findByRoleId(id);
		// 3，将查询到的权限set到角色对象中SysRole
		clientRole.setPerList(permissionList);
		return clientRole;
	}
	
	@Transactional("businessTransactionManager")
	@Override
	public boolean saveOrUpdate(ClientRole entity) {
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

	@Transactional("businessTransactionManager")
	@Override
	public boolean deleteById(Long id) {
		// 1，通过id删除角色信息表数据
		baseMapper.deleteById(id);
		// 2，通过id删除角色权限管关系表数据
		baseMapper.deleteRolePermissionByRoleId(id);
		return true;
	}

}
