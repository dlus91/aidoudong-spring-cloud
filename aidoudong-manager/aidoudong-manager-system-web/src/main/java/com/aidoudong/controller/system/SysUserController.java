package com.aidoudong.controller.system;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import com.aidoudong.entity.system.SysRole;
import com.aidoudong.entity.system.SysUser;
import com.aidoudong.service.system.SysRoleService;
import com.aidoudong.service.system.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 * --@PreAuthorize/@PostAuthorize/@PreFilter/@PostFilter
 * 上面这几个注释可以用到@Controller,@Service,@Repository层中
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HTML_PREFIX = "system/user/";
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private BaseResultView fastJsonResultView;
	
	@PreAuthorize("hasAuthority('sys:user')")
	@GetMapping(value = {"/",""})
	public String user() {
		return HTML_PREFIX + "user-list";
	}
	
	/**
	 * 分页查询用户列表 
	 * @param page 分页对象：size，current
	 * @param sysUser 查询条件：username，mobile
	 * @return
	 */
	@PreAuthorize("hasAuthority('sys:user:list')")
	@PostMapping("/page")
	@ResponseBody
	public String page(Page<SysUser> page,SysUser sysUser) {
		return fastJsonResultView.ok(ResultView.success(sysUserService.selectPage(page, sysUser)));
	}
	
	/**
	 * 跳转新增或修改页面
	 * /form 新增 
	 * /form/{id} 修改  
	 * @PathVariable(required = false) 设置为false，则id可不传，为true，则必须传id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
	@GetMapping(value = {"/form","/form/{id}"})
	public String form(@PathVariable(required = false) Long id, Model model) {
		// 1，通过用户id信息，并包含了用户拥有的角色
		SysUser user = sysUserService.findById(id);
		model.addAttribute("user", user == null ? new SysUser() : user);
		// 2，查询出所有的角色信息
		List<SysRole> roleList = sysRoleService.list();
		model.addAttribute("roleList", roleList);
		// 绑定后页面可获取
		return HTML_PREFIX + "user-form";
	}
	
	@PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
	@RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST})
	public String saveOrUpdate(SysUser sysUser) {
		// 保存或更新到用户表，要将选中的角色保存或更新到用户角色中间表 sys_user_role
		sysUserService.saveOrUpdate(sysUser);
		return "redirect:/" + HTML_PREFIX;
	}
	
	@PreAuthorize("hasAuthority('sys:user:delete')")
	@DeleteMapping("/{id}")
	@ResponseBody
	public String deleteById(@PathVariable Long id) {
		// 逻辑删除，只做更新
		sysUserService.deleteById(id);
		return fastJsonResultView.ok(ResultView.success());
	}
	
	
}
