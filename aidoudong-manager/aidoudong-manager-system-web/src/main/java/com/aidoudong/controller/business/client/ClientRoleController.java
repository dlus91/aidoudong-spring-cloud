package com.aidoudong.controller.business.client;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import com.aidoudong.entity.business.ClientRole;
import com.aidoudong.service.business.client.ClientRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/client/role")
public class ClientRoleController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HTML_PREFIX = "client/role/";
	@Autowired
	private ClientRoleService clientRoleService;
	@Autowired
	private BaseResultView fastJsonResultView;
	
	@PreAuthorize("hasAuthority('client:role')")
	@GetMapping(value = {"/",""})
	public String role() {
		return HTML_PREFIX + "role-list";
	}
	
	@PreAuthorize("hasAuthority('client:role:list')")
	@PostMapping("/page")
	@ResponseBody
	public String page(Page<ClientRole> page, ClientRole sysRole) {
		return fastJsonResultView.ok(ResultView.success(clientRoleService.selectPage(page, sysRole)));
	}
	
	/**
	 * 跳转新增或修改页面
	 * /form 新增 
	 * /form/{id} 修改  
	 * @PathVariable(required = false) 设置为false，则id可不传，为true，则必须传id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('client:role:add','client:role:edit')")
	@GetMapping(value = {"/form","/form/{id}"})
	public String form(@PathVariable(required = false) Long id, Model model) {
		// 1，通过角色id查询对应权限信息
		ClientRole role = clientRoleService.findById(id);
		// 绑定后页面可获取
		model.addAttribute("role",role == null ? new ClientRole() : role);
		return HTML_PREFIX + "role-form";
	}
	
	/**
	 * 提交新增或者修改的数据
	 * @param clientRole
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('client:role:add','client:role:edit')")
	@RequestMapping(value="", method = {RequestMethod.PUT,RequestMethod.POST})
	public String saveOrUpdate(ClientRole clientRole) {
		clientRoleService.saveOrUpdate(clientRole);
		return "redirect:/" + HTML_PREFIX;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('client:role:delete')")
	@DeleteMapping("/{id}")
	@ResponseBody
	public String deleteById(@PathVariable Long id) {
		clientRoleService.deleteById(id);
		return fastJsonResultView.ok(ResultView.success());
	}
}
