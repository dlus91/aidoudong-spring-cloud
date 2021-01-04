package com.aidoudong.controller;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private BaseResultView fastJsonResultView;

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HTML_PREFIX = "system/user/";
	
	@PreAuthorize("hasAuthority('sys:user')")
	@GetMapping(value = {"/",""})
	public String user() {
		return HTML_PREFIX + "user-list";
	}
	
	/**
	 * 跳转到新增或者修改页面
	 * @return
	 */
	//有'sys:user:add', 'sys:user:edit'权限的用户可以访问
	@PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
	@GetMapping(value= {"/form"})
	public String form() {
		return HTML_PREFIX + "user-form";
	}
	
	/**
	 * 跳转到新增或者修改页面
	 * @return
	 */
	//返回值的code等于200，则调用成功有权限，否则把403返回
	@PostAuthorize("returnObject.code == 200")
	@GetMapping("/{id}") // /user/{id}
	@ResponseBody
	public String deleteById(@PathVariable Long id) {
		if(id < 0) {
			return fastJsonResultView.data(ResultView.fail("id不能小于0"));
		}
		return fastJsonResultView.data(ResultView.success());
	}
	
	// 过滤请求参数：fiterTarget指定哪个参数，filterObject是集合中的每个元素，
	// 如果value表达式为true的数据则不会被过滤，否则就过滤掉
	@PreFilter(filterTarget = "ids",value = "filterObject > 0")
	@GetMapping("/batch/{ids}") // /user/batch/{ids} -1,1,4
	@ResponseBody
	public String deleteByIds(@PathVariable List<Long> ids) {
		return fastJsonResultView.data(ResultView.success(ids));
	}
	
	// 过滤返回值：filterObject是返回值集合中的每一个元素，当表达式等于true则对应元素返回
	@PostFilter("filterObject != authentication.principal.username")
	@GetMapping("/list") // /user/list/{ids} -1,1,4
	@ResponseBody
	public List<String> page() {
		List<String> userList = Lists.newArrayList("ai","dou","dong","admin");
		return userList;
	}
	
	
}
