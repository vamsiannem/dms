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
import com.dms.model.ClientInfo;
import com.dms.model.DataCoreMeasurement;
import com.dms.model.ProductInfo;
import com.dms.model.ProjectInfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.dms.utils.DateUtils.convertMysqlDateToDisplayDate;
import static com.dms.utils.DMSConstants.MAX_RESULTS_EXPORT;
import static com.dms.utils.DMSConstants.ALL_MAX_RESULTS;

/**
 *
 * @author Vamsi
 */
@Repository
@Transactional
public class DataCoreMeasurementRepositoryImpl implements DataCoreMeasurementRepository {

    private final Logger logger = LoggerFactory.getLogger(DataCoreMeasurementRepositoryImpl.class);

    private static final Integer MAX_RESULTS = 2000;

    @Resource
    SessionFactory sessionFactory;

    @Resource
    ProjectRepository projectRepository;

    @Override
    public UnitDataDateLimit getTimeRangeOfProjectData() {
        Session session = sessionFactory.getCurrentSession();
        // TODO: replace this with HQL criteria query.
        SQLQuery query = session.createSQLQuery("SELECT min(date(p.time)), max(date(p.time)) FROM data_core_measurements p");

        Object[] result = (Object[]) query.uniqueResult();
        UnitDataDateLimit res = null;
        try {
            res = new UnitDataDateLimit(
                    convertMysqlDateToDisplayDate(result[0].toString()),
                    convertMysqlDateToDisplayDate(result[1].toString())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<DataCoreMeasurement> getAllDataMeasurements() {
        Session session = sessionFactory.getCurrentSession();
        String hqlQuery = "FROM DataCoreMeasurement p";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(ALL_MAX_RESULTS);
        return createDataMeasurements(query.list());
    }

    @Override
    public List<Object[]> getDataMeasurements(Long projectInfoId, String startDate, String endDate) {
        Session session = sessionFactory.getCurrentSession();
        Object[] values = {startDate,endDate};
        Criteria query = session.createCriteria(DataCoreMeasurement.class)
                .createAlias("projectInfo", "nu", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .add(Restrictions.sqlRestriction("date({alias}.time) between ? and ?",
                        values, new org.hibernate.type.Type[]{StandardBasicTypes.STRING, StandardBasicTypes.STRING}))
                .addOrder(Order.asc("id"))
                .setMaxResults(MAX_RESULTS_EXPORT)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("time"))
                        .add(Projections.property("vNetAddress"))
                        .add(Projections.property("dataType"))
                        .add(Projections.property("status"))
                        .add(Projections.property("l1L2Ratio"))
                        .add(Projections.property("insulationResistance"))
                        .add(Projections.property("insulationCapacitance"))
                        .add(Projections.property("downstreamInsulationResistance"))
                        .add(Projections.property("downstreamInsulationCapacitance"))
                        .add(Projections.property("lineVoltage"))
                        .add(Projections.property("lineCurrent"))
                        .add(Projections.property("lineFrequency"))
                        .add(Projections.property("linePhase")));
        return createDataMeasurementsExport(query.list());
    }

    @Override
    public List<DataCoreMeasurement> getDataMeasurements(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(DataCoreMeasurement.class)
                .createAlias("projectInfo", "nu", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .addOrder(Order.desc("id"))
                .setMaxResults(MAX_RESULTS)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("time"))
                        .add(Projections.property("vNetAddress"))
                        .add(Projections.property("type"))
                        .add(Projections.property("status"))
                        .add(Projections.property("l1L2Ratio"))
                        .add(Projections.property("insulationResistance"))
                        .add(Projections.property("insulationCapacitance"))
                        .add(Projections.property("nu.clientInfo.id"))
                        .add(Projections.property("nu.clientInfo.name"))
                        .add(Projections.property("nu.productInfo.unitSerialNo"))
                        .add(Projections.property("nu.projectInfoId"))
                        .add(Projections.property("nu.projectId")));
        return createDataMeasurements(query.list());
    }

    @Override
    public void saveDataMeasurements(List<DataCoreMeasurement> dataCoreMeasurementList, Long projectInfoId) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        ProjectInfo unit = projectRepository.get(projectInfoId);
        if(unit == null){
            throw new Exception("No Project created for the selected Project !!!");
        }
        int skippedRecordCount = 0;
        int failedRecordCount = 0;
        for (int i=0; i< dataCoreMeasurementList.size(); i++){
            DataCoreMeasurement dataCoreMeasurement = dataCoreMeasurementList.get(i);
            dataCoreMeasurement.setProjectInfo(unit);
            Criteria query = session.createCriteria(DataCoreMeasurement.class)
                    .createAlias("projectInfo", "nu", CriteriaSpecification.INNER_JOIN)
                    .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                    .add(Restrictions.eq("time", dataCoreMeasurement.getTime()))
                    .add(Restrictions.eq("vNetAddress", dataCoreMeasurement.getvNetAddress()))
                    .setProjection(Projections.rowCount());
            int rows =  query.uniqueResult().hashCode();
            if(rows !=0){
                skippedRecordCount++;
                continue;
            }
            try {
                session.save(dataCoreMeasurement);
            } catch (HibernateException he){
                System.err.println("Error while inserting record ->"+ dataCoreMeasurement.toString()+ ", Error Msg:"+ he.getCause().getMessage());
                failedRecordCount++;
            }
            if ((i+1)%30 == 0){
                session.flush();
                session.clear();
            }
        }
        logger.info("CSV File Upload stats: \n Project Id: {} \n Total Records Processed: {} \n Skipped Records: {} \n Failed Records: {}",
                unit.getProjectId(), dataCoreMeasurementList.size(), skippedRecordCount, failedRecordCount);
    }


