/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

/**
 * Created by vamsikrishna on 19/10/14.
 */
public interface DMSConstants {
    Integer MAX_POSSIBLE_NETWORK_UNITS = 10;
    Integer DEFAULT_THREAD_POOL_SIZE = MAX_POSSIBLE_NETWORK_UNITS/2;
    static final String SESSION_USER = "USER";
    String PACKAGE_ROOT = "com.dms";
    String ADHOC_SYNC_IN_PROGRESS = "Sync In Progress for Unit with Serial No: %s";
    String ADHOC_SYNC_IN_PROGRESS_VIEW = "Sync In Progress";
    String ADHOC_SYNC_DONE = "Sync Complete for Unit with Serial No: %s";
    String ADHOC_SYNC_CANCELED = "Sync Canceled for Unit with Serial No: %s";
    String ADHOC_SYNC_NOT_ALLOWED = "Sync already In Progress for Unit with Serial No: %s, Please try later.";
}
