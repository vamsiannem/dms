/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ProductInfo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by VamsiKrishna on 12/4/15.
 */
@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @Resource
    CommonRepository commonRepository;

    @Override
    public ProductInfo get(String unitSerialNo) {
        return (ProductInfo) commonRepository.createCriteria(ProductInfo.class)
                .add(Restrictions.eq("unitSerialNo", unitSerialNo))
                .uniqueResult();
    }

    @Override
    public ProductInfo get(Long id) {
        return commonRepository.get(ProductInfo.class, id);
    }
}
