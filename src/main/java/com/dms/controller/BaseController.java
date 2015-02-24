/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.exception.UnitSyncInProgressException;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Created by vamsikrishna on 18/10/14.
 */
public abstract class BaseController {
    /**
     * Logging all exceptions
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = {IOException.class, HibernateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    String handleException(Throwable throwable,  HttpServletRequest request) {
        return throwable.getMessage();
    }

    @ExceptionHandler(value = {UnitSyncInProgressException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    String handleSyncProgressException(Throwable throwable,  HttpServletRequest request) {
        return throwable.getMessage();
    }
    @ExceptionHandler(value = {Exception.class, NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    String handleOtherExceptions(Throwable throwable,  HttpServletRequest request) {
        return throwable.getMessage();
    }
}
