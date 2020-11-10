package com.aidoudong.product.controller;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultViewBuilder;
import com.aidoudong.common.utils.DictionaryCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseResultView fastJsonResultView;
	@Autowired
	private DictionaryCodeUtil dictionaryCodeUtil;
	
	@GetMapping("/refresh/dectionary")
	@ResponseBody
	public String refreshDectionaryTable() {
		dictionaryCodeUtil.refresh();
		return fastJsonResultView.ok(ResultViewBuilder.success());
	}
	
}
