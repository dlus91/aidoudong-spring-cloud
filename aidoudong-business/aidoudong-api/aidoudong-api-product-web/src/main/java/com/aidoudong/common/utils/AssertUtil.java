package com.aidoudong.common.utils;

import com.aidoudong.common.exception.BussinessException;
import org.springframework.lang.Nullable;

public final class AssertUtil {

	public static void isNull(@Nullable Object object, String message) {
		if (object != null) {
			throw new BussinessException(message);
		}
	}
	
	public static void notNull(@Nullable Object object, String message) {
		if (object == null) {
			throw new BussinessException(message);
		}
	}
	
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new BussinessException(message);
		}
	}
	
	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new BussinessException(message);
		}
	}
	
	
}
