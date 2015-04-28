/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.exception.InvalidCredentialsException;
import com.dms.exception.NoResultsException;
import com.dms.exception.UnitSyncInProgressException;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Created by vamsikrishna on 18/10/14.
 */
public abstract class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
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

    @ExceptionHandler(value = {NoResultsException.class})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ModelAndView handleNoResultsExceptions(Throwable throwable,  HttpServletRequest req) {
        logger.error("Request: " + req.getRequestURL() + " raised " + throwable);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", throwable);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("flag", "1");
        mav.addObject("status", "No Results Found.");
        return mav;
    }


}
