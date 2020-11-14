package com.aidoudong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: dlus91
 * @Date: 2020/11/13 23:30
 */
public enum PropertiesEnum {

    ERROR_CODE_EN("config/application-errorCodeEn.properties");

    private static final Logger logger = LoggerFactory.getLogger(PropertiesEnum.class);
    private final String location;
    private Properties properties;
    static {
        for (PropertiesEnum simple : PropertiesEnum.values()){
            if(simple.properties == null){
                simple.properties = PropertiesEnum.getProperties(simple.location);
            }
        }
    }

    PropertiesEnum(String location) {
        this.location = location;
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

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public String getProperty(String key,String defaultValue) {
        return this.properties.getProperty(key,defaultValue);
    }

    public String getOrDefault(String key,String defaultValue) {
        return (String) this.properties.getOrDefault(key,defaultValue);
    }

    public String replace(String str,String key){
        String value = this.properties.getProperty(key);
        if(value == null){ return str; }
        return str.replaceAll(key, value);
    }

    public String replaceAll(String str){
        Set<Object> keys = this.properties.keySet();
        for (Object key : keys){
            String strKey = key.toString();
            str = str.replaceAll(strKey, this.properties.getProperty(strKey));
        }
        return str;
    }

    @Override
    public String toString() {
        return this.location;
    }

}
