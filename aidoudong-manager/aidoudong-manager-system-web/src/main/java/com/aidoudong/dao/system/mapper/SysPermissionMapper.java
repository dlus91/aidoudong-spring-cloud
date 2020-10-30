package com.aidoudong.dao.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aidoudong.entity.system.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysPermissionMapper extends BaseMapper<SysPermission>{

	
	List<SysPermission> selectPermissionByUserId(@Param("userId") Long userId);
	
	List<SysPermission> findByRoleId(@Param("roleId") Long roleId);
	
}
