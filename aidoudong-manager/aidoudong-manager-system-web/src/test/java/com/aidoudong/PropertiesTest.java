package com.aidoudong;

import com.aidoudong.common.utils.PropertiesEnum;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

        System.out.println("=========================");
        String key = "USERNAME_NULL2";
        System.out.println(PropertiesEnum.ERROR_CODE_EN.getProperty("USERNAME_NULL"));
        System.out.println(PropertiesEnum.ERROR_CODE_EN.getProperty(key,key));
        System.out.println(PropertiesEnum.ERROR_CODE_EN.getOrDefault("USERNAME_NULL3","嘎嘎"));
    }
    
    @Test
    public void test02(){
        String aaa = "色情书籍，泽东可能也看了";

        System.out.println("=========================");

        System.out.println(PropertiesEnum.SENSITIVE_CODE.getProperty("色情"));
        System.out.println(PropertiesEnum.SENSITIVE_CODE.getProperty("啊泽东"));

        System.out.println(PropertiesEnum.SENSITIVE_CODE.replaceAll(aaa));
        System.out.println(PropertiesEnum.SENSITIVE_CODE.replace(aaa,"泽东"));
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
