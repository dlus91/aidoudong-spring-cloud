package com.aidoudong.properties;

import aidoudong.common.resultview.AbstractResultView;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/10/29 19:46
 */
public class SimpleResultView extends AbstractResultView {

    public final static String SUCCESS_MESSAGE = "SUCCESS";
    public final static String FAIL_MESSAGE = "FAIL";

    private SimpleResultView(int code, String message, Object data, String dateFormater, Map<String, String> codeMap) {
        super(code, message, data, dateFormater, codeMap);
    }

    public static SimpleResultView of(int code, String message, Object data) {
        return new SimpleResultView(code, message, data, null, null);
    }

    public static SimpleResultView of(int code, String message, Object data, String dateFormater) {
        return new SimpleResultView(code, message, data, dateFormater, null);
    }

    public static SimpleResultView of(int code, String message, Object data, Map<String, String> codeMap) {
        return new SimpleResultView(code, message, data, null, codeMap);
    }

    public static SimpleResultView of(int code, String message, Object data, String dateFormater, Map<String, String> codeMap) {
        return new SimpleResultView(code, message, data, dateFormater, codeMap);
    }

    public static SimpleResultView success(){
        return new SimpleResultView(200, SUCCESS_MESSAGE, null, null, null);
    }

    public static SimpleResultView success(String message){
        return new SimpleResultView(200, message, null, null, null);
    }

    public static SimpleResultView success(Object data){
        return new SimpleResultView(200, SUCCESS_MESSAGE, data, null, null);
    }

    public static SimpleResultView fail(){
        return new SimpleResultView(500, FAIL_MESSAGE, null, null, null);
    }

    public static SimpleResultView fail(String message){
        return new SimpleResultView(500, message, null, null, null);
    }

    public static SimpleResultView fail(int code, String message){
        return new SimpleResultView(code, message, null, null, null);
    }

    @Override
    public String convertMessage(String message) {
        return message;
    }
}
