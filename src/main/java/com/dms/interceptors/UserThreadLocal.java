
package com.dms.interceptors;

/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */



public class UserThreadLocal {

	private static final ThreadLocal<Integer> currentUserId = new ThreadLocal<Integer>();

	public static Integer getCurrentUserId() {
		return currentUserId.get();
	}

	public static void setCurrentUserId(Integer userId) {
		currentUserId.set(userId);
	}
	
	public static void remove() {
		currentUserId.remove();
	}
}
