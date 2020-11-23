package com.aidoudong.common.result;

import aidoudong.common.resultview.AbstractResultView;
import com.aidoudong.common.utils.PropertiesEnum;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/10/27 21:39
 */
public final class ResultViewBuilder extends AbstractResultView {

	public final static String SUCCESS_MESSAGE = "SUCCESS";
	public final static String FAIL_MESSAGE = "FAIL";

	private ResultViewBuilder(int code, String message, Object data, String dateFormatter, Map<String, String> codeMap) {
		super(code, message, data, dateFormatter, codeMap);
	}

    public static ResultViewBuilder of(int code, String message, Object data) {
        return new ResultViewBuilder(code, message, data, null, null);
    }

	public static ResultViewBuilder of(int code, String message, Object data, String dateFormatter) {
		return new ResultViewBuilder(code, message, data, dateFormatter, null);
	}

	public static ResultViewBuilder of(int code, String message, Object data, Map<String, String> codeMap) {
		return new ResultViewBuilder(code, message, data, null, codeMap);
	}

	public static ResultViewBuilder of(int code, String message, Object data, String dateFormatter, Map<String, String> codeMap) {
		return new ResultViewBuilder(code, message, data, dateFormatter, codeMap);
	}

	public static ResultViewBuilder success(){
		return new ResultViewBuilder(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, null, null);
	}

    public static ResultViewBuilder success(String message){
		return new ResultViewBuilder(HttpStatus.OK.value(), message, null, null, null);
	}

	public static ResultViewBuilder success(Object data){
		return new ResultViewBuilder(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data, null, null);
	}

	public static ResultViewBuilder fail(){
		String failMessage = String.join("_", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().split(" "));
		return new ResultViewBuilder(HttpStatus.INTERNAL_SERVER_ERROR.value(), failMessage, null, null, null);
	}

	public static ResultViewBuilder fail(String message){
		return new ResultViewBuilder(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null, null, null);
	}

	public static ResultViewBuilder fail(int code, String message){
		return new ResultViewBuilder(code, message, null, null, null);
	}

	@Override
    public String convertMessage(String message) {
		return PropertiesEnum.ERROR_CODE_EN.getProperty(message,message);
    }

}
