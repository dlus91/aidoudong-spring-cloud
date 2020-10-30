package com.aidoudong.service.business.client.impl;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aidoudong.dao.business.mapper.ClientRoleMapper;
import com.aidoudong.dao.business.mapper.ClientUserMapper;
import com.aidoudong.entity.business.ClientUser;
import com.aidoudong.service.business.client.ClientUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ClientUserServiceImpl extends ServiceImpl<ClientUserMapper, ClientUser> implements ClientUserService {
	
	private static final String PASSWORD_DEFAULT = "$2a$10$GbfcjA0Gy0BEq/BWlYXjJufGC2QYea.v9ye/kB0DY0jyH88qIRw5.";
	@Autowired
	private ClientRoleMapper clientRoleMapper;

	@Override
	public ClientUser findByUsername(String username) {
		if(StringUtils.isEmpty(username)) {
			return null;
		}
		QueryWrapper<ClientUser> queryWrapper = new QueryWrapper<ClientUser>();
		queryWrapper.eq("username", username);
		// baseMapper对应的SysUserMapper
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public ClientUser findByMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)) {
			return null;
		}
		QueryWrapper<ClientUser> queryWrapper = new QueryWrapper<ClientUser>();
		queryWrapper.eq("mobile", mobile);
		// baseMapper对应的SysUserMapper
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public IPage<ClientUser> selectPage(Page<ClientUser> page, ClientUser sysUser) {
		return baseMapper.selectPage(page, sysUser);
	}

	@Override
	public ClientUser findById(Long id) {
		if(StringUtils.isEmpty(id)) {
			return null;
		}
		// 1，用户id查询用户信息
		ClientUser clientUser = baseMapper.selectById(id);
		if(clientUser != null) {
			// 2，用户id查询所拥有的角色
			clientUser.setRoleList(clientRoleMapper.findByUserId(id));
		}
		return clientUser;
	}
	
	@Transactional("businessTransactionManager")
	@Override
	public boolean saveOrUpdate(ClientUser entity) {
		// 如果传入的id为空，则为新增，则初始化密码
		if(entity != null && entity.getId() == null) {
			entity.setPassword(PASSWORD_DEFAULT);
		}
		entity.setUpdateDate(new Date());
		// 1，保存或更新到用户表
		boolean flag = super.saveOrUpdate(entity);
		if(flag) {
			// 2，更新用户角色表中的数据（删除>新增）
			// 这里解释下为什么要先删后增
			// 因为数据库是行级锁，这里又有加事务，
			// 而删除=id非批量操作效率较高故先删，而新增因为是批量新增效率相对较低
			// 因为事务的特性，应该把效率最低的放到最后，所以先删后增
			
			// 更新用户角色表中的数据（删除）
			baseMapper.deleteUserRoleByUserId(entity.getId());
			
			if(CollectionUtils.isNotEmpty(entity.getRoleIds())) {
				// 更新用户角色表中的数据（新增）
				baseMapper.saveUserRoleByUserId(entity.getId(), entity.getRoleIds());
			}
		}
		return true;
	}

	@Override
	public boolean deleteById(Long id) {
		// 1，查询用户信息
		ClientUser clientUser = baseMapper.selectById(id);
		// 2，再更新用户信息，将状态更新为已删除
		clientUser.setEnabled(false);
		clientUser.setUpdateDate(new Date());
		baseMapper.updateById(clientUser);
		return true;
	}
}
