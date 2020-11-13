package com.aidoudong;

import com.aidoudong.common.utils.DictionaryCodeUtil;
import com.aidoudong.entity.business.ClientUser;
import com.aidoudong.entity.system.SysPermission;
import com.aidoudong.entity.system.SysRole;
import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.business.client.ClientRoleService;
import com.aidoudong.service.business.client.ClientUserService;
import com.aidoudong.service.system.SysPermissionService;
import com.aidoudong.service.system.SysRoleService;
import com.aidoudong.service.system.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWebApplication {
	
	@Autowired
    SysUserService sysUserService;
	@Autowired
    SysRoleService sysRoleService;
	@Autowired
    SysPermissionService sysPermissionService;
	@Autowired
    ClientUserService clientUserService;
	@Autowired
    ClientRoleService clientRoleService;

	@Test
	public void testSysUser() {
//		List<SysUser> list = sysUserService.list();
//		System.out.println("list:"+list);

		SysUser user = sysUserService.findByUsername("");
		System.out.println("user:"+user);
	}

	@Test
	public void testSysRole() {
		ClientUser user = clientUserService.findById(9L);
		SysRole sysRole = sysRoleService.getById(9);
		System.out.println("client-user:"+user);
		System.out.println("system-role:"+sysRole);
	}

	@Test
	public void testSysPermission() {
//		SysPermission sysPermission = sysPermissionService.getById(18);
//		System.out.println("permission:"+sysPermission.toString());
		List<SysPermission> permissionList = sysPermissionService.findByUserId(9L);
		permissionList.forEach(System.out::println);
	}

	@Test
	public void test01(){
		Map codeMap = DictionaryCodeUtil.getCodeMap();
		Map map = Collections.checkedMap(codeMap, String.class, List.class);
		System.out.println(map);
		map.put("a1", "");
	}


}
