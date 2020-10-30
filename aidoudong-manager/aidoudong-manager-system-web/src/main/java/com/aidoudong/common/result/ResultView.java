package com.aidoudong.common.result;

import aidoudong.common.resultview.AbstractResultView;
import com.aidoudong.common.utils.PropertiesUtil;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/10/27 21:39
 */
public class ResultView extends AbstractResultView {

    public ResultView() {
    }

    public ResultView(int code, String message, Object data) {
        super(code, message, data);
    }

    public ResultView(int code, String message, Object data, String dateFomater) {
        super(code, message, data, dateFomater);
    }

    public ResultView(int code, String message, Object data, Map<String, String> codeMap) {
        super(code, message, data, codeMap);
    }

    public ResultView(int code, String message, Object data, String dateFomater, Map<String, String> codeMap) {
        super(code, message, data, dateFomater, codeMap);
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
