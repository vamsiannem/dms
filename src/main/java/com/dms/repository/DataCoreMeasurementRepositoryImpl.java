/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.utils.DMSConstants;
import com.dms.utils.UploadCSVHelper;
import com.dms.dto.ProjectDataTimeLimit;
import com.dms.model.ClientInfo;
import com.dms.model.DataCoreMeasurement;
import com.dms.model.ProductInfo;
import com.dms.model.ProjectInfo;

import java.text.ParseException;
import java.util.*;
import javax.annotation.Resource;

import com.dms.sysenum.UploadCSVType;
import com.dms.utils.EnumHelper;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.dms.utils.DateUtils.*;
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

    @Resource
    private UploadCSVHelper uploadCSVHelper;

    @Override
    public Map<Long, ProjectDataTimeLimit> getTimeRangeOfAllProjectsData() {
        Session session = sessionFactory.getCurrentSession();
        Collection<Long> projectInfoIds = projectRepository.getAllIds();
        Map<Long, ProjectDataTimeLimit> dataTimeLimitMap = new HashMap<Long, ProjectDataTimeLimit>(projectInfoIds!=null ? projectInfoIds.size(): 10);
        for (Iterator<Long> iterator = projectInfoIds.iterator(); iterator.hasNext(); ) {
            Long projectInfoId = iterator.next();
            // TODO: replace this with HQL criteria query.
            Query query = session.createSQLQuery("SELECT min(TIMESTAMP(p.time)), max(TIMESTAMP(p.time)) FROM data_core_measurements p where p.project_info_id = :projectInfoId").setParameter("projectInfoId", projectInfoId);
            Object[] result = (Object[]) query.uniqueResult();
            ProjectDataTimeLimit res = null;
            try {
                String toFormat = "dd/MM/yyyy HH:mm";
                String fromFormat = "yyyy-MM-dd HH:mm:ss";
                res = new ProjectDataTimeLimit(
                        getDateStrWithFormat(result[0].toString(),fromFormat, toFormat),
                        getDateStrWithFormat(result[1].toString(),fromFormat, toFormat)
                );
                dataTimeLimitMap.put(projectInfoId, res);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dataTimeLimitMap;
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
    public List<Object[]> getDataMeasurementsForExport(Long projectInfoId, String startDate, String endDate) {
        Session session = sessionFactory.getCurrentSession();
        Object[] values = {startDate,endDate};
        Criteria query = session.createCriteria(DataCoreMeasurement.class)
                .createAlias("projectInfo", "nu", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .add(Restrictions.sqlRestriction("timestamp({alias}.time) between ? and ?",
                        values, new org.hibernate.type.Type[]{StandardBasicTypes.STRING, StandardBasicTypes.STRING}))
                .addOrder(Order.asc("id"))
                .setMaxResults(MAX_RESULTS_EXPORT);
        ProjectInfo projectInfo = projectRepository.get(projectInfoId);
        UploadCSVType type = EnumHelper.load(UploadCSVType.class, projectInfo.getProductInfo().getType());
        if(type.compareTo(UploadCSVType.VLIFE_MK1)==0){
            query.createAlias("dataVlifeMkOne", DMSConstants.EXPORT_VLIFE_MK1_ORM_SUFFIX.replace(".", ""));
        }
        query.setProjection(get(type));
        return createDataMeasurementsExport(query.list());
    }

    private ProjectionList get(UploadCSVType type){

        ProjectionList list = Projections.projectionList();
        String[] mappingCols = uploadCSVHelper.getMetaData(type).getOrmMappings();
        for (String col: mappingCols){
            if (col != null)
                list.add(Projections.property(col));
        }
        return list;
    }

    @Override
    public List<DataCoreMeasurement> getDataMeasurements(Long projectInfoId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria query = session.createCriteria(DataCoreMeasurement.class)
                .createAlias("projectInfo", "nu", CriteriaSpecification.INNER_JOIN)
                .createAlias("nu.clientInfo", "client", CriteriaSpecification.INNER_JOIN)
                .createAlias("nu.productInfo", "product", CriteriaSpecification.INNER_JOIN)
                .add(Restrictions.eq("nu.projectInfoId", projectInfoId))
                .addOrder(Order.desc("id"))
                .setMaxResults(MAX_RESULTS)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("time"))
                        .add(Projections.property("vNetAddress"))
                        .add(Projections.property("dataType"))
                        .add(Projections.property("status"))
                        .add(Projections.property("l1L2Ratio"))
                        .add(Projections.property("insulationResistance"))
                        .add(Projections.property("insulationCapacitance"))
                        .add(Projections.property("client.id"))
                        .add(Projections.property("client.name"))
                        .add(Projections.property("product.unitSerialNo"))
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
