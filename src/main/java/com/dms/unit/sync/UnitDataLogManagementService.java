/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.unit.sync;

import com.dms.model.NetworkUnit;


/**
 * Created by Vamsi 19/2/15.
 */
public interface UnitDataLogManagementService {
    void syncDataFor(NetworkUnit unit);
    String getSyncStatus(NetworkUnit unit);
    String[] getSyncStatus();

}
