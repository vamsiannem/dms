/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.NetworkUnit;
import com.dms.model.UnitSyncStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by support on 2/2/15.
 */
@Repository
@Transactional
public class UnitSyncRepositoryImpl implements UnitSyncRepository {

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private NetworkUnitRepository unitRepository;

    @Override
    public UnitSyncStatus getLastRun(String unitProjectId) {
        Session session = sessionFactory.getCurrentSession();

        Collection<UnitSyncStatus> result = getSyncStatus(unitProjectId, 1);
        if (result !=null && result.size() == 1) {
            Iterator<UnitSyncStatus> iterator =result.iterator();
            return iterator.next();
        }
        return null;
    }

    @Override
    public Collection<UnitSyncStatus> getLastRun() {
        Collection<NetworkUnit> units = unitRepository.getAll();
        Collection<UnitSyncStatus> syncStatusCollection = null;
        if (units != null && units.size() > 0){
            Session session = sessionFactory.getCurrentSession();
            syncStatusCollection = new ArrayList<UnitSyncStatus>(units.size());
            for(NetworkUnit unit: units){
                Collection<UnitSyncStatus> result = getSyncStatus(unit.getProjectId(), 1);
                if (result !=null && result.size() == 1) {
                    Iterator<UnitSyncStatus> iterator =result.iterator();
                    syncStatusCollection.add(iterator.next());
                }
            }
        }
        return syncStatusCollection;
    }

    @Override
    public void startSync(UnitSyncStatus syncStatus) {
        Session session = sessionFactory.getCurrentSession();
        session.save(syncStatus);
    }

    @Override
    public void updateSyncStatus(UnitSyncStatus syncStatus) {
        Session session = sessionFactory.getCurrentSession();
        session.update(syncStatus);
    }

    @Override
    public Map<String, Collection<UnitSyncStatus>> getLastWeekSyncStatus() {
        Collection<NetworkUnit> units = unitRepository.getAll();
        Map<String, Collection<UnitSyncStatus>> map = null;
        if (units != null && units.size()>0 ) {
            map = new HashMap<String, Collection<UnitSyncStatus>>(units.size());
            Session session = sessionFactory.getCurrentSession();
            for(NetworkUnit unit: units){
                Collection<UnitSyncStatus> result = getSyncStatus(unit.getProjectId(), 7);
                if (result !=null && result.size()>0) {
                    map.put(unit.getProjectId(), result);
                }
            }
        }
        return map;
    }

    private Collection<UnitSyncStatus> getSyncStatus(String projectId, int howManyRuns){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UnitSyncStatus.class)
                .add(Restrictions.eq("networkUnit.projectId", projectId))
                .addOrder(Order.desc("endTime"))
                .setFirstResult(0)
                .setMaxResults(howManyRuns);
        return criteria.list();
    }
}
