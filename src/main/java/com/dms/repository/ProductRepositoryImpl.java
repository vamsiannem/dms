/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.model.ProductData;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vamsi
 */
@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository{
    
    @Resource
    SessionFactory sessionFactory;

    @Override
    public List<ProductData> getAllAvailableProducts() {
       Session session = sessionFactory.getCurrentSession();
        String hqlQuery = "select companyName, unitSerialNo, time, vNetAddress, status  from ProductData ";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(2000);
       return query.list();
    }

    @Override
    public List<ProductData> getDataForProduct(String companyName) {
       Session session = sessionFactory.getCurrentSession();
       Criteria query = session.createCriteria(ProductData.class)
               .add(Restrictions.eq("companyName", companyName))
               .addOrder(Order.desc("id"))
               .setMaxResults(10000);
       return query.list();
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

}
