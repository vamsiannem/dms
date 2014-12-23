/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.model.ProductData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vamsi
 */
@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository{

    private static final Integer MAX_RESULTS = 2000;

    private static final Integer ALL_MAX_RESULTS = 4000;

    @Resource
    SessionFactory sessionFactory;

    @Override
    public List<ProductData> getAllAvailableProducts() {

       Session session = sessionFactory.getCurrentSession();
        String hqlQuery = "select time,vNetAddress, type, status, limImbalance, limResistance, limCapacitance, companyName, unitSerialNo  FROM ProductData p";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(ALL_MAX_RESULTS);
        return createProductsList(query.list());
    }

    @Override
    public List<ProductData> getDataForProduct(String companyName) {
       Session session = sessionFactory.getCurrentSession();
       Criteria query = session.createCriteria(ProductData.class)
               .add(Restrictions.eq("companyName", companyName))
               .addOrder(Order.desc("id"))
               .setMaxResults(MAX_RESULTS)
               .setProjection(Projections.projectionList()
                       .add(Projections.property("time"))
                       .add(Projections.property("vNetAddress"))
                       .add(Projections.property("type"))
                       .add(Projections.property("status"))
                       .add(Projections.property("limImbalance"))
                       .add(Projections.property("limResistance"))
                       .add(Projections.property("limCapacitance")));
        return createProductsList(query.list());
    }

    @Override
    public List<ProductData> getDataForProduct(String companyName, String unitSerialNo) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(ProductData.class)
                .add(Restrictions.eq("companyName", companyName))
                .add(Restrictions.eq("unitSerialNo", unitSerialNo))
                .addOrder(Order.asc("companyName"))
                //.addOrder(Order.desc("unitSerialNo"))
                .setMaxResults(MAX_RESULTS)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("time"))
                        .add(Projections.property("vNetAddress"))
                        .add(Projections.property("type"))
                        .add(Projections.property("status"))
                        .add(Projections.property("limImbalance"))
                        .add(Projections.property("limResistance"))
                        .add(Projections.property("limCapacitance")));

        return createProductsList(query.list());
    }

    @Override
    public List<ProductData> getUnitListByCompany(String companyName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ProductData.class);
        criteria.setProjection(Projections.distinct(Projections.property("unitSerialNo")));
        criteria.add(Restrictions.eq("companyName", companyName));
        return criteria.list();
    }
      @Override
    public void saveProducts(List<ProductData> productDataList, String companyName, String unitSerialNo) {
        Session session = sessionFactory.getCurrentSession();
        for (ProductData productData : productDataList){
            productData.setCompanyName(companyName);
            productData.setUnitSerialNo(unitSerialNo);
            session.save(productData);
        }
    }

    @Override
    public List<String> getAllCompanies(){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( ProductData.class );
        criteria.setProjection(Projections.distinct(Projections.property("companyName")));
        return criteria.list();
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
            productDataList.add(productData);
        }
        return productDataList;
    }

}
