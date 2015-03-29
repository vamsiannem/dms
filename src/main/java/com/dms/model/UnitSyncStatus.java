/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vamsi Krishna on 26/1/15.
 */
@Entity
@Table(name = "unit_sync_status")
public class UnitSyncStatus implements Serializable {

    @Id
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_info_id", referencedColumnName = "project_info_id", nullable = false)
    private ProjectInfo projectInfo;

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

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
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
