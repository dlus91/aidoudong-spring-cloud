package com.aidoudong.common.result;

import aidoudong.common.resultview.AbstractResultView;
import com.aidoudong.common.utils.PropertiesEnum;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2020/10/27 21:39
 */
public final class ResultView extends AbstractResultView {

	public final static String SUCCESS_MESSAGE = "SUCCESS";
	public final static String FAIL_MESSAGE = "FAIL";
	//时间格式是否统一标准，null不受控制，非null则强制统一标准
	private final static String _dateFormatter = null;

	private ResultView(int code, String message, Object data, String dateFormatter, Map<String, String> codeMap) {
		super(code, message, data, dateFormatter, codeMap);
	}

    public static ResultView of(int code, String message, Object data) {
        return new ResultView(code, message, data, _dateFormatter, null);
    }

	public static ResultView of(int code, String message, Object data, String dateFormatter) {
		return new ResultView(code, message, data, dateFormatter, null);
	}

	public static ResultView of(int code, String message, Object data, Map<String, String> codeMap) {
		return new ResultView(code, message, data, _dateFormatter, codeMap);
	}

	public static ResultView of(int code, String message, Object data, String dateFormatter, Map<String, String> codeMap) {
		return new ResultView(code, message, data, dateFormatter, codeMap);
	}

	public static ResultView success(){
		return new ResultView(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null, _dateFormatter, null);
	}

    public static ResultView success(String message){
		return new ResultView(HttpStatus.OK.value(), message, null, _dateFormatter, null);
	}

	public static ResultView success(Object data){
		return new ResultView(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data, _dateFormatter, null);
	}

	public static ResultView fail(){
		String failMessage = String.join("_", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().split(" "));
		return new ResultView(HttpStatus.INTERNAL_SERVER_ERROR.value(), failMessage, null, _dateFormatter, null);
	}

	public static ResultView fail(String message){
		return new ResultView(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null, _dateFormatter, null);
	}

	public static ResultView fail(int code, String message){
		return new ResultView(code, message, null, _dateFormatter, null);
	}

	@Override
    public String convertMessage(String message) {
		return PropertiesEnum.ERROR_CODE_EN.getProperty(message,message);
    }

}
