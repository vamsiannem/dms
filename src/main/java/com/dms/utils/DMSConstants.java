/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

/**
 * Created by vamsikrishna on 19/10/14.
 */
public interface DMSConstants {
    Integer MAX_POSSIBLE_NETWORK_UNITS = 10;
    Integer DEFAULT_THREAD_POOL_SIZE = MAX_POSSIBLE_NETWORK_UNITS/2;
    static final String SESSION_USER_FULL_NAME = "USER_NAME";
    static final String SESSION_USER_ID = "USER_ID";
    String PACKAGE_ROOT = "com.dms";
    String ADHOC_SYNC_IN_PROGRESS = "Sync In Progress for Unit with Serial No: %s";
    String ADHOC_SYNC_IN_PROGRESS_VIEW = "Sync In Progress";
    String ADHOC_SYNC_DONE = "Sync Complete for Unit with Serial No: %s";
    String ADHOC_SYNC_CANCELED = "Sync Canceled for Unit with Serial No: %s";
    String ADHOC_SYNC_NOT_ALLOWED = "Sync already In Progress for Unit with Serial No: %s, Please try later.";

    String DUMMY_URL ="http://abc.com/dataLog";
    String DUMMY_METHOD = "GET";
    String DUMMY_HEADERS = "Content-type:application/json;Accept:application/json";
    String DUMMY_PARAM = "type=0&vNetAddress=0&period=10";

    String EXPORT_FILE_PREFIX = "vlim-datalog_";
    String SUFFIX_CSV = ".csv";
    String[] HEADERS_CSV = new String[] { "Time", "vNetAddress", "Type", "Status",
            "limImbalance", "limResistance", "limCapacitance", "limResistanceCm",
            "limCapacitanceCm", "lineVoltage", "lineCurrent", "lineFrequency", "linePhase" };
}
