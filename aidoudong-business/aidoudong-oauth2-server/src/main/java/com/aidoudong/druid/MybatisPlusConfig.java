package com.aidoudong.druid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
public class MybatisPlusConfig {
	
    //==================================↓↓↓是mybatis-plus↓↓↓==================================
    
    // mybatis-plus 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	return new PaginationInterceptor().setDialectType("mysql");
    }
	
}
