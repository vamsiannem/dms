/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by vamsikrishna on 19/10/14.
 */
public class User implements Serializable{

    private Integer userId;
    private String username;
    private String email;
    private String password;
    private Integer mappedRoleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMappedRoleId() {
        return mappedRoleId;
    }

    public void setMappedRoleId(Integer mappedRoleId) {
        this.mappedRoleId = mappedRoleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
