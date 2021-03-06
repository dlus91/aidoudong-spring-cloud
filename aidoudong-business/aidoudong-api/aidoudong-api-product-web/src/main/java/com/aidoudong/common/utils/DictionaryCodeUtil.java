package com.aidoudong.common.utils;

import aidoudong.common.utils.BaseDictionaryCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public final class DictionaryCodeUtil implements BaseDictionaryCodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryCodeUtil.class);
	private static Map<String, Map<String,String>> codeMap = new HashMap<>();

	@Resource(name = "oauth2DataSource")
	private DataSource dataSource;

	@PostConstruct
	public void refresh() {
		codeMap = getDictionaryCode();
		logger.info("字典刷新成功:{} data: {}",getTableName(),codeMap);
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public String getTableName() {
		return "dictionary_code";
	}

	public static Map<String, Map<String,String>> getCodeMap() {
		return codeMap;
	}
	
	public static String getValue(String code,String valueCode) {
		Map<String, String> singleCodeMap = codeMap.get(code);
		if(singleCodeMap == null || singleCodeMap.isEmpty()){ return ""; }
		String resultValue = singleCodeMap.get(valueCode);
		return null == resultValue ? "" : resultValue;
	}
	
}
