package aidoudong.common.resultview;

import com.alibaba.fastjson.JSONObject;

import java.beans.Transient;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: dlus91
 * @Date: 2020/8/21 11:30
 */
public abstract class AbstractResultView {

	private final int code;
	private final String message;
	private final Object data;
	private final String dateFormatter;
	private final Map<String, String> codeMap;

	public abstract String convertMessage(String message);

	public AbstractResultView(int code, String message,
							  Object data, String dateFormater,
							  Map<String, String> codeMap){
		this.code = code;
		this.message = convertMessage(message);
		this.data = data;
		this.dateFormatter = dateFormater;
		this.codeMap = codeMap;
	}

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
	public String getDateFormatter() {
		return dateFormatter;
	}
	@Transient
	public Map<String, String> getCodeMap() {
		return codeMap;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractResultView)) return false;
		AbstractResultView that = (AbstractResultView) o;
		return getCode() == that.getCode() &&
				Objects.equals(getMessage(), that.getMessage()) &&
				Objects.equals(getData(), that.getData()) &&
				Objects.equals(getDateFormatter(), that.getDateFormatter()) &&
				Objects.equals(getCodeMap(), that.getCodeMap());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCode(), getMessage(), getData(), getDateFormatter(), getCodeMap());
	}

	@Override
	public String toString() {
		return "ResultView{" +
				"code=" + code +
				", message='" + message + '\'' +
				", data=" + data +
				", dateFormatter='" + dateFormatter + '\'' +
				", codeMap=" + codeMap +
				'}';
	}
}
