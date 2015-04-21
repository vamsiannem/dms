/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.dto;

/**
 * Created by VamsiKrishna on 14/3/15.
 */
public class ProjectDataTimeLimit {

    public ProjectDataTimeLimit(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    private String fromDate;
    private String toDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
