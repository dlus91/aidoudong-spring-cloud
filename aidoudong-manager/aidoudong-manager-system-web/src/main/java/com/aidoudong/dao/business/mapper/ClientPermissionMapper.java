package com.aidoudong.dao.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aidoudong.entity.business.ClientPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ClientPermissionMapper extends BaseMapper<ClientPermission>{

	List<ClientPermission> selectPermissionByUserId(@Param("userId") Long userId);
	
	List<ClientPermission> findByRoleId(@Param("roleId") Long roleId);
	
}
