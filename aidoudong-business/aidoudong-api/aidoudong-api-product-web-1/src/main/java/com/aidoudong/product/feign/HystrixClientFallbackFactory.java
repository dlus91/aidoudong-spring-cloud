package com.aidoudong.product.feign;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import feign.hystrix.FallbackFactory;

//@Component
//public class HystrixClientFallbackFactory implements FallbackFactory<TestFeignClient>{
public class HystrixClientFallbackFactory {

//	@Override
//	public TestFeignClient create(Throwable cause) {
//		return new TestFeignClient() {
//
//			@Override
//			public JSONObject findByIdJson(Long id) {
//				JSONObject json = new JSONObject();
//				json.put("id", id);
//				json.put("name", "yyyy");
//				return json;
//			}
//		};
//	}		


}
