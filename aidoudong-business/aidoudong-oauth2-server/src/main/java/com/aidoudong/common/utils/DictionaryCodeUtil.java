package com.aidoudong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public final class DictionaryCodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryCodeUtil.class);
	private static Map<String, Map<String,String>> codeMap = new HashMap<>();
	private static final String tableName = "dictionary_code";
	
	@Resource(name = "oauth2DataSource")
	private DataSource dataSource;

	@PostConstruct
	public void refresh() {
		Map<String, Map<String,String>> refreshCodeMap = new HashMap<>();
		Map<String,String> dictionaryMap = this.findDictionaryMap();
		for(Map.Entry<String, String> entry : dictionaryMap.entrySet()){
			String value = entry.getValue();
			String[] valueArray = value.split(",");
			Map<String, String> valueMap = new HashMap<>();
			for(String simpleValue : valueArray) {
				String[] simpleValueArray = simpleValue.split("\\|");
				valueMap.put(simpleValueArray[0], simpleValueArray[1]);
			}
			refreshCodeMap.put(entry.getKey(), valueMap);
		}
		codeMap = Collections.unmodifiableMap(refreshCodeMap);
		logger.info("字典刷新成功:{} data: {}",tableName,codeMap);
	}
	
	private Map<String,String> findDictionaryMap(){
		Map<String,String> resultMap = new HashMap<>();
		try {
            // 获得连接:
            Connection connection = dataSource.getConnection();
            // 编写SQL:
            String sql = "select * from "+tableName;
            PreparedStatement psst = connection.prepareStatement(sql);
            // 执行sql:
            ResultSet rs = psst.executeQuery();
            while (rs.next()) {
                resultMap.put(rs.getString("code"), rs.getString("value"));
            }
            psst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return resultMap;
	}

	public static Map<String, Map<String,String>> getCodeMap() {
		return codeMap;
	}
	
	public static String getValue(String code,String valueCode) {
		Map<String, String> simpleCodeMap = codeMap.get(code);
		if(simpleCodeMap == null || simpleCodeMap.isEmpty()){ return ""; }
		String resultValue = simpleCodeMap.get(valueCode);
		return null == resultValue ? "" : resultValue;
	}
	
}
