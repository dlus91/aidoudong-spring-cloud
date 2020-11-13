package com.aidoudong;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    static <T> T[] toArray(T... args){
        return args;
    }

    static <T> T[] pickTwo(T a,T b,T c){
        switch (ThreadLocalRandom.current().nextInt(3)){
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError();
    }

    @Test
    public void test04(){
        String[] attributes = pickTwo("Good", "Fast", "Cheap");
        System.out.println(attributes);
    }



}

