/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.exception;

/**
 * Created by VamsiKrishna on 31/3/15.
 */
public class InvalidRequest extends Exception {
    public InvalidRequest() {
        super();
    }

    public InvalidRequest(String message) {
        super(message);
    }

    public InvalidRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequest(Throwable cause) {
        super(cause);
    }
}
