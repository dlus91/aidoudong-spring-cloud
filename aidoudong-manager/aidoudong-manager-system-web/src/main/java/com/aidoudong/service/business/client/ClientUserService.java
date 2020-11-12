package com.aidoudong.service.business.client;

import com.aidoudong.entity.business.ClientUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * IService<T>提供了对T表操作的很多抽象方法，比如：批量操作
 */
public interface ClientUserService extends IService<ClientUser> {
	
	/**
	 * 通过用户名查询用户信息
	 * @param username 用户名
	 */
	ClientUser findByUsername(String username);
	
	/**
	 * 通过手机号查询用户信息
	 * @param mobile 手机号
	 */
	ClientUser findByMobile(String mobile);
	
	/**
	 * 分页查询用户信息
	 * @param page  分页对象 
	 * @param sysUser 查询条件
	 */
	IPage<ClientUser> selectPage(Page<ClientUser> page,ClientUser sysUser);
	
	/**
	 * 1，用户id查询用户信息
	 * 2，用户id查询所拥有的角色
	 */
	ClientUser findById(Long id);
	
	/**
	 * 通过用户id来逻辑删除，将is_enabled = 0
	 */
	boolean deleteById(Long id);
}
