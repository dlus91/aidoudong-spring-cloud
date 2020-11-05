package com.aidoudong.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class SensitiveCodeUtil {
	
	static Logger logger = LoggerFactory.getLogger(SensitiveCodeUtil.class);

	private static Properties sensitiveCodeProperties = null;

	static{
		sensitiveCodeProperties = SensitiveCodeUtil.getProperties("application-sensitiveCode.properties");
	}



	public static Properties getProperties(String location){
		Properties props = null;
		try {
			logger.info("加载资源[{}]",location);
			props = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(location),"UTF-8"));
		} catch (IOException e) {
			logger.error("加载资源[{}]失败",location);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return props;
	}

	public static Properties getSensitiveCodeProperties(){
		return sensitiveCodeProperties;
	}

}
