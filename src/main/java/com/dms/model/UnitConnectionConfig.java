/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VamsiKrishna on 31/1/15.
 */
@Entity
@Table(name = "unit_config")
public class UnitConnectionConfig implements Serializable {

    private static final long serialVersionUID = -6170180512537571236L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @Column(name = "body")
    private String bodyParams;

    @OneToOne(mappedBy = "unitConnectionConfig")
    private ProjectInfo projectInfo;


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

    public String[] getHeaders() {
        return headers!=null ? headers.split(";") : null;
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

    public String getBodyParams() {
        return bodyParams;
    }

    public void setBodyParams(String bodyParams) {
        this.bodyParams = bodyParams;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    public Map<String, ?> getHeadersMap(){
       String[] _headers = headers!=null ? headers.split(";") : null;
        Map<String, String> headersMap = new HashMap<String, String>(_headers.length);
        for(String _header: _headers){
            String[] keyValue = _header.split(":");
            headersMap.put(keyValue[0], keyValue[1]);
        }
        return headersMap;
    }


    @JsonIgnore
    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }
}
