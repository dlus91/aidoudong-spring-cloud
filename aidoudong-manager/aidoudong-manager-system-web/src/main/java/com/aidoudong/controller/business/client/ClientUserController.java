package com.aidoudong.controller.business.client;

import java.util.List;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aidoudong.entity.business.ClientRole;
import com.aidoudong.entity.business.ClientUser;
import com.aidoudong.service.business.client.ClientRoleService;
import com.aidoudong.service.business.client.ClientUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 用户管理
 * --@PreAuthorize/@PostAuthorize/@PreFilter/@PostFilter
 * 上面这几个注释可以用到@Controller,@Service,@Repository层中
 */
@Controller
@RequestMapping("/client/user")
public class ClientUserController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HTML_PREFIX = "client/user/";
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientRoleService clientRoleService;
	@Autowired
	private BaseResultView fastJsonResultView;
	
	@PreAuthorize("hasAuthority('client:user')")
	@GetMapping(value = {"/",""})
	public String user() {
		return HTML_PREFIX + "user-list";
	}
	
	/**
	 * 分页查询用户列表 
	 * @param page 分页对象：size，current
	 * @param clientUser 查询条件：username，mobile
	 * @return
	 */
	@PreAuthorize("hasAuthority('client:user:list')")
	@PostMapping("/page")
	@ResponseBody
	public String page(Page<ClientUser> page,ClientUser clientUser) {
		return fastJsonResultView.ok(new ResultView().success(clientUserService.selectPage(page, clientUser)));
	}
	
	/**
	 * 跳转新增或修改页面
	 * /form 新增 
	 * /form/{id} 修改  
	 * @PathVariable(required = false) 设置为false，则id可不传，为true，则必须传id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('client:user:add','client:user:edit')")
	@GetMapping(value = {"/form","/form/{id}"})
	public String form(@PathVariable(required = false) Long id, Model model) {
		// 1，通过用户id信息，并包含了用户拥有的角色
		ClientUser user = clientUserService.findById(id);
		model.addAttribute("user", user == null ? new ClientUser() : user);
		// 2，查询出所有的角色信息
		List<ClientRole> roleList = clientRoleService.list();
		model.addAttribute("roleList", roleList);
		// 绑定后页面可获取
		return HTML_PREFIX + "user-form";
	}
	
	@PreAuthorize("hasAnyAuthority('client:user:add','client:user:edit')")
	@RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST})
	public String saveOrUpdate(ClientUser sysUser) {
		// 保存或更新到用户表，要将选中的角色保存或更新到用户角色中间表 sys_user_role
		clientUserService.saveOrUpdate(sysUser);
		return "redirect:/" + HTML_PREFIX;
	}
	
	@PreAuthorize("hasAuthority('client:user:delete')")
	@DeleteMapping("/{id}")
	@ResponseBody
	public String deleteById(@PathVariable Long id) {
		// 逻辑删除，只做更新
		clientUserService.deleteById(id);
		return fastJsonResultView.ok(new ResultView().success());
	}
	
	
}
