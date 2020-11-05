package com.aidoudong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
	
	static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties errorCodeEnProperties = null;
	private static Properties sensitiveCodeProperties = null;

	static{
		errorCodeEnProperties = PropertiesUtil.getProperties("config/application-errorCodeEn.properties");
		sensitiveCodeProperties = PropertiesUtil.getProperties("config/application-sensitiveCode.properties");
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

	public static String convertErrorCodeEnProperty(String str,String key){
		String value = getErrorCodeEnProperties().getProperty(key);
		if(value == null){ return str; }
		return str.replaceAll(key, value);
	}

	public static String convertSensitiveCodeProperty(String str,String key){
		String value = getSensitiveCodeProperties().getProperty(key);
		if(value == null){ return str; }
		return str.replaceAll(key, value);
	}


	public static String convertErrorCodeEnProperties(String str){
		Properties errorCodeEnProperties = getErrorCodeEnProperties();
		Set<Object> keys = errorCodeEnProperties.keySet();
		for (Object key : keys){
			String strKey = key.toString();
			str = str.replaceAll(strKey, errorCodeEnProperties.getProperty(strKey));
		}
		return str;
	}

	public static String convertSensitiveCodeProperties(String str){
		Properties sensitiveCodeProperties = getSensitiveCodeProperties();
		Set<Object> keys = sensitiveCodeProperties.keySet();
		for (Object key : keys){
			String strKey = key.toString();
			str = str.replaceAll(strKey, sensitiveCodeProperties.getProperty(strKey));
		}
		return str;
	}

	public static Properties getErrorCodeEnProperties(){
		return errorCodeEnProperties;
	}
	public static Properties getSensitiveCodeProperties(){
		return sensitiveCodeProperties;
	}

}
