package aidoudong.common.resultview;

import com.alibaba.fastjson.JSONObject;

import java.beans.Transient;
import java.util.Map;

public abstract class AbstractResultView {

	public final static String SUCCESS_MESSAGE = "SUCCESS";
	public final static String FAIL_MESSAGE = "FAIL";
	
	private int code;
	private String message;
	private Object data;
	private String dateFomater;
	private Map<String, String> codeMap;

	public AbstractResultView() {}

	public AbstractResultView(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public AbstractResultView(int code, String message, Object data, String dateFomater) {
		this.code = code;
		this.message = convertMessage(message);
		this.data = data;
		this.dateFomater = dateFomater;
	}

	public AbstractResultView(int code, String message, Object data, Map<String, String> codeMap) {
		this.code = code;
		this.message = convertMessage(message);
		this.data = data;
		this.codeMap = codeMap;
	}

	public AbstractResultView(int code, String message, Object data, String dateFomater, Map<String, String> codeMap) {
		this.code = code;
		this.message = convertMessage(message);
		this.data = data;
		this.dateFomater = dateFomater;
		this.codeMap = codeMap;
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
	public void setCode(int code) {
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
	@Transient
	public String getDateFomater() {
		return dateFomater;
	}
	public void setDateFomater(String dateFomater) {
		this.dateFomater = dateFomater;
	}
	@Transient
	public Map<String, String> getCodeMap() {
		return codeMap;
	}
	public void setCodeMap(Map<String, String> codeMap) {
		this.codeMap = codeMap;
	}
	
}
