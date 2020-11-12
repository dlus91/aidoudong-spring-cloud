package com.aidoudong.dao.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aidoudong.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 继承BaseMapper<T>接口，它提供了很多对T表的数据操作方法
 */
public interface SysUserMapper extends BaseMapper<SysUser>{
	
	IPage<SysUser> selectPage(Page<SysUser> page,@Param("u") SysUser sysUser);
	
	/**
	 * 通过用户id删除用户角色表中的所有记录
	 */
	boolean deleteUserRoleByUserId(@Param("userId") Long userId);
	
	/**
	 * 保存用户与角色关系表数据
	 * @param userId 用户id
	 * @param roleIds 角色ids
	 */
	boolean saveUserRoleByUserId(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
	
}
