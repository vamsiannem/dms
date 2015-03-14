/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.NetworkUnit;
import com.dms.model.UnitConnectionConfig;
import org.hibernate.HibernateException;

import java.util.Collection;

/**
 * Created by support on 1/2/15.
 */
public interface NetworkUnitRepository {
    NetworkUnit getUnitIfo(Long projectInfoId);
    NetworkUnit update(NetworkUnit unit);
    NetworkUnit create(NetworkUnit unit) throws HibernateException;
    void delete(Long projectInfoId);
    UnitConnectionConfig getUnitConfig(Long projectInfoId);
    Collection<NetworkUnit> getAll();
    Collection<NetworkUnit> getAllUnitsInOrder(String orderBy);
}
