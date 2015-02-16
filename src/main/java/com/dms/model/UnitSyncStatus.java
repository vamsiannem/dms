/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by support on 26/1/15.
 */
@Entity
@Table(name = "unit_sync_status")
public class UnitSyncStatus implements Serializable {

    @Id
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unit_project_id", referencedColumnName = "project_id", nullable = false)
    private NetworkUnit networkUnit;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "is_sync_success")
    private boolean isSyncSuccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NetworkUnit getNetworkUnit() {
        return networkUnit;
    }

    public void setNetworkUnit(NetworkUnit networkUnit) {
        this.networkUnit = networkUnit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isSyncSuccess() {
        return isSyncSuccess;
    }

    public void setSyncSuccess(boolean isSyncSuccess) {
        this.isSyncSuccess = isSyncSuccess;
    }
}