/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.exception;

/**
 * Created by support on 20/2/15.
 */
public class UnitSyncInProgressException extends Throwable {

    public UnitSyncInProgressException() {
        super();
    }

    public UnitSyncInProgressException(String message) {
        super(message);
    }

    public UnitSyncInProgressException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
