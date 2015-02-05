/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.NetworkUnit;
import com.dms.model.UnitConnectionConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by support on 1/2/15.
 */
@Repository
@Transactional
public class NetworkUnitRepositoryImpl implements NetworkUnitRepository {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public NetworkUnit getUnitIfo(String projectId) {
        Session session = sessionFactory.getCurrentSession();
        return (NetworkUnit) session.get(NetworkUnit.class, projectId);
    }

    @Override
    public NetworkUnit update(NetworkUnit unit) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(unit);
        return unit;
    }

    @Override
    public NetworkUnit create(NetworkUnit unit) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(unit);
        return unit;
    }

    @Override
    public void delete(String projectId) {
        Session session = sessionFactory.getCurrentSession();
        Object unit = session.get(NetworkUnit.class, projectId);
        if(unit!=null){
            session.delete(unit);
        } else {
            System.err.println(" No Network Unit found with the given ProjectId: "+ projectId);
        }
    }

    @Override
    public UnitConnectionConfig getUnitConfig(String projectId) {
        Session session = sessionFactory.getCurrentSession();
        NetworkUnit unit = (NetworkUnit) session.get(NetworkUnit.class, projectId);
        return unit.getUnitConnectionConfig();
    }

    @Override
    public Collection<NetworkUnit> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(NetworkUnit.class).list();
    }
}
