/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ClientInfo;

import java.util.List;

/**
 * Created by VamsiKrishna on 29/3/15.
 */
public interface ClientRepository {

    ClientInfo getClient(Integer id);
    ClientInfo getClient(String name);
    List<ClientInfo> getAll();

}
