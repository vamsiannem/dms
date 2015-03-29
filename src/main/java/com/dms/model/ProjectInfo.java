/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by support on 25/1/15.
 */
@Entity
@Table(name = "project_info")
public class ProjectInfo implements Serializable {

    @Id
    @Column(name="project_info_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long projectInfoId;

    @Column(name="project_id", length = 6)
    private String projectId;

    @Column(name="unit_serial_no", nullable=false)
    private String unitSerialNo;

    @Column(name="platform", nullable=false)
    private String platform;

    @Column(name="control_system", nullable=false)
    private String controlSystem;

    @Column(name="channel", nullable=false)
    private String channel;

    @Column(name = "description")
    private String description;

    /*
        when this unit is installed on client location.
        User is expected to enter a date, while creating a project.
     */
    @Column(name = "installation_date", nullable = false, columnDefinition = "TIME")
    @Temporal(value = TemporalType.TIME)
    private Date installationDate;

    @Column(name="ip_address", nullable=false)
    private String ipAddress;

    @Column(name="is_alive", nullable=false)
    private boolean isAlive;

    /*
       Who has created this project in DMS system.
       Currently only admin user is defined in this system.
     */
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    /*
        When this project is created.
        Defaults to current system timestamp.
     */
    @Column(name = "last_modified_date", nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "part_no")
    private String partNo;

    @Column(name = "refNo")
    private String refNo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_config_id", referencedColumnName = "id", nullable = false)
    private UnitConnectionConfig unitConnectionConfig;

    @OneToMany(mappedBy = "projectInfo", fetch = FetchType.EAGER)
    private Collection<UnitSyncStatus> unitSyncStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_info_id", referencedColumnName = "id", nullable = false)
    private ClientInfo clientInfo;

    public Long getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Long projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyName() {
        if(clientInfo!=null)
            return clientInfo.getName();
        return null;
    }

    public String getUnitSerialNo() {
        return unitSerialNo;
    }

    public void setUnitSerialNo(String unitSerialNo) {
        this.unitSerialNo = unitSerialNo;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getControlSystem() {
        return controlSystem;
    }

    public void setControlSystem(String controlSystem) {
        this.controlSystem = controlSystem;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public UnitConnectionConfig getUnitConnectionConfig() {
        return unitConnectionConfig;
    }

    public void setUnitConnectionConfig(UnitConnectionConfig unitConnectionConfig) {
        this.unitConnectionConfig = unitConnectionConfig;
    }

    public Collection<UnitSyncStatus> getUnitSyncStatus() {
        return unitSyncStatus;
    }

    public void setUnitSyncStatus(Collection<UnitSyncStatus> unitSyncStatus) {
        this.unitSyncStatus = unitSyncStatus;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
