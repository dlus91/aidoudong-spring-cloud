package com.aidoudong;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class AdminApplicationApp {
	
    public static void main(String[] args){
    	new SpringApplicationBuilder(AdminApplicationApp.class).web(WebApplicationType.SERVLET).run(args);
    	System.out.println("==========start===========");
    }
    
}