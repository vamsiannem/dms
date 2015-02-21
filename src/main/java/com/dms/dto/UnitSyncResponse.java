/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.dto;

import java.io.Serializable;

/**
 * Created by support on 20/2/15.
 */
public class UnitSyncResponse implements Serializable{

    private String contentType;
    private String body;
    private String headers;
    private Integer statusCode;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
