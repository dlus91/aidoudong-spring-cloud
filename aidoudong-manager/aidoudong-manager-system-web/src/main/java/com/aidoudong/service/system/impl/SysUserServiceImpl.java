package com.aidoudong.service.system.impl;

import java.util.Date;

import aidoudong.common.utils.AssertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aidoudong.dao.system.mapper.SysRoleMapper;
import com.aidoudong.dao.system.mapper.SysUserMapper;
import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.system.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	
	private static final String PASSWORD_DEFAULT = "$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public SysUser findByUsername(String username) {
		if(StringUtils.isEmpty(username)) {
			return null;
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		// baseMapper对应的SysUserMapper
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public SysUser findByMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)) {
			return null;
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mobile", mobile);
		// baseMapper对应的SysUserMapper
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser) {
		return baseMapper.selectPage(page, sysUser);
	}

	@Override
	public SysUser findById(Long id) {
		if(StringUtils.isEmpty(id)) {
			return null;
		}
		// 1，用户id查询用户信息
		SysUser sysUser = baseMapper.selectById(id);
		if(sysUser != null) {
			// 2，用户id查询所拥有的角色
			sysUser.setRoleList(sysRoleMapper.findByUserId(id));
		}
		return sysUser;
	}
	
	@Transactional("systemTransactionManager")
	@Override
	public boolean saveOrUpdate(SysUser entity) {
		AssertUtil.notNull(entity,"保存失败");
		// 如果传入的id为空，则为新增，则初始化密码
		if(entity.getPassword() == null) {
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
		SysUser sysUser = baseMapper.selectById(id);
		// 2，再更新用户信息，将状态更新为已删除
		sysUser.setEnabled(false);
		sysUser.setUpdateDate(new Date());
		baseMapper.updateById(sysUser);
		return true;
	}
}
