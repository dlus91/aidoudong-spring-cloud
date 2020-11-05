package com.aidoudong;

import com.aidoudong.common.utils.PropertiesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author: dlus91
 * @Date: 2020/10/28 14:27
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class PropertiesTest{

    @Test
    public void test01(){
        Properties errorCode = PropertiesUtil.getErrorCodeEnProperties();
        System.out.println(errorCode.get("SUCCESS"));
        System.out.println(errorCode.getProperty("SUCCESS33"));

        String aaa = "SUCCESS响应,但是出现了ID_NULL";
        System.out.println(PropertiesUtil.convertErrorCodeEnProperties(aaa));
        System.out.println(PropertiesUtil.convertErrorCodeEnProperty(aaa,"ID_NULL"));
    }
    
    @Test
    public void test02(){
        Properties sensitiveCode = PropertiesUtil.getSensitiveCodeProperties();
        System.out.println(sensitiveCode.getProperty("色情"));
        System.out.println(sensitiveCode.getProperty("毛泽东"));

        String aaa = "色情书籍，泽东可能也看了";
        System.out.println(PropertiesUtil.convertSensitiveCodeProperties(aaa));
        System.out.println(PropertiesUtil.convertSensitiveCodeProperty(aaa,"泽东"));
    }

    @Test
    public void test04(){
    }

    @Test
    public void test03(){
        Map map = new HashMap();
        map.putIfAbsent("a", 1);
        map.putIfAbsent("b", 2);
        map.putIfAbsent("c", 3);
        map.putIfAbsent("d", 4);
        map.putIfAbsent("e", 5);
        map.putIfAbsent("b", 50);
        map.computeIfAbsent("c1", o -> o+"999");
        System.out.println(map.toString());
    }

}
