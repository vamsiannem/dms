/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

import com.dms.dto.UnitSyncResponse;
import com.dms.utils.DMSConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Vamsi on 20/2/15.
 */
@Component
public class UnitDataPersistenceExecutorService {

    private static final int SLEEP_TIME = 3000;
    private static final Logger logger = LoggerFactory.getLogger(UnitDataPersistenceExecutorService.class);
    private ExecutorService executorService = null;
    private Runnable persistRunnableTask = null;
    private SyncResponseCache cache;


    public UnitDataPersistenceExecutorService(SyncResponseCache cache) {
        this.cache = cache;
    }

    @PostConstruct
    public void start(){
        logger.debug("Starting the executor service for Unit Data persistence");
        executorService = Executors.newSingleThreadExecutor(new MyThreadFactory());
        this.persistRunnableTask = new MyPersistRunnable(this);
        executorService.submit(persistRunnableTask);
    }

    @PreDestroy
    public void stop(){
        logger.debug("Stopping the executor service for Unit Data persistence");
        executorService.shutdownNow();
    }

    private class MyPersistRunnable implements Runnable {
        UnitDataPersistenceExecutorService parent;

        public MyPersistRunnable(UnitDataPersistenceExecutorService parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    UnitSyncResponse item = parent.retrieveNextItem();
                    if(item != null){
                        parent.persistUnitData(item);
                    }
                    Thread.currentThread().sleep(SLEEP_TIME);
                } catch ( Exception e) {
                    logger.error(e.getMessage());
                    //e.printStackTrace();
                }
            }
        }
    }

    private void persistUnitData(UnitSyncResponse syncResponse){
        // write the magic code to persist the unit data on product_data table.
    }

    private class MyThreadFactory implements ThreadFactory {

        Random random = new Random(100);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "UnitDataPersistence-"+random.nextInt());
        }

    }

    private UnitSyncResponse retrieveNextItem(){
        if(Thread.currentThread().isInterrupted()){
            return  null;
        }
        return cache.isEmpty() ? null : cache.popItem();
    }

    public SyncResponseCache getResponse() {
        return cache;
    }


}
