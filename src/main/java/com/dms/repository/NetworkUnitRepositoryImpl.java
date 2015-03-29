/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ProjectInfo;
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
    public ProjectInfo getUnitIfo(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        return (ProjectInfo) session.get(ProjectInfo.class, projectInfoId);
    }

    @Override
    public ProjectInfo update(ProjectInfo unit) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(unit);
        return unit;
    }

    @Override
    public ProjectInfo create(ProjectInfo unit) throws HibernateException{
        Session session = sessionFactory.getCurrentSession();
        //session.save(unit.getUnitConnectionConfig());
        session.saveOrUpdate(unit);
        return unit;
    }

    @Override
    public void delete(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Object unit = session.get(ProjectInfo.class, projectInfoId);
        if(unit!=null){
            session.delete(unit);
        } else {
            System.err.println(" No Network Unit found with the given ProjectInfoId: "+ projectInfoId);
        }
    }

    @Override
    public UnitConnectionConfig getUnitConfig(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        ProjectInfo unit = (ProjectInfo) session.get(ProjectInfo.class, projectInfoId);
        return unit.getUnitConnectionConfig();
    }

    @Override
    public Collection<ProjectInfo> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(ProjectInfo.class).list();
    }

    @Override
    public Collection<ProjectInfo> getAllUnitsInOrder(String orderBy) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(ProjectInfo.class).addOrder(Order.desc(orderBy)).list();
    }
}
