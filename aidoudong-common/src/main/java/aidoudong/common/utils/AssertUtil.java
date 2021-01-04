package aidoudong.common.utils;


import aidoudong.common.exception.BussinessException;

public final class AssertUtil {

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new BussinessException(message);
		}
	}
	
	public static void notNull(Object object, String message) {
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
