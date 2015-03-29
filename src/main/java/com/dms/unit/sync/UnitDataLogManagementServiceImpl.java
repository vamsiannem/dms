/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

import com.dms.model.ProjectInfo;
import com.dms.utils.DMSConstants;
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
    public void syncDataFor(ProjectInfo unit) {

    }

    @Override
    public String getSyncStatus(ProjectInfo unit) {
        return syncService.isAdhocSyncInProgress(unit.getUnitSerialNo())
                ? DMSConstants.ADHOC_SYNC_IN_PROGRESS.replace("%s", unit.getUnitSerialNo()):
                DMSConstants.ADHOC_SYNC_DONE;
    }

    @Override
    public String[] getSyncStatus() {
        return new String[0];
    }
}
