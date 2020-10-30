package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableZuulProxy
public class ZuulApplicationApp {
	
    public static void main(String[] args){
    	new SpringApplicationBuilder(ZuulApplicationApp.class).web(WebApplicationType.SERVLET).run(args);
    	System.out.println("==========start===========");
    }
    
}