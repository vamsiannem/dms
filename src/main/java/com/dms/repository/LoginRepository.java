/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.User;

/**
 * Created by vamsikrishna on 19/10/14.
 */
public interface LoginRepository {


    public User doLogin(String username, String password);
}
