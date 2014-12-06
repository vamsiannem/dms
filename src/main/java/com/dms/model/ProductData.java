/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Uploaded CSV data model class.
 * @author vamsikrishna_a
 */
@Entity
@Table(name="product_data")
public class ProductData implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="company_name", nullable=false)
    private String companyName;

    @Column(name="unit_serial_no", nullable=false)
    private String unitSerialNo;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getvNetAddress() {
        return vNetAddress;
    }

    public void setvNetAddress(String vNetAddress) {
        this.vNetAddress = vNetAddress;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLimImbalance() {
        return limImbalance;
    }

    public void setLimImbalance(Double limImbalance) {
        this.limImbalance = limImbalance;
    }

    public Double getLimResistance() {
        return limResistance;
    }

    public void setLimResistance(Double limResistance) {
        this.limResistance = limResistance;
    }

    public String getLimCapacitance() {
        return limCapacitance;
    }

    public void setLimCapacitance(String limCapacitance) {
        this.limCapacitance = limCapacitance;
    }

    public Double getLimResistanceCm() {
        return limResistanceCm;
    }

    public void setLimResistanceCm(Double limResistanceCm) {
        this.limResistanceCm = limResistanceCm;
    }

    public String getLimCapacitanceCm() {
        return limCapacitanceCm;
    }

    public void setLimCapacitanceCm(String limCapacitanceCm) {
        this.limCapacitanceCm = limCapacitanceCm;
    }

    public Double getLineVoltage() {
        return lineVoltage;
    }

    public void setLineVoltage(Double lineVoltage) {
        this.lineVoltage = lineVoltage;
    }

    public Double getLineCurrent() {
        return lineCurrent;
    }

    public void setLineCurrent(Double lineCurrent) {
        this.lineCurrent = lineCurrent;
    }

    public Double getLineFrequency() {
        return lineFrequency;
    }

    public void setLineFrequency(Double lineFrequency) {
        this.lineFrequency = lineFrequency;
    }

    public Double getLinePhase() {
        return linePhase;
    }

    public void setLinePhase(Double linePhase) {
        this.linePhase = linePhase;
    }
}
