/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by VamsiKrishna on 11/4/15
 * Over a period of time, we may have to replace the current UNIT with new one for a client.
 * This table is to track the changes in the unit for a project.
 */
@Entity
@Table(name = "project_history_info")
public class ProjectHistoryInfo implements Serializable, Comparable<ProjectHistoryInfo>{

    private static final long serialVersionUID = -2122913247547157684L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_info_id", referencedColumnName = "project_info_id")
    private ProjectInfo projectInfo;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "unit_serial_no")
    private String unitSerialNo;

    /*
        when this unit is installed on client location.
        User is expected to enter a date, while creating a project.
     */
    @Column(name = "installation_date", nullable = false, columnDefinition = "TIME")
    @Temporal(value = TemporalType.TIME)
    private Date installationDate;

    // When the unit has been dismantled and another unit is installed.
    @Column(name = "decommission_date", columnDefinition = "TIME")
    @Temporal(value = TemporalType.TIME)
    private Date deCommissionedDate;

    @Column(name="consigned_engineer")
    private String consignedEngineer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitSerialNo() {
        return unitSerialNo;
    }

    public void setUnitSerialNo(String unitSerialNo) {
        this.unitSerialNo = unitSerialNo;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public Date getDeCommissionedDate() {
        return deCommissionedDate;
    }

    public void setDeCommissionedDate(Date deCommissionedDate) {
        this.deCommissionedDate = deCommissionedDate;
    }

    public String getConsignedEngineer() {
        return consignedEngineer;
    }

    public void setConsignedEngineer(String consignedEngineer) {
        this.consignedEngineer = consignedEngineer;
    }


    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @Override
    public int compareTo(ProjectHistoryInfo o) {
        return this.getInstallationDate().compareTo(o.getInstallationDate());
    }

    @Override
    public String toString() {
        return "ProjectHistoryInfo{" +
                "id=" + id +
                ", unitSerialNo='" + unitSerialNo + '\'' +
                ", installationDate=" + installationDate +
                ", deCommissionedDate=" + deCommissionedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectHistoryInfo that = (ProjectHistoryInfo) o;

        if (!unitSerialNo.equals(that.unitSerialNo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return unitSerialNo.hashCode();
    }
}
