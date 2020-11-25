package com.aidoudong.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aidoudong.common.utils.AssertUtil;
import com.aidoudong.product.entity.Product;
import com.aidoudong.product.service.ProductService;

@RestController
@RequestMapping("/test")
public class TestControler {
	
	@Autowired
	private ProductService productServiceImpl;
	@Autowired
	private BaseResultView fastJsonResultView;
	
	@GetMapping("/findAll/include")
	public String include() {
		ResultView resultView = ResultView.of(200, ResultView.SUCCESS_MESSAGE,productServiceImpl.findAll());
		return fastJsonResultView.include(
				resultView,
				new String[] {"pname","type","price"});
	}
	
	@GetMapping("/findAll/include2")
	public String include2() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("type", "product_type");
		ResultView resultView = ResultView.of(200, ResultView.SUCCESS_MESSAGE,productServiceImpl.findAll(),map);
		return fastJsonResultView.include(resultView,
				new String[] {"pname","type","price","createTime"});
	}
	
	@GetMapping("/findAll/exclude")
	public String exclude() {
		ResultView resultView = ResultView.of(200, ResultView.SUCCESS_MESSAGE,productServiceImpl.findAll());
		return fastJsonResultView.exclude(
				resultView,
				new String[] {"price","createTime"});
	}
	
	@GetMapping("/findAll/exclude2")
	public String exclude2() {
		ResultView resultView = ResultView.of(200, ResultView.SUCCESS_MESSAGE,productServiceImpl.findAll(),"yyyy-MM-dd");
		return fastJsonResultView.exclude(
				resultView,
				new String[] {"price"});
	}
	
	@GetMapping("/fail1")
	public void fail1() {
		AssertUtil.notNull(null, "USERNAME_PATTERN");
	}
	
	@PostMapping("/fail2")
	public void fail2(@RequestBody @Valid Product product) {
		
		System.out.println("==========fail2==========");
	}
	
}
