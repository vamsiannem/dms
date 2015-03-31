/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

import com.dms.dto.UnitSyncResponse;
import com.dms.exception.UnitSyncInProgressException;
import com.dms.model.ProjectInfo;
import com.dms.model.UnitConnectionConfig;
import com.dms.utils.DMSConstants;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Vamsi 19/2/15.
 */
@Service
public class UnitSyncScheduledExecutorService {

    @Resource
    private UnitDataPersistenceExecutorService persistenceService;

    private Logger logger = LoggerFactory.getLogger(UnitSyncScheduledExecutorService.class);

    private ScheduledExecutorService executorService;
    private List<UnitConnectionConfig> unitsForSync = null;
    private HttpUnitSyncHandler runnableTask = null;
    private long period;
    private TimeUnit unit;
    private long initialDelay;
    private boolean isBatchSyncInProgress;
    private Map<String, ScheduledFuture> adhocSyncStatusTracker = new HashMap<String, ScheduledFuture>(10, 0.5f);
    private Runnable adhocSyncStatusUpdateThread;

    // It is a simple matter to transform an absolute time represented as a Date to the required form.
// For example, to schedule at a certain future date, you can use:
// schedule(task, date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    public UnitSyncScheduledExecutorService(List<UnitConnectionConfig> unitsForSync,long initialDelay, long period, TimeUnit unit) {
        this.unitsForSync = Collections.unmodifiableList(unitsForSync);
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    @PostConstruct
    public void start(){
        logger.debug("Starting the executor service for UnitSync");
        executorService = new ScheduledThreadPoolExecutor(DMSConstants.DEFAULT_THREAD_POOL_SIZE, new MyThreadFactory());
        this.runnableTask = new HttpUnitSyncHandler();
        executorService.scheduleAtFixedRate(runnableTask, initialDelay, period, unit);
        intAdhocSyncStatusTrackerThread();
    }

    @PreDestroy
    public void stop(){
        logger.debug("Stopping the executor service for UnitSync");
        executorService.shutdownNow();
    }


    /**
     * Runnable that performs sync operation on a scheduled basis.
     * Gets the data from the Network Unit
     */
    private class HttpUnitSyncHandler implements Runnable {
        @Override
        public void run() {
            setBatchSyncInProgress(true);
            for (UnitConnectionConfig config : unitsForSync){
                logger.info("Batch Sync: Started capturing data log for URL: {}", config.getUrl());
                Response resp = RestAssured.given().baseUri(config.getUrl()).headers(config.getHeadersMap())
                        .body(config.getBodyParams()).post();
                //resp.
                // Submit this response to other executor service which will take care of
                // reading the data from response and persist it to the ProductData table.
                logger.info("Batch Sync: Ended capturing data log for URL: {}", config.getUrl());
            }
            setBatchSyncInProgress(false);
        }
    }

    private class AdhocUnitSyncHandler implements Runnable {
        private UnitConnectionConfig syncNowConfig;

        private AdhocUnitSyncHandler(UnitConnectionConfig syncNowConfig) {
            this.syncNowConfig = syncNowConfig;
        }

        @Override
        public void run() {
            logger.info("Adhoc Sync: Started capturing data log for URL: {}", syncNowConfig.getUrl());
            Response resp = RestAssured.given().baseUri(syncNowConfig.getUrl()).headers(syncNowConfig.getHeadersMap())
                    .body(syncNowConfig.getBodyParams()).post();
            // Submit this response to other executor service which will take care of
            // reading the data from response and persist it to the ProductData table.
            logger.info("Adhoc Sync: Ended capturing data log for URL: {}", syncNowConfig.getUrl());
        }
    }

    /**
     *
     * @param projectInfo
     */
    public void triggerImmediateSync(ProjectInfo projectInfo) throws UnitSyncInProgressException {
        Integer someDelay = 5; // in seconds.
        if(projectInfo !=null){
            String unitSerialNo = projectInfo.getUnitSerialNo();
            if(isBatchSyncInProgress() || isAdhocSyncInProgress(unitSerialNo)){
                throw new UnitSyncInProgressException(DMSConstants.ADHOC_SYNC_NOT_ALLOWED.replaceFirst("%s", unitSerialNo));
            }
            ScheduledFuture future = executorService.schedule(new AdhocUnitSyncHandler(projectInfo.getUnitConnectionConfig()), someDelay, TimeUnit.SECONDS);
            adhocSyncStatusTracker.put(unitSerialNo, future);

        }
    }


    /**
     *
     * @param unitSerialNo
     * @return
     */
    public boolean isAdhocSyncInProgress(String unitSerialNo){
        ScheduledFuture future= adhocSyncStatusTracker.get(unitSerialNo);
        if(future !=null){
            logger.debug("The job may be still running or it wud have completed recently for Unit with SerialNo: {}", unitSerialNo);
            boolean isDone = future.isDone();
            return isDone;
        }
        return false;
    }

    public boolean isBatchSyncInProgress() {
        return isBatchSyncInProgress;
    }

    private void processUnitResponse(Response response){
        String contentType = response.getContentType().toLowerCase();
        UnitSyncResponse syncResponse = new UnitSyncResponse();
        syncResponse.setStatusCode(response.getStatusCode());
        syncResponse.setBody(response.getBody().asString());
        syncResponse.setHeaders(response.getHeaders().toString());
        syncResponse.setContentType(contentType);
    }

    private void intAdhocSyncStatusTrackerThread(){
        adhocSyncStatusUpdateThread = new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(adhocSyncStatusTracker !=null && adhocSyncStatusTracker.size() >0){
                        for(String unitSerial: adhocSyncStatusTracker.keySet()){
                            ScheduledFuture _future = adhocSyncStatusTracker.get(unitSerial);
                            if(_future.isCancelled()){
                                logger.error("Sync job cancelled for Unit with SerialNo: {}", unitSerial);
                                adhocSyncStatusTracker.remove(unitSerial);
                            } else if(_future.isDone()){
                                logger.info("Sync job done for Unit with SerialNo: {}", unitSerial);
                                adhocSyncStatusTracker.remove(unitSerial);
                            } else {
                                logger.debug("Sync job IN_PROGRESS for Unit with SerialNo: {}", unitSerial);
                            }
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }

    private void setBatchSyncInProgress(boolean isBatchSyncInProgress) {
        this.isBatchSyncInProgress = isBatchSyncInProgress;
    }

    private class MyThreadFactory implements ThreadFactory {

        Random random = new Random(100);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "UnitSyncThread-"+random.nextInt());
        }

    }
}
