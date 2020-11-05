package aidoudong.common.resultview;

import com.alibaba.fastjson.JSONObject;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Map;

public abstract class AbstractResultView {

	private static final long serialVersionUID = 4325431521204380876L;

	public final static String SUCCESS_MESSAGE = "SUCCESS";
	public final static String FAIL_MESSAGE = "FAIL";
	
	private int code;
	private String message;
	private Object data;
	private String dateFormater;
	private Map<String, String> codeMap;

	public AbstractResultView() {}

	public AbstractResultView(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public AbstractResultView build(int code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
		return this;
	}

	public AbstractResultView dateFormater(String dateFormater){
		this.dateFormater = dateFormater;
		return this;
	}

	public AbstractResultView codeMap(Map<String, String> codeMap){
		this.codeMap = codeMap;
		return this;
	}

	public AbstractResultView success(){
		this.code = 200;
		this.message = convertMessage(SUCCESS_MESSAGE);
		return this;
	}

	public AbstractResultView success(Object data){
		this.code = 200;
		this.message = convertMessage(SUCCESS_MESSAGE);
		this.data = data;
		return this;
	}

	public AbstractResultView fail(){
		this.code = 500;
		this.message = convertMessage(FAIL_MESSAGE);
		return this;
	}

	public AbstractResultView fail(String message){
		this.code = 500;
		this.message = convertMessage(message);
		return this;
	}

	public AbstractResultView fail(int code, String message){
		this.code = code;
		this.message = convertMessage(message);
		return this;
	}

	public abstract String convertMessage(String message);
	
	public String outPutData() {
		return JSONObject.toJSONString(this);
	}
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public Object getData() {
		return data;
	}

	@Transient
	public String getDateFomater() {
		return dateFormater;
	}
	@Transient
	public Map<String, String> getCodeMap() {
		return codeMap;
	}

}
