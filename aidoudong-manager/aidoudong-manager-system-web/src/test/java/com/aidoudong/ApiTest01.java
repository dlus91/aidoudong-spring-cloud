package com.aidoudong;

import com.aidoudong.common.utils.PropertiesEnum;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.UnaryOperator;

/**
 * @Author: dlus91
 * @Date: 2020/9/22 12:52
 */
@SpringBootTest
public class ApiTest01 {

    public static <E> List<E> union(List<E> s1, List<E> s2){
        ArrayList<E> result = new ArrayList<>(s1);
        result.addAll(s2);
        return result;
    }

    @Test
    public void test01(){
        List<String> guys = Arrays.asList("Tom", "Dick", "Harry");
        List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
        List<String> aflCio = union(guys, stooges);
        System.out.println(aflCio);
    }

    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    public static <T> UnaryOperator<T> identityFunction(){
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    @Test
    public void test02(){
        String[] strings = {"jute","hemp","nylon"};
        UnaryOperator<String> sameString = identityFunction();
        for (String s : strings){
            System.out.println(sameString.apply(s));
        }

        System.out.println("==========================");

        Number[] numbers = {1,2.0,3L};
        UnaryOperator<Number> sameNumber = identityFunction();
        for (Number n : numbers){
            System.out.println(sameNumber.apply(n));
        }
    }

    public static <E extends Comparable<E>> E max(Collection<E> c){
        if(c.isEmpty()){
            throw new IllegalArgumentException("Empty collection");
        }
        E result = null;
        for (E e : c){
            if(result == null || e.compareTo(result) > 0){
                result = Objects.requireNonNull(e);
            }
        }
        return result;
    }

    @Test
    public void test03(){
        Integer max = max(Arrays.asList(20, 30, 5, 50, 40, 200, 150, 120, 110, 125));
        System.out.println(max);
    }

    @Test
    public void test04(){
        BigDecimal bigDecimal = new BigDecimal(22.3);
        BigDecimal add = bigDecimal.add(BigDecimal.valueOf(3.2));
        BigDecimal plus = add.plus(new MathContext(5, RoundingMode.HALF_UP));
        System.out.println("============================");
        System.out.println(plus);
        System.out.printf("negate():%s\n", plus.negate());
        System.out.printf("precision():%s\n", plus.precision());
        System.out.printf("scale():%s\n", plus.scale());
        System.out.printf("signum():%s\n", plus.signum());

    }

    @Test
    public void test05(){
        String aaa = "Internal Server Error";
        System.out.println(String.join("_", aaa.split(" ")));
    }

    @Test
    public void test06(){
        System.out.println(PropertiesEnum.ERROR_CODE_EN.getProperty("internal_server_error"));
    }


    @Test
    public void test07(){


    }



}

