package com.aidoudong.common.result;

import aidoudong.common.resultview.AbstractResultView;
import com.aidoudong.common.utils.PropertiesUtil;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/10/27 21:39
 */
public class ResultViewBuilder extends AbstractResultView {

    private ResultViewBuilder() {
    }

    private ResultViewBuilder(int code, String message, Object data, String dateFormater, Map<String, String> codeMap) {
        super(code, message, data, dateFormater, codeMap);
    }

    public static ResultViewBuilder of(int code, String message, Object data) {
        return new ResultViewBuilder(code, message, data, null, null);
    }

    public static ResultViewBuilder of(int code, String message, Object data, String dateFormater) {
        return new ResultViewBuilder(code, message, data, dateFormater, null);
    }

    public static ResultViewBuilder of(int code, String message, Object data, Map<String, String> codeMap) {
        return new ResultViewBuilder(code, message, data, null, codeMap);
    }

    public static ResultViewBuilder of(int code, String message, Object data, String dateFormater, Map<String, String> codeMap) {
        return new ResultViewBuilder(code, message, data, dateFormater, codeMap);
    }

    public static ResultViewBuilder success(){
        return new ResultViewBuilder(200, SUCCESS_MESSAGE, null, null, null);
    }

    public static ResultViewBuilder success(String message){
        return new ResultViewBuilder(200, message, null, null, null);
    }

    public static ResultViewBuilder success(Object data){
        return new ResultViewBuilder(200, SUCCESS_MESSAGE, data, null, null);
    }

    public static ResultViewBuilder fail(){
        return new ResultViewBuilder(500, FAIL_MESSAGE, null, null, null);
    }

    public static AbstractResultView fail(String message){
        return new ResultViewBuilder(500, message, null, null, null);
    }

    public static AbstractResultView fail(int code, String message){
        return new ResultViewBuilder(code, message, null, null, null);
    }

    @Override
    public String convertMessage(String message) {
        String errorMessage = PropertiesUtil.getErrorCodeEnProperties().getProperty(message);
        if(errorMessage == null || "".equals(errorMessage)){
            return message;
        }
        return errorMessage;
    }
}