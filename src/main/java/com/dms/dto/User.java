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
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer mappedRoleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername(){
        return lastName+" "+ firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMappedRoleId() {
        return mappedRoleId;
    }

    public void setMappedRoleId(Integer mappedRoleId) {
        this.mappedRoleId = mappedRoleId;
    }
}
