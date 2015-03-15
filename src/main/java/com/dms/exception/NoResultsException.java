/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.exception;

/**
 * Created by VamsiKrishna on 15/3/15.
 */
public class NoResultsException extends  Exception{
    public NoResultsException() {
        super();
    }

    public NoResultsException(String message) {
        super(message);
    }

    public NoResultsException(String message, Throwable cause) {
        super(message, cause);
    }
}
