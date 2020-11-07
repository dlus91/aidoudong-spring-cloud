package com.aidoudong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class DictionaryCodeUtil {
	
	static Logger logger = LoggerFactory.getLogger(DictionaryCodeUtil.class);
	private static Map<String, Object> codeMap;

	public static String tableName = "dictionary_code";
	
	@Autowired
	@Qualifier("systemDataSource")
	private DataSource dataSource;

	@PostConstruct
	public void refresh() {
		Map refreshCodeMap = new HashMap<String,Object>();
		Map<String,String> dictionaryMap = this.findDictionaryMap();
		for(Map.Entry<String, String> entry : dictionaryMap.entrySet()){
			String value = entry.getValue();
			String[] valueArray = value.split(",");
			Map<String, String> valueMap = new HashMap<String, String>();
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
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
            // 获得连接:
            Connection connection = dataSource.getConnection();
            // 编写SQL:
            String sql = "select * from "+tableName;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // 执行sql:
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultMap.put(rs.getString("code"), rs.getString("value"));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return resultMap;
	}
	
	public static Map<String, Object> getCodeMap() {
		return codeMap;
	}
	
	@SuppressWarnings("unchecked")
	public static String getValue(String code,String valueCode) {
		Map<String, String> simpleCodeMap = (HashMap<String, String>) codeMap.get(code);
		if(simpleCodeMap == null || simpleCodeMap.isEmpty()){ return ""; }
		String resultValue = simpleCodeMap.get(valueCode);
		return null == resultValue ? "" : resultValue;
	}
	
}
