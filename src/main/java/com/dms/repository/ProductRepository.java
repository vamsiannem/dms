/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.dto.UnitDataDateLimit;
import com.dms.model.ProductData;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Vamsi
 */
public interface ProductRepository {

    UnitDataDateLimit getUnitDataLimits();

    /**
     * 
     * @return  List of product names available in the product data table.
     */
    List<ProductData> getAllAvailableProducts();

    List<Object[]> getUnitData(Long projectInfoId, String startDate, String endDate);
    
    /**
     * Get all the data for a company. This data is used to display
     * graphs, charts to the user.
     * @param projectInfoId
     * @return list of product data.
     */
    List<ProductData> getDataForProduct(Long projectInfoId);

   // List<ProductData> getDataForProduct(String companyName, String unitSerialNo);

  //  List<ProductData> getUnitListByCompany(String companyName);

    /**
     * File Uploaded for a company is persisted in product tables.
     */
    void saveProducts(List<ProductData> productDataList, Long projectInfoId) throws Exception;

   // List<String> getAllCompanies();

    List<String> getVNetAddress(Long projectInfoId);
}
