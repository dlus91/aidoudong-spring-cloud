package com.aidoudong.product.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductControler {

	@GetMapping("/list")
//	@PreAuthorize("hasAuthority('sys:role:list')")
//	@PreAuthorize("#oauth2.hasScope('all')")
	public String list() {
		
		List<String> list = new ArrayList<String>();
		list.add("眼界2");
		list.add("双肩包2");
		list.add("衬衫2");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 200);
		jsonObject.put("message", "成功");
		jsonObject.put("data", list);

		return jsonObject.toJSONString();
	}
	
}
