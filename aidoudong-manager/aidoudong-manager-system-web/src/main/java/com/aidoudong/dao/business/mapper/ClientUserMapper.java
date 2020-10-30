package com.aidoudong.dao.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aidoudong.entity.business.ClientUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 继承BaseMapper<T>接口，它提供了很多对T表的数据操作方法
 */
public interface ClientUserMapper extends BaseMapper<ClientUser>{
	
	IPage<ClientUser> selectPage(Page<ClientUser> page,@Param("u") ClientUser sysUser);
	
	/**
	 * 通过用户id删除用户角色表中的所有记录
	 * @param userId
	 * @return
	 */
	boolean deleteUserRoleByUserId(@Param("userId") Long userId);
	
	/**
	 * 保存用户与角色关系表数据
	 * @param userId 用户id
	 * @param roleIds 角色ids
	 * @return
	 */
	boolean saveUserRoleByUserId(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
	
}
