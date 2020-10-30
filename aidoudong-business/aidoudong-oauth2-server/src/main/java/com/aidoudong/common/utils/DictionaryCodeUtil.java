package com.aidoudong.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Iterator;
import java.util.Map;

@Component
public class DictionaryCodeUtil {
	
	static Logger logger = LoggerFactory.getLogger(DictionaryCodeUtil.class);
	
	private static Map<String, Object> codeMap = new HashMap<String,Object>();
	private static final String jsonObjectType = "JSONObject";
	private static final String jsonArrayType = "JSONOArray";
	private static final String pageMybatisPlusType = "MybatisPlusPage";
	private static final String pageJpaType = "JpaPage";
	private static final String jsonNoknowType = "unknow";
	private static final String resultConvertCode = "code";
	
	public static String tableName = "dictionary_code";
	
	@Autowired
	@Qualifier("oauth2DataSource")
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
	
	public Map<String, Object> getCodeAll(){
		return codeMap;
	}
	
	@SuppressWarnings("unchecked")
	public String getValue(String code,String valueCode) {
		Map<String, String> simpleCodeMap = (HashMap<String, String>) codeMap.get(code);
		if(simpleCodeMap == null || simpleCodeMap.isEmpty()){ return ""; }
		String resultValue = simpleCodeMap.get(valueCode);
		return null == resultValue ? "" : resultValue;
	}
	
	public Object codeCovert(Object data,Map<String, String> code) {
		String type = this.validResultType(data);
		if(pageMybatisPlusType.equals(type)) { // mybatis-plus
			JSONObject pageJson = (JSONObject) JSONObject.toJSON(data);
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(pageJson.get("records"));
			Iterator<Object> iterator = jsonArray.iterator();
	        while (iterator.hasNext()){
	        	JSONObject jsonObject = (JSONObject) iterator.next();
	        	this.handlerResultCode(jsonObject,code);
	        }
	        data = jsonArray;
		}else if(pageJpaType.equals(type)) { // jpa
			JSONObject pageJson = (JSONObject) JSONObject.toJSON(data);
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(pageJson.get("content"));
			Iterator<Object> iterator = jsonArray.iterator();
	        while (iterator.hasNext()){
	        	JSONObject jsonObject = (JSONObject) iterator.next();
	        	this.handlerResultCode(jsonObject,code);
	        }
	        data = jsonArray;
		}else if(jsonArrayType.equals(type)) { // array
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(data);
			Iterator<Object> iterator = jsonArray.iterator();
	        while (iterator.hasNext()){
	        	JSONObject jsonObject = (JSONObject) iterator.next();
	        	this.handlerResultCode(jsonObject,code);
	        }
	        data = jsonArray;
		}else if(jsonObjectType.equals(type)){ // map
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(data);
			this.handlerResultCode(jsonObject,code);
			data = jsonObject;
		}
		return data;
	}
	
	private void handlerResultCode(JSONObject json,Map<String, String> codeMap) {
		for (Map.Entry<String, String> entry : codeMap.entrySet()) {
			if(json.containsKey(entry.getKey())){
				if(!json.containsKey(resultConvertCode)) {
					json.put(resultConvertCode, new JSONObject());
				}
				JSONObject codeJson = (JSONObject) json.get(resultConvertCode);
				codeJson.put(entry.getKey(), this.getValue(entry.getValue(), json.getString(entry.getKey())));
			}
		}
	}
	
	private String validResultType(Object object) {
		if(object instanceof com.baomidou.mybatisplus.extension.plugins.pagination.Page) {
			return pageMybatisPlusType;
		}else if(object instanceof org.springframework.data.domain.Page) {
			return pageJpaType;
		}
		String resultStr = jsonNoknowType;
		String fristStr = object.toString().trim().split("")[0];
		if("[".equals(fristStr)) {
			resultStr = jsonArrayType;
		}else if("{".equals(fristStr)) {
			resultStr = jsonObjectType;
		}else {
			resultStr = jsonObjectType;
		}
		return resultStr;
	}

	
}
