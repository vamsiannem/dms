/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.NetworkUnit;
import com.dms.model.UnitConnectionConfig;

import java.util.Collection;

/**
 * Created by support on 1/2/15.
 */
public interface NetworkUnitRepository {
    NetworkUnit getUnitIfo(String projectId);
    NetworkUnit update(NetworkUnit unit);
    NetworkUnit create(NetworkUnit unit);
    void delete(String projectId);
    UnitConnectionConfig getUnitConfig(String projectId);
    Collection<NetworkUnit> getAll();
}
