/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ProjectHistoryInfo;
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
import java.util.SortedSet;

/**
 * Created by VamsiKrishna on 1/2/15.
 */
@Repository
@Transactional
public class ProjectRepositoryImpl implements ProjectRepository {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public ProjectInfo get(Long projectInfoId) {
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
    @Transactional
    public ProjectInfo create(ProjectInfo projectInfo) throws HibernateException{
        Session session = sessionFactory.getCurrentSession();
        SortedSet<ProjectHistoryInfo> historyInfoList = projectInfo.getProjectHistoryInfoList();
        session.saveOrUpdate(projectInfo);
        session.flush();
        session.clear();
        for(ProjectHistoryInfo historyInfo : historyInfoList){
            historyInfo.setProjectInfo(projectInfo);
            session.save(historyInfo);
        }
        return projectInfo;
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
    public Collection<ProjectInfo> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(ProjectInfo.class).list();
    }

    @Override
    public Collection<ProjectInfo> getAll(String orderBy) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(ProjectInfo.class).addOrder(Order.desc(orderBy)).list();
    }

    // TODO: This needs to be moved to ProductRepo.
    @Override
    public UnitConnectionConfig getUnitConfig(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        ProjectInfo unit = (ProjectInfo) session.get(ProjectInfo.class, projectInfoId);
        return unit.getUnitConnectionConfig();
    }
}
