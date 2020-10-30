package com.aidoudong.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aidoudong.filter.TokenFeignClientInterceptor;

//@FeignClient(name = "aidoudong-provider-user",fallback  = HystrixClientFallback.class)
@FeignClient(name = "AIDOUDONG-RESOURCE-PRODUCT-1",configuration = TokenFeignClientInterceptor.class)
public interface TestFeignClient {

//	//注意这里这能用requestMapping和参数要用pathVariable带参数
//	@RequestMapping(value = "/user/simple/{id}", method = RequestMethod.GET)
//	User findById(@PathVariable("id") Long id);
	
	
	//注意这里这能用requestMapping和参数要用pathVariable带参数
	@RequestMapping(value = "/api/product/list", method = RequestMethod.GET)
	String findByIdJson();
}

