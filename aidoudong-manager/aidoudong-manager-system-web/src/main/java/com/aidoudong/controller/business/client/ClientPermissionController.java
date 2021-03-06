package com.aidoudong.controller.business.client;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import com.aidoudong.entity.business.ClientPermission;
import com.aidoudong.service.business.client.ClientPermissionService;
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
 */

@Controller
@RequestMapping("/client/permission")
public class ClientPermissionController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HTML_PREFIX = "client/permission/";
	
	@Autowired
	private ClientPermissionService clientPermissionService;
	@Autowired
	private BaseResultView fastJsonResultView;
	
	@PreAuthorize("hasAuthority('client:permission')")
	@GetMapping(value = {"/",""})
	public String permission() {
		return HTML_PREFIX + "permission-list";
	}
	
	@PreAuthorize("hasAuthority('client:permission:list')")
	@PostMapping("/list")
	@ResponseBody
	public String list() {
		// Mybatis-plus已经提供的，查询SysPermission表中的所有记录
		List<ClientPermission> list = clientPermissionService.list();
		return fastJsonResultView.data(ResultView.success(list));
	}
	
	/**
	 * 跳转新增或修改页面
	 * /form 新增 
	 * /form/{id} 修改  
	 * @PathVariable(required = false) 设置为false，则id可不传，为true，则必须传id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('client:permission:add','client:permission:edit')")
	@GetMapping(value = {"/form","/form/{id}"})
	public String form(@PathVariable(required = false) Long id, Model model) {
		// 1，通过权限id查询对应权限信息
		ClientPermission permission = clientPermissionService.getById(id);
		// 绑定后页面可获取
		model.addAttribute("permission",permission == null ? new ClientPermission() : permission);
		return HTML_PREFIX + "permission-form";
	}
	
	/**
	 * 提交新增或修改的数据
	 * @param permission
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('client:permission:add','client:permission:edit')")
	@RequestMapping(value = "", method = {RequestMethod.PUT, RequestMethod.POST})
	public String saveOrUpdate(ClientPermission permission) {
		clientPermissionService.saveOrUpdate(permission);
		return "redirect:/" + HTML_PREFIX;
	}
	
	@PreAuthorize("hasAuthority('client:permission:delete')")
	@DeleteMapping("/{id}")
	@ResponseBody
	public String deleteById(@PathVariable Long id) {
		clientPermissionService.deleteById(id);
		return fastJsonResultView.data(ResultView.success());
	}
	
	
}
