package aidoudong.common.resultview;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: dlus91
 * @Date: 2020/8/21 11:30
 */
public abstract class AbstractResultView<T> {

	private final int code;
	private final String message;
	private final T data;
	private final transient String dateFormatter;
	private final transient Map<String, String> codeMap;

	public abstract String convertMessage(String message);

	public AbstractResultView(int code, String message,
							  T data, String dateFormatter,
							  Map<String, String> codeMap){
		this.code = code;
		this.message = convertMessage(message);
		this.data = data;
		this.dateFormatter = dateFormatter;
		this.codeMap = codeMap;
	}
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public T getData() {
		return data;
	}

	public String getDateFormatter() {
		return dateFormatter;
	}
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
