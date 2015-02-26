/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.model.NetworkUnit;
import com.dms.model.ProductData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vamsi
 */
@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository{

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private static final Integer MAX_RESULTS = 2000;

    private static final Integer ALL_MAX_RESULTS = 4000;

    @Resource
    SessionFactory sessionFactory;

    @Resource
    NetworkUnitRepository unitRepository;

    @Override
    public List<ProductData> getAllAvailableProducts() {

        Session session = sessionFactory.getCurrentSession();
        String hqlQuery = "FROM ProductData p";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(ALL_MAX_RESULTS);
        return createProductsList(query.list());
    }

    @Override
    public List<ProductData> getDataForProduct(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(ProductData.class)
                .createAlias("networkUnit", "nu", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .addOrder(Order.desc("id"))
                .setMaxResults(MAX_RESULTS)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("time"))
                        .add(Projections.property("vNetAddress"))
                        .add(Projections.property("type"))
                        .add(Projections.property("status"))
                        .add(Projections.property("limImbalance"))
                        .add(Projections.property("limResistance"))
                        .add(Projections.property("limCapacitance"))
                        .add(Projections.property("nu.companyName"))
                        .add(Projections.property("nu.unitSerialNo"))
                        .add(Projections.property("nu.projectInfoId"))
                        .add(Projections.property("nu.projectId")));
        return createProductsList(query.list());
    }


    @Override
    public void saveProducts(List<ProductData> productDataList, Long projectInfoId) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        NetworkUnit unit = unitRepository.getUnitIfo(projectInfoId);
        if(unit == null){
            throw new Exception("No network unit configured for the selected Project !!!");
        }
        int skippedRecordCount = 0;
        int failedRecordCount = 0;
        for (int i=0; i< productDataList.size(); i++){
            ProductData productData = productDataList.get(i);
            productData.setNetworkUnit(unit);
            Criteria query = session.createCriteria(ProductData.class)
                    .createAlias("networkUnit", "nu", CriteriaSpecification.INNER_JOIN)
                    .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                    .add(Restrictions.eq("time", productData.getTime()))
                    .add(Restrictions.eq("vNetAddress", productData.getvNetAddress()))
                    .setProjection(Projections.rowCount());
            int rows =  query.uniqueResult().hashCode();
            if(rows !=0){
                skippedRecordCount++;
                continue;
            }
            try {
                session.save(productData);
            } catch (HibernateException he){
                System.err.println("Error while inserting record ->"+ productData.toString()+ ", Error Msg:"+ he.getCause().getMessage());
                failedRecordCount++;
            }
            if ((i+1)%30 == 0){
                session.flush();
                session.clear();
            }
        }
        logger.info("CSV File Upload stats: \n Project Id: %d \n Total Records Processed: %d \n Skipped Records: %d \n Failed Records: %d", new Object[] {unit.getProjectId(), productDataList.size(), skippedRecordCount, failedRecordCount});
    }

    @Override
    public List<String> getVNetAddress(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(ProductData.class)
                .createAlias("networkUnit", "nu", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .addOrder(Order.desc("vNetAddress"))
                .setMaxResults(MAX_RESULTS)
                .setProjection(Projections.distinct(Projections.projectionList()
                        .add(Projections.property("vNetAddress"))));
        //.setResultTransformer(Transformers.aliasToBean(String.class));
        return query.list();
    }

    private List<ProductData> createProductsList(List<Object[]> results){

        if(results == null || results.size() == 0) {
            return new ArrayList<ProductData>();
        }
        List<ProductData> productDataList = new ArrayList<ProductData>(results.size());
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            ProductData productData = new ProductData();
            Object[] row = (Object[]) iterator.next();
            productData.setTime((String) row[0]);
            productData.setvNetAddress((String) row[1]);
            productData.setType((Integer) row[2]);
            productData.setStatus((String) row[3]);
            productData.setLimImbalance((Double) row[4]);
            productData.setLimResistance((Double) row[5]);
            productData.setLimCapacitance((String) row[6]);
            NetworkUnit nu = new NetworkUnit();
            nu.setCompanyName((String) row[7]);
            nu.setUnitSerialNo((String) row[8]);
            nu.setProjectInfoId((Long) row[9]);
            nu.setProjectId((String)row[10]);
            productData.setNetworkUnit(nu);
            productDataList.add(productData);
        }
        return productDataList;
    }

}
