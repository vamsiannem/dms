/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.NetworkUnit;
import com.dms.model.UnitConnectionConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
    public NetworkUnit getUnitIfo(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        return (NetworkUnit) session.get(NetworkUnit.class, projectInfoId);
    }

    @Override
    public NetworkUnit update(NetworkUnit unit) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(unit);
        return unit;
    }

    @Override
    public NetworkUnit create(NetworkUnit unit) throws HibernateException{
        Session session = sessionFactory.getCurrentSession();
        //session.save(unit.getUnitConnectionConfig());
        session.saveOrUpdate(unit);
        return unit;
    }

    @Override
    public void delete(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Object unit = session.get(NetworkUnit.class, projectInfoId);
        if(unit!=null){
            session.delete(unit);
        } else {
            System.err.println(" No Network Unit found with the given ProjectInfoId: "+ projectInfoId);
        }
    }

    @Override
    public UnitConnectionConfig getUnitConfig(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        NetworkUnit unit = (NetworkUnit) session.get(NetworkUnit.class, projectInfoId);
        return unit.getUnitConnectionConfig();
    }

    @Override
    public Collection<NetworkUnit> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(NetworkUnit.class).list();
    }

    @Override
    public Collection<NetworkUnit> getAllUnitsInOrder(String orderBy) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(NetworkUnit.class).addOrder(Order.desc(orderBy)).list();
    }
}
