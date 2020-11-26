package aidoudong.common.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/11/26 14:58
 */
public interface BaseDictionaryCodeUtil {

    DataSource getDataSource();

    String getTableName();

    default Map<String, Map<String,String>> getDictionaryCode() throws SQLException {
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
        return Collections.unmodifiableMap(refreshCodeMap);
    }

    default Map<String,String> findDictionaryMap() throws SQLException {
        Map<String,String> resultMap = new HashMap<>();
        Connection connection = getDataSource().getConnection();
        PreparedStatement psst = connection.prepareStatement("select * from "+getTableName());
        ResultSet rs = psst.executeQuery();
        psst.close();
        connection.close();
        while (rs.next()) {
            resultMap.put(rs.getString("code"), rs.getString("value"));
        }
        return resultMap;
    }

}
