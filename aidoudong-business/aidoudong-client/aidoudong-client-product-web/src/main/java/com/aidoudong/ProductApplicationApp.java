package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductApplicationApp {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ProductApplicationApp.class)
			.web(WebApplicationType.SERVLET)
			.run(args);
		
		System.out.println("=========start===========");
	}
	
}
