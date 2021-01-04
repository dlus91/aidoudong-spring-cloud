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

    default Map<String, Map<String,String>> getDictionaryCode() {
        Map<String, Map<String,String>> refreshCodeMap = new HashMap<>();
        Map<String,String> dictionaryMap = this.findDictionaryMap();
        for(Map.Entry<String, String> entry : dictionaryMap.entrySet()){
            String value = entry.getValue();
            String[] valueArray = value.split(",");
            Map<String, String> valueMap = new HashMap<>();
            for(String singleValue : valueArray) {
                String[] singleValueArray = singleValue.split("\\|");
                valueMap.put(singleValueArray[0], singleValueArray[1]);
            }
            refreshCodeMap.put(entry.getKey(), valueMap);
        }
        return Collections.unmodifiableMap(refreshCodeMap);
    }

    default Map<String,String> findDictionaryMap() {
        Map<String,String> resultMap = new HashMap<>();
        try(
            Connection connection = getDataSource().getConnection();
            PreparedStatement psst = connection.prepareStatement("select * from "+getTableName());
        ){
            ResultSet rs = psst.executeQuery();
            while (rs.next()) {
                resultMap.put(rs.getString("code"), rs.getString("value"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return resultMap;
    }

}
