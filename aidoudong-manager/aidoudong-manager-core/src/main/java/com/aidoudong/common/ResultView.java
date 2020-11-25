package com.aidoudong.common;

import aidoudong.common.resultview.AbstractResultView;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/10/27 21:39
 */
public final class ResultView extends AbstractResultView {

	public final static String SUCCESS_MESSAGE = "SUCCESS";
	public final static String FAIL_MESSAGE = "FAIL";

	private ResultView(int code, String message, Object data, String dateFormatter, Map<String, String> codeMap) {
		super(code, message, data, dateFormatter, codeMap);
	}

    public static ResultView of(int code, String message, Object data) {
        return new ResultView(code, message, data, null, null);
    }

	public static ResultView of(int code, String message, Object data, String dateFormatter) {
		return new ResultView(code, message, data, dateFormatter, null);
	}

	public static ResultView of(int code, String message, Object data, Map<String, String> codeMap) {
		return new ResultView(code, message, data, null, codeMap);
	}

	public static ResultView of(int code, String message, Object data, String dateFormatter, Map<String, String> codeMap) {
		return new ResultView(code, message, data, dateFormatter, codeMap);
	}

	public static ResultView success(){
		return new ResultView(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null, null);
	}

    public static ResultView success(String message){
		return new ResultView(HttpStatus.OK.value(), message, null, null, null);
	}

	public static ResultView success(Object data){
		return new ResultView(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data, null, null);
	}

	public static ResultView fail(){
		String failMessage = String.join("_", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().split(" "));
		return new ResultView(HttpStatus.INTERNAL_SERVER_ERROR.value(), failMessage, null, null, null);
	}

	public static ResultView fail(String message){
		return new ResultView(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null, null, null);
	}

	public static ResultView fail(int code, String message){
		return new ResultView(code, message, null, null, null);
	}

	@Override
    public String convertMessage(String message) {
		return message;
    }

}