    @Override
    public List<String> getVNetAddress(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(DataCoreMeasurement.class)
                .createAlias("projectInfo", "nu", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .addOrder(Order.desc("vNetAddress"))
                .setMaxResults(MAX_RESULTS)
                .setProjection(Projections.distinct(Projections.projectionList()
                        .add(Projections.property("vNetAddress"))));
        //.setResultTransformer(Transformers.aliasToBean(String.class));
        return query.list();
    }

    private List<DataCoreMeasurement> createDataMeasurements(List<Object[]> results){

        if(results == null || results.size() == 0) {
            return new ArrayList<DataCoreMeasurement>();
        }
        List<DataCoreMeasurement> dataCoreMeasurementList = new ArrayList<DataCoreMeasurement>(results.size());
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            DataCoreMeasurement dataCoreMeasurement = new DataCoreMeasurement();
            Object[] row = (Object[]) iterator.next();
            dataCoreMeasurement.setTime((String) row[0]);
            dataCoreMeasurement.setvNetAddress((String) row[1]);
            dataCoreMeasurement.setDataType((Integer) row[2]);
            dataCoreMeasurement.setStatus((String) row[3]);
            dataCoreMeasurement.setL1L2Ratio((Double) row[4]);
            dataCoreMeasurement.setInsulationResistance((Double) row[5]);
            dataCoreMeasurement.setInsulationCapacitance((String) row[6]);
            ProjectInfo nu = new ProjectInfo();
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setId((Integer) row[7]);
            clientInfo.setName((String) row[8]);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setUnitSerialNo((String) row[9]);
            //nu.setUnitSerialNo((String) row[9]);
            nu.setProjectInfoId((Long) row[10]);
            nu.setProjectId((String) row[11]);
            nu.setClientInfo(clientInfo);
            nu.setProductInfo(productInfo);
            dataCoreMeasurement.setProjectInfo(nu);
            dataCoreMeasurementList.add(dataCoreMeasurement);
        }
        return dataCoreMeasurementList;
    }

    private List<Object[]> createDataMeasurementsExport(List results) {
        if(results == null || results.size() == 0) {
            return new ArrayList<Object[]>();
        }
        List<Object[]> dataCoreMeasurements = new ArrayList<Object[]>(results.size());
        Iterator iterator = results.iterator();
        while (iterator.hasNext()){
            Object[] row = (Object[]) iterator.next();
            dataCoreMeasurements.add(row);
        }
        return dataCoreMeasurements;
    }

}
