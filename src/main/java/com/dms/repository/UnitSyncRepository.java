/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.UnitSyncStatus;

import java.util.Collection;
import java.util.Map;

/**
 * Created by support on 1/2/15.
 */
public interface UnitSyncRepository {

    /**
     * Get last run status for a network unit
     * @param unitProjectId
     * @return
     */
    UnitSyncStatus getLastRun(String unitProjectId);

    /**
     * Get last run status for all the units.
     * @return
     */
    Collection<UnitSyncStatus> getLastRun();

    /**
     * Before starting the sync process, mark start of sync in database
     * @param syncStatus
     */
    void startSync(UnitSyncStatus syncStatus);

    /**
     * After finishing the unit sync process, update the status in database.
     * @param syncStatus
     */
    void updateSyncStatus(UnitSyncStatus syncStatus );


    /**
     * Get last 7 runs sync status. This will help
     * @return
     */
    public Map<String, Collection<UnitSyncStatus>> getLastWeekSyncStatus();




}
