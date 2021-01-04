package com.aidoudong;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author: dlus91
 * @Date: 2020/10/28 14:27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExceptionTest {

    @Autowired
    private BaseResultView fastJsonResultView;

    @Test
    public void test01(){
        System.out.println("=========数组========");
        String[] strArray = new String[]{"a","b","c","d"};
        System.out.println(fastJsonResultView.data(ResultView.of(500,"测试数组异常",strArray)));

        System.out.println("=========字符串========");
        System.out.println(fastJsonResultView.data(ResultView.of(500,"测试字符串异常","测试测试测试字符串")));

        System.out.println("=========集合1========");
        List<String> list = Arrays.asList("a", "b", "c", "d");
        System.out.println(fastJsonResultView.data(ResultView.of(500,"测试集合1异常",list)));
        System.out.println("=========集合2========");
//        Map.of jdk11
        Map<String,Integer> map = new HashMap();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        map.put("d",4);
        System.out.println(fastJsonResultView.data(ResultView.of(500,"测试集合2异常",map)));
    }


}
