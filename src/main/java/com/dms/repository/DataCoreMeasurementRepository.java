/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.dto.ProjectDataTimeLimit;
import com.dms.model.DataCoreMeasurement;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Vamsi
 */
public interface DataCoreMeasurementRepository {

    Map<Long, ProjectDataTimeLimit> getTimeRangeOfAllProjectsData();

    /**
     * 
     * @return  List of product names available in the product data table.
     */
    List<DataCoreMeasurement> getAllDataMeasurements();

    /**
     * This method is used to fetch data measurements for export.
     * @param projectInfoId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Object[]> getDataMeasurementsForExport(Long projectInfoId, String startDate, String endDate);
    
    /**
     * Get all the data for a company. This data is used to display
     * graphs, charts to the user.
     * @param projectInfoId
     * @return list of product data.
     */
    List<DataCoreMeasurement> getDataMeasurements(Long projectInfoId, int maxResults);

   // List<DataCoreMeasurement> getDataMeasurementsForExport(String companyName, String unitSerialNo);

  //  List<DataCoreMeasurement> getUnitListByCompany(String companyName);

    /**
     * File Uploaded for a company is persisted in product tables.
     */
    void saveDataMeasurements(List<DataCoreMeasurement> dataCoreMeasurementList, Long projectInfoId) throws Exception;

   // List<String> getAllCompanies();

    List<String> getVNetAddress(Long projectInfoId);
}
