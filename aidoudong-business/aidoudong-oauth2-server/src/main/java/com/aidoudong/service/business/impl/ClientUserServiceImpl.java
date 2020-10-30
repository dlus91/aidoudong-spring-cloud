package com.aidoudong.service.business.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aidoudong.dao.business.mapper.ClientUserMapper;
import com.aidoudong.entity.business.ClientUser;
import com.aidoudong.service.business.ClientUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ClientUserServiceImpl extends ServiceImpl<ClientUserMapper, ClientUser> implements ClientUserService {
	
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

}
