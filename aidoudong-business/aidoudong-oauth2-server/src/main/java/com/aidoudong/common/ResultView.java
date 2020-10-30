package com.aidoudong.common;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;

public class ResultView implements Serializable{

    private static final long serialVersionUID = -6584284303557656884L;

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public ResultView() {
    }
    public ResultView(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public ResultView(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public ResultView(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultView ok() {
        return new ResultView(null);
    }
    public static ResultView ok(String message) {
        return new ResultView(message, null);
    }
    public static ResultView ok(Object data) {
        return new ResultView(data);
    }
    public static ResultView ok(String message, Object data) {
        return new ResultView(message, data);
    }

    public static ResultView build(Integer code, String message) {
        return new ResultView(code, message, null);
    }

    public static ResultView build(Integer code, String message, Object data) {
        return new ResultView(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MengxueguResult 对象
     * @param json
     * @return
     */
    public static ResultView format(String json) {
        try {
            return JSON.parseObject(json, ResultView.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
