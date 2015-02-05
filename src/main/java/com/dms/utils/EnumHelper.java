/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

public class EnumHelper {

	public static <T> T load(Class<T> enumClass, String code) {
		T match = null;
		for (T enumType : enumClass.getEnumConstants()) {
			if (enumType.toString().equalsIgnoreCase(code)) {
				match = enumType;
				break;
			}
		}
		return match;
	}
}
