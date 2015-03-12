/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.model;

import com.dms.annotation.Json;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * Uploaded CSV data model class.
 * @author vamsikrishna_a
 */

@Entity
@Table(name="product_data",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"network_unit_id", "vNetAddress","time"})})
public class ProductData implements Serializable {

    @ManyToOne(optional = false, targetEntity = NetworkUnit.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "network_unit_id", referencedColumnName = "project_info_id", nullable = false)
    private NetworkUnit networkUnit;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column( name="time")
    private String time;

    @Column(name="vNetAddress", nullable=false)
    private String vNetAddress;

    @Column( name="type")
    private Integer type;

    @Column( name="status")
    private String status;

    @Column(name="lim_imbalance")
    private Double limImbalance;

    @Column(name="lim_resistance")
    private Double limResistance;

    @Column(name="lim_capacitance")
    private String limCapacitance;

    @Column(name= "lim_resistance_cm")
    private Double limResistanceCm;

    @Column( name="lim_capacitance_cm")
    private String limCapacitanceCm;

    @Column(name="line_voltage")
    private Double lineVoltage;

    @Column(name="line_current")
    private Double lineCurrent;

    @Column(name="line_frequency")
    private Double lineFrequency;

    @Column(name="line_phase")
    private Double linePhase;

    @Json
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Json
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Json
    public String getvNetAddress() {
        return vNetAddress;
    }

    public void setvNetAddress(String vNetAddress) {
        this.vNetAddress = vNetAddress;
    }

    @Json
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Json
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Json
    public Double getLimImbalance() {
        return limImbalance;
    }

    public void setLimImbalance(Double limImbalance) {
        this.limImbalance = limImbalance;
    }

    @Json
    public Double getLimResistance() {
        return limResistance;
    }

    public void setLimResistance(Double limResistance) {
        this.limResistance = limResistance;
    }

    @Json
    public String getLimCapacitance() {
        return limCapacitance;
    }

    public void setLimCapacitance(String limCapacitance) {
        this.limCapacitance = limCapacitance;
    }

    @Json
    public Double getLimResistanceCm() {
        return limResistanceCm;
    }

    public void setLimResistanceCm(Double limResistanceCm) {
        this.limResistanceCm = limResistanceCm;
    }

    @Json
    public String getLimCapacitanceCm() {
        return limCapacitanceCm;
    }

    public void setLimCapacitanceCm(String limCapacitanceCm) {
        this.limCapacitanceCm = limCapacitanceCm;
    }

    @Json
    public Double getLineVoltage() {
        return lineVoltage;
    }

    public void setLineVoltage(Double lineVoltage) {
        this.lineVoltage = lineVoltage;
    }

    @Json
    public Double getLineCurrent() {
        return lineCurrent;
    }

    public void setLineCurrent(Double lineCurrent) {
        this.lineCurrent = lineCurrent;
    }

    @Json
    public Double getLineFrequency() {
        return lineFrequency;
    }

    public void setLineFrequency(Double lineFrequency) {
        this.lineFrequency = lineFrequency;
    }

    @Json
    public Double getLinePhase() {
        return linePhase;
    }

    public void setLinePhase(Double linePhase) {
        this.linePhase = linePhase;
    }

    public NetworkUnit getNetworkUnit() {
        return networkUnit;
    }

    public void setNetworkUnit(NetworkUnit networkUnit) {
        this.networkUnit = networkUnit;
    }

    @Override
    public String toString() {
        Long projectInfoId = this.getNetworkUnit() != null ? this.getNetworkUnit().getProjectInfoId(): null;
        return "ProductData{" +
                "time='" + time + '\'' +
                ", vNetAddress='" + vNetAddress + '\'' +
                ", type=" + type +
                ", status='" + status + '\'' +
                ", projectInfoId=" + projectInfoId +
                '}';
    }
}
