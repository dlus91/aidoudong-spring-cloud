package com.aidoudong.product.controller;

import java.util.ArrayList;
import java.util.List;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultViewBuilder;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aidoudong.product.entity.Product;
import com.aidoudong.product.feign.TestFeignClient;
import com.aidoudong.product.service.ProductService;

import brave.Tracer;

@RestController
@RequestMapping("/product")
public class ProductControler {

	@Autowired
	private TestFeignClient testFeignClient;
	@Autowired
	private ProductService productServiceImpl;
	@Autowired
	private BaseResultView fastJsonResultView;
	@Autowired
	private Tracer tracer;
	
	@GetMapping("/list")
//	@PreAuthorize("hasAuthority('sys:role:list')")
//	@PreAuthorize("#oauth2.hasScope('all')")
	public String list() {
		tracer.currentSpan().tag("name", "product-server -> product1-server");
		ResultViewBuilder resultView = JSONObject.parseObject(testFeignClient.findByIdJson(), ResultViewBuilder.class);
		@SuppressWarnings("unchecked")
		List<String> resultList = (List<String>) resultView.getData();
		List<String> list = new ArrayList<>();
		list.add("眼界1");
		list.add("双肩包1");
		list.add("衬衫1");
		
		resultList.addAll(list);
		
		return fastJsonResultView.ok(ResultViewBuilder.success(resultList));
	}
	
	@GetMapping("/id/{id}")
	public String getById(@PathVariable() Long id) {
		return fastJsonResultView.ok(ResultViewBuilder.success(productServiceImpl.findById(id)));
	}
	
	@GetMapping("/findAll")
	public String findAll() {
		return fastJsonResultView.ok(ResultViewBuilder.success(productServiceImpl.findAll()));
	}
	
	@PostMapping("/update")
	public String update(Product product) {
		productServiceImpl.update(product);
		return fastJsonResultView.ok(ResultViewBuilder.success());
	}
	
}
