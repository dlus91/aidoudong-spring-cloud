package com.aidoudong.common.utils;

import aidoudong.common.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
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

    ERROR_CODE_EN("config/application-errorCodeEn.properties","错误码"),

    SENSITIVE_CODE("config/application-sensitiveCode.properties", "敏感字");

    private static final Logger logger = LoggerFactory.getLogger(PropertiesEnum.class);
    private final String location;
    private final String keyName;
    private Properties properties;
    static {
        for (PropertiesEnum simple : PropertiesEnum.values()){
            if(simple.properties == null){
                simple.properties = PropertiesEnum.getProperties(simple.location);
            }
        }
    }

    PropertiesEnum(String location, String keyName) {
        this.location = location;
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public static Properties getProperties(String location){
        Properties props = null;
        try {
            logger.info("加载资源[{}]",location);
            props = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(location),"UTF-8"));
        } catch (IOException e) {
            logger.error("加载资源[{}]失败",location);
            logger.error(e.getMessage());
        }
        return props;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        AssertUtil.isTrue(StringUtils.isNotEmpty(key),this.getKeyName()+"key为空或null");
        return this.properties.getProperty(key.toUpperCase());
    }

    public String getProperty(String key,String defaultValue) {
        AssertUtil.isTrue(StringUtils.isNotEmpty(key),this.getKeyName()+"key为空或null");
        return this.properties.getProperty(key.toUpperCase(),defaultValue);
    }

    public String getOrDefault(String key,String defaultValue) {
        AssertUtil.isTrue(StringUtils.isNotEmpty(key),this.getKeyName()+"key为空或null");
        return (String) this.properties.getOrDefault(key.toUpperCase(),defaultValue);
    }

    public String replace(String str,String key){
        AssertUtil.isTrue(StringUtils.isNotEmpty(str),this.getKeyName()+"语句为空或null");
        AssertUtil.isTrue(StringUtils.isNotEmpty(key),this.getKeyName()+"key为空或null");
        String value = this.properties.getProperty(key.toUpperCase());
        if(value == null){ return str; }
        return str.replaceAll(key, value);
    }

    public String replaceAll(String str){
        AssertUtil.isTrue(StringUtils.isNotEmpty(str),this.getKeyName()+"语句为空或null");
        Set<Object> keys = this.properties.keySet();
        for (Object key : keys){
            String strKey = key.toString();
            str = str.replaceAll(strKey.toUpperCase(), this.properties.getProperty(strKey));
        }
        return str;
    }

    @Override
    public String toString() {
        return this.keyName;
    }
}
