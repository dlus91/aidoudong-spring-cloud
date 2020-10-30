package com.aidoudong.dao.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aidoudong.entity.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface SysRoleMapper extends BaseMapper<SysRole>{

	/**
	 * 只要将非limit的sql语句写在对应的id="selectPage"里面（SysRoleMapper.xml）,
	 * 不需要自己去分页，mybatis-plus自己分页。
	 * 但是，它实现分页，你需要第一个参数传入Page，而其他参数你需要使用@Param("xxx")要取别名 
	 * 最终查询到的数据会被封装到IPage实现里面
	 * @param page
	 * @param sysRole
	 * @return
	 */
	IPage<SysRole> selectPage(Page<SysRole> page,@Param("p") SysRole sysRole);
	
	/**
	 * 通过角色id删除角色权限表中的所有记录
	 * @param roleId
	 * @return
	 */
	boolean deleteRolePermissionByRoleId(@Param("roleId") Long roleId);
	
	/**
	 * 保存角色与权限关系表数据
	 * @param roleId 角色id
	 * @param perIds 角色所拥有的权限
	 * @return
	 */
	boolean saveRolePermissionByRoleId(@Param("roleId") Long roleId, @Param("perIds") List<Long> perIds);
	
	/**
	 * 通过用户id查询所拥有的角色
	 * @param userId 用户id
	 * @return 查询到的角色集合
	 */
	List<SysRole> findByUserId(@Param("userId") Long userId);
	
}
