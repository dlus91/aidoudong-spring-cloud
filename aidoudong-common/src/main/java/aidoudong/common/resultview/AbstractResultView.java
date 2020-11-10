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

	public AbstractResultView(int code, String message,
									Object data, String dateFormater,
									Map<String, String> codeMap){
		this.code = code;
		this.message = convertMessage(message);
		this.data = data;
		this.dateFormater = dateFormater;
		this.codeMap = codeMap;
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
