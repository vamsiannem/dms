/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

/**
 * @author vamsikrishna
 * @param <T> 
 * 
 */
public interface Cache<T> {

	/**
	 * @param item
	 */
	void pushItem(T item);

	/**
	 * @return T
	 */
	T popItem();

	/**
	 * @return cacheSize
	 */
	Integer getSize();
	
	/**
	 * 
	 * @return true if cache is empty. Otherwise, false.
	 */
	boolean isEmpty();

}
