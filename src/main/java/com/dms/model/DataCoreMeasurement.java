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
@Table(name="data_core_measurements",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"project_info_id", "vNetAddress","time"})})
public class DataCoreMeasurement implements Serializable {

    private static final long serialVersionUID = -671385228972170150L;
    @ManyToOne(optional = false, targetEntity = ProjectInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_info_id", referencedColumnName = "project_info_id", nullable = false)
    private ProjectInfo projectInfo;

    @OneToOne(optional = true, targetEntity = DataVlifeMkOne.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "data_vlife_mkone_id", referencedColumnName = "id", nullable = true)
    private DataVlifeMkOne dataVlifeMkOne;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column( name="time", nullable = false)
    private String time;

    @Column(name="vNetAddress")
    private String vNetAddress;

    @Column( name="data_type", nullable = false)
    private Integer dataType;

    @Column( name="status", nullable = false)
    private String status;

    @Column(name="l1_l2_ratio")
    private Double l1L2Ratio;

    @Column(name="insulation_resistance")
    private Double insulationResistance;

    @Column(name="insulation_capacitance")
    private String insulationCapacitance;

    @Column(name= "downstream_insulation_resistance")
    private Double downstreamInsulationResistance;

    @Column( name="downstream_insulation_capacitance")
    private String downstreamInsulationCapacitance;

    @Column(name="line_voltage")
    private Double lineVoltage;

    @Column(name="line_current")
    private Double lineCurrent;

    @Column(name="line_frequency")
    private Double lineFrequency;

    @Column(name="line_phase")
    private Double linePhase;

    @Column(name = "vlife_Mode")
    private Double vLifeMode;

    @Column(name= "vlife_param")
    private Double vLifeParam;

    @Column( name="vlife_voltage")
    private Double vLifeVoltage;

    @Column(name="live_earth_noise")
    private String liveEarthNoise;

    @Column(name="high_sample_insulation_capacitance")
    private String highSampleInsulationCapacitance;


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
    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    @Json
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Json
    public Double getL1L2Ratio() {
        return l1L2Ratio;
    }

    public void setL1L2Ratio(Double l1L2Ratio) {
        this.l1L2Ratio = l1L2Ratio;
    }

    @Json
    public Double getInsulationResistance() {
        return insulationResistance;
    }

    public void setInsulationResistance(Double insulationResistance) {
        this.insulationResistance = insulationResistance;
    }

    @Json
    public String getInsulationCapacitance() {
        return insulationCapacitance;
    }

    public void setInsulationCapacitance(String insulationCapacitance) {
        this.insulationCapacitance = insulationCapacitance;
    }

    @Json
    public Double getDownstreamInsulationResistance() {
        return downstreamInsulationResistance;
    }

    public void setDownstreamInsulationResistance(Double downstreamInsulationResistance) {
        this.downstreamInsulationResistance = downstreamInsulationResistance;
    }

    @Json
    public String getDownstreamInsulationCapacitance() {
        return downstreamInsulationCapacitance;
    }

    public void setDownstreamInsulationCapacitance(String downstreamInsulationCapacitance) {
        this.downstreamInsulationCapacitance = downstreamInsulationCapacitance;
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

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }


    public Double getvLifeMode() {
        return vLifeMode;
    }

    public void setvLifeMode(Double vLifeMode) {
        this.vLifeMode = vLifeMode;
    }

    public Double getvLifeParam() {
        return vLifeParam;
    }

    public void setvLifeParam(Double vLifeParam) {
        this.vLifeParam = vLifeParam;
    }

    public Double getvLifeVoltage() {
        return vLifeVoltage;
    }

    public void setvLifeVoltage(Double vLifeVoltage) {
        this.vLifeVoltage = vLifeVoltage;
    }

    public String getLiveEarthNoise() {
        return liveEarthNoise;
    }

    public void setLiveEarthNoise(String liveEarthNoise) {
        this.liveEarthNoise = liveEarthNoise;
    }

    public String getHighSampleInsulationCapacitance() {
        return highSampleInsulationCapacitance;
    }

    public void setHighSampleInsulationCapacitance(String highSampleInsulationCapacitance) {
        this.highSampleInsulationCapacitance = highSampleInsulationCapacitance;
    }

    /// specific to child class getters & setters
    public String getMode() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getMode(): "";
    }

    public void setMode(String mode) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setMode(mode);
    }

    public Long getReadingNumber() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getReadingNumber(): 0;
    }

    public void setReadingNumber(Long readingNumber) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setReadingNumber(readingNumber);
    }

    public Double getLimVoltage() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getLimVoltage(): 0.0;
    }

    public void setLimVoltage(Double limVoltage) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setLimVoltage(limVoltage);
    }

    public String getLimCurrent() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getLimCurrent(): "";
    }

    public void setLimCurrent(String limCurrent) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setLimCurrent(limCurrent);
    }

    public Double getTemperature() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getTemperature(): 0.0;
    }

    public void setTemperature(Double temperature) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setTemperature(temperature);
    }

    public Double getLineVoltageTwo() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getLineVoltageTwo(): 0.0;
    }

    public void setLineVoltageTwo(Double lineVoltageTwo) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setLineVoltageTwo(lineVoltageTwo);
    }

    public Double getLimResistance() {
        return dataVlifeMkOne!=null ? this.dataVlifeMkOne.getLimResistance(): 0.0;
    }

    public void setLimResistance(Double limResistance) {
        initVlifeMkOne();
        this.dataVlifeMkOne.setLimResistance(limResistance);
    }

    @Override
    public String toString() {
        Long projectInfoId = this.getProjectInfo() != null ? this.getProjectInfo().getProjectInfoId(): null;
        return "DataCoreMeasurement{" +
                "time='" + time + '\'' +
                ", vNetAddress='" + vNetAddress + '\'' +
                ", dataType=" + dataType +
                ", status='" + status + '\'' +
                ", projectInfoId=" + projectInfoId +
                '}';
    }

    private void initVlifeMkOne() {
        if(dataVlifeMkOne==null){
            dataVlifeMkOne = new DataVlifeMkOne();
        }
    }
}
