package com.aidoudong.service.business.client;

import com.aidoudong.entity.business.ClientRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ClientRoleService extends IService<ClientRole>{
	
	/**
	 * 分页查询角色列表
	 * @param page 分页参数
	 * @param sysRole 条件查询对象，会取name属性值作为条件
	 * @return
	 */
	IPage<ClientRole> selectPage(Page<ClientRole> page, ClientRole sysRole);
	
	/**
	 * 通过角色id查询角色信息和所拥有的权限信息
	 * @param id
	 * @return
	 */
	ClientRole findById(Long id);
	
	/**
	 * 1，通过id删除角色信息表数据
	 * 2，通过id删除角色权限管关系表数据
	 * @param id 角色id
	 * @return
	 */
	boolean deleteById(Long id);
	
}
