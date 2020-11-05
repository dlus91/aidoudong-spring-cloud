package com.aidoudong.properties;

import aidoudong.common.resultview.AbstractResultView;
import aidoudong.common.resultview.BaseSimpleResultView;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author: dlus91
 * @Date: 2020/10/29 19:46
 */
public class SimpleResultView extends AbstractResultView {
//    public String ok(String message) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", 200);
//        jsonObject.put("message",message);
//        return jsonObject.toJSONString();
//    }
//
//    public String build(int code, String message) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", code);
//        jsonObject.put("message",message);
//
//        return jsonObject.toJSONString();
//    }



    @Override
    public String convertMessage(String message) {
        return message;
    }
}
