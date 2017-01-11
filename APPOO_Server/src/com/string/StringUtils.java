package com.string;

public class StringUtils {
	public static boolean isEmpty(String string) {
		if (string != null) {
			return string.isEmpty();
		}
		return true;
	}

	public static String trimToEmpty(String string) {
		if (string != null) {
			return string.trim();
		}
		return "";
	}
}
