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

    String UPLOAD_CSV_NAME_PREFIX = "vlim-datalog";
    String VLIFE_MK1_TYPE_IDFR_STR = "V-LIFE Started";
    String VLIFE_MK2_TY4_TYPE_IDFR_STR = "VlifeMode";
    String VLIM_TYPE_IDFR_STR = "LimResistanceCm,LimCapacitanceCm,LineVoltage";
    String UPLOAD_CSV_SPLIT =",";
    int VLIFE_MK1_NUM_OF_COLS = 17;
    int VLIFE_MK2_TY4_NUM_OF_COLS = 9;
    int VLIM_MK1_NUM_OF_COLS = 13;
    int VLIM_MK2_TY3_NUM_OF_COLS = 16;

    String[] VLIFE_MK1_HEADERS = {"time","dataType","status","mode", "readingNumber", "insulationResistance",
            "limVoltage", "limCurrent", "temperature", "lineVoltage", "lineVoltage2",
            "lineCurrent", "limResistance", "vLifeMK1Column1", "vLifeMK1Column2", "vLifeMK1Column3", "vLifeMK1Column4"};
    String[] VLIFE_MK2_TY4_HEADERS = {"time" , "vNetAddress" , "dataType" , "status" , "insulationResistance" , "lineVoltage" , "vLifeMode" ,"vLifeParam" , "vLifeVoltage"};
    String[] VLIM_MK1_HEADERS = { "time", "vNetAddress", "dataType", "status",
            "l1L2Ratio", "insulationResistance", "insulationCapacitance", "downstreamInsulationResistance",
            "downstreamInsulationCapacitance", "lineVoltage", "lineCurrent", "lineFrequency", "linePhase" };
    String[] VLIM_MK2_TY3_HEADERS = { "time", "vNetAddress", "dataType", "status",
            "l1L2Ratio", "insulationResistance", "insulationCapacitance", "downstreamInsulationResistance",
            "downstreamInsulationCapacitance", "lineVoltage", "lineCurrent", "lineFrequency",
            "linePhase", "vLimMK2TY3Column1", "vLimMK2TY3Column2", "vLimMK2TY3Column3" };
}
