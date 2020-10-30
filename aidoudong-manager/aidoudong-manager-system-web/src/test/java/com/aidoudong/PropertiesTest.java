package com.aidoudong;

import com.aidoudong.common.utils.PropertiesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * @Author: dlus91
 * @Date: 2020/10/28 14:27
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class PropertiesTest {

    @Test
    public void test01(){
        Properties errorCode = PropertiesUtil.getErrorCodeEnProperties();
        System.out.println(errorCode.get("SUCCESS"));
        System.out.println(errorCode.getProperty("SUCCESS33"));
    }


}
