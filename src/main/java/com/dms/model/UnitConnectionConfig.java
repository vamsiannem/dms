/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by support on 31/1/15.
 */
@Entity
@Table(name = "unit_config")
public class UnitConnectionConfig implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Example
     * URL: http://localhost:8080/unit/data
     */
    @Column(name="remote_url", nullable=false)
    private String url;
    /**
     * Examples:
     * ContentType:application/json,Accept:application/json
     */
    //@Enumerated(EnumType.STRING)
    @Column(name="headers", nullable=false)
    private String headers;
    /**
     *  Possible values:
     *  GET, POST, PUT
     */
    @Enumerated(EnumType.STRING)
    @Column(name="method")
    private RequestMethod method;

    @OneToOne(mappedBy = "unitConnectionConfig")
    private NetworkUnit networkUnit;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }
}
