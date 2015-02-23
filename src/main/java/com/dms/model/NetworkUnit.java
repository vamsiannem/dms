/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by support on 25/1/15.
 */
@Entity
@Table(name = "network_unit")
public class NetworkUnit implements Serializable {

    @Id
    @Column(name="project_info_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long projectInfoId;

    @Column(name="project_id", length = 6)
    private String projectId;

    @Column(name="company_name", nullable=false)
    private String companyName;

    @Column(name="unit_serial_no", nullable=false)
    private String unitSerialNo;

    @Column(name="platform", nullable=false)
    private String platform;

    @Column(name="control_system", nullable=false)
    private String controlSystem;

    @Column(name="channel", nullable=false)
    private String channel;

    @Column(name="ip_address", nullable=false)
    private String ipAddress;

    @Column(name="is_alive", nullable=false)
    private boolean isAlive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_config_id", referencedColumnName = "id", nullable = false)
    private UnitConnectionConfig unitConnectionConfig;

    @OneToMany(mappedBy = "networkUnit", fetch = FetchType.LAZY)
    private Collection<UnitSyncStatus> unitSyncStatus;

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
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public UnitConnectionConfig getUnitConnectionConfig() {
        return unitConnectionConfig;
    }

    public void setUnitConnectionConfig(UnitConnectionConfig unitConnectionConfig) {
        this.unitConnectionConfig = unitConnectionConfig;
    }
}
