/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ClientInfo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by VamsiKrishna on 29/3/15.
 */
@Repository
@Transactional
public class ClientRepositoryImpl implements ClientRepository {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public ClientInfo getClient(Integer id) {
        return null;
    }

    @Override
    public ClientInfo getClient(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(ClientInfo.class)
                .add(Restrictions.eq("name", name));
        return (ClientInfo) query.uniqueResult();
    }
}
