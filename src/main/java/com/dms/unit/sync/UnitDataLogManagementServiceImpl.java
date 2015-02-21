/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

import com.dms.model.NetworkUnit;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Vamsi on 19/2/15.
 */
@Component
public class UnitDataLogManagementServiceImpl implements UnitDataLogManagementService {

    @Resource
    private UnitSyncScheduledExecutorService syncService;

    @Override
    public void syncDataFor(NetworkUnit unit) {

    }

    @Override
    public String getSyncStatus(NetworkUnit unit) {
        return syncService.isAdhocSyncInProgress(unit.getUnitSerialNo());
    }

    @Override
    public String[] getSyncStatus() {
        return new String[0];
    }
}
