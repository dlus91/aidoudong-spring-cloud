package com.aidoudong.service.system;

import com.aidoudong.entity.system.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * IService<T>提供了对T表操作的很多抽象方法，比如：批量操作
 */
public interface SysUserService extends IService<SysUser> {
	
	/**
	 * 通过用户名查询用户信息
	 * @param username 用户名
	 */
	SysUser findByUsername(String username);
	
	/**
	 * 通过手机号查询用户信息
	 * @param mobile 手机号
	 */
	SysUser findByMobile(String mobile);
	
	/**
	 * 分页查询用户信息
	 * @param page  分页对象 
	 * @param sysUser 查询条件
	 */
	IPage<SysUser> selectPage(Page<SysUser> page,SysUser sysUser);
	
	/**
	 * 1，用户id查询用户信息
	 * 2，用户id查询所拥有的角色
	 */
	SysUser findById(Long id);
	
	/**
	 * 通过用户id来逻辑删除，将is_enabled = 0
	 */
	boolean deleteById(Long id);
}
