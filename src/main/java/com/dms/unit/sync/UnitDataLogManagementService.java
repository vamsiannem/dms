/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

import com.dms.model.ProjectInfo;


/**
 * Created by Vamsi 19/2/15.
 */
public interface UnitDataLogManagementService {
    void syncDataFor(ProjectInfo unit);
    String getSyncStatus(ProjectInfo unit);
    String[] getSyncStatus();

}
