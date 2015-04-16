/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.ProductInfo;

/**
 * Created by VamsiKrishna on 12/4/15.
 */

public interface ProductRepository {

    ProductInfo get(String unitSerialNo);
    ProductInfo get(Long id);
}
