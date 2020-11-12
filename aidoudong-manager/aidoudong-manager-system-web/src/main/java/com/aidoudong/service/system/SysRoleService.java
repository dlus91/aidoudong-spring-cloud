package com.aidoudong.service.system;

import com.aidoudong.entity.system.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysRoleService extends IService<SysRole>{
	
	/**
	 * 分页查询角色列表
	 * @param page 分页参数
	 * @param sysRole 条件查询对象，会取name属性值作为条件
	 */
	IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole);
	
	/**
	 * 通过角色id查询角色信息和所拥有的权限信息
	 */
	SysRole findById(Long id);
	
	/**
	 * 1，通过id删除角色信息表数据
	 * 2，通过id删除角色权限管关系表数据
	 * @param id 角色id
	 */
	boolean deleteById(Long id);
	
}
