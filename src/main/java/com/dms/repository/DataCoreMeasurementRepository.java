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
import com.dms.model.DataCoreMeasurement;

import java.util.List;

/**
 *
 * @author Vamsi
 */
public interface DataCoreMeasurementRepository {

    UnitDataDateLimit getTimeRangeOfProjectData();

    /**
     * 
     * @return  List of product names available in the product data table.
     */
    List<DataCoreMeasurement> getAllDataMeasurements();

    List<Object[]> getDataMeasurements(Long projectInfoId, String startDate, String endDate);
    
    /**
     * Get all the data for a company. This data is used to display
     * graphs, charts to the user.
     * @param projectInfoId
     * @return list of product data.
     */
    List<DataCoreMeasurement> getDataMeasurements(Long projectInfoId);

   // List<DataCoreMeasurement> getDataMeasurements(String companyName, String unitSerialNo);

  //  List<DataCoreMeasurement> getUnitListByCompany(String companyName);

    /**
     * File Uploaded for a company is persisted in product tables.
     */
    void saveDataMeasurements(List<DataCoreMeasurement> dataCoreMeasurementList, Long projectInfoId) throws Exception;

   // List<String> getAllCompanies();

    List<String> getVNetAddress(Long projectInfoId);
}
