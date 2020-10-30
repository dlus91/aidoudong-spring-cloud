package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductApiApp implements WebMvcConfigurer{

	public static void main(String[] args) {
    	new SpringApplicationBuilder(ProductApiApp.class).web(WebApplicationType.SERVLET).run(args);
    	System.out.println("=========start===========");
	}

}
