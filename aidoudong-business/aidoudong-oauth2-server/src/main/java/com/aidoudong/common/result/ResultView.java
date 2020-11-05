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

    @Override
    public AbstractResultView build(int code, String message, Object data) {
        return super.build(code, message, data);
    }

    @Override
    public AbstractResultView dateFormater(String dateFormater) {
        return super.dateFormater(dateFormater);
    }

    @Override
    public AbstractResultView codeMap(Map<String, String> codeMap) {
        return super.codeMap(codeMap);
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
