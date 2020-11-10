package com.aidoudong.product.feign;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultViewBuilder;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestFeignFallback implements TestFeignClient{

	@Autowired
	private BaseResultView fastJsonResultView;

	@Override
	public String findByIdJson() {
		
		return fastJsonResultView.ok(ResultViewBuilder.success(Lists.newArrayList("测试1","测试2")));
	}


}
