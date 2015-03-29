/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ProjectInfo;
import com.dms.model.UnitConnectionConfig;
import org.hibernate.HibernateException;

import java.util.Collection;

/**
 * Created by support on 1/2/15.
 */
public interface NetworkUnitRepository {
    ProjectInfo getUnitIfo(Long projectInfoId);
    ProjectInfo update(ProjectInfo unit);
    ProjectInfo create(ProjectInfo unit) throws HibernateException;
    void delete(Long projectInfoId);
    UnitConnectionConfig getUnitConfig(Long projectInfoId);
    Collection<ProjectInfo> getAll();
    Collection<ProjectInfo> getAllUnitsInOrder(String orderBy);
}
