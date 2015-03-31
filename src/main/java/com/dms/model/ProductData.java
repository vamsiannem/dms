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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"project_info_id", "vNetAddress","time"})})
public class ProductData implements Serializable {

    @ManyToOne(optional = false, targetEntity = ProjectInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_info_id", referencedColumnName = "project_info_id", nullable = false)
    private ProjectInfo projectInfo;

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

    @Column(name="l1_l2_ratio", nullable = false)
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

    @Column(name = "mode")
    private String mode;

    @Column(name = "reading_number")
    private Long readingNumber;

    @Column(name = "lim_voltage")
    private Double limVoltage;

    @Column(name = "lim_current")
    private String limCurrent;

    @Column(name= "temperature")
    private Double temperature;

    @Column(name="line_voltage2")
    private Double lineVoltage2;

    @Column(name="lim_resistance")
    private Double limResistance;

    @Column(name="vlife_mk1_column1")
    private Double vLifeMK1Column1;

    @Column(name="vlife_mk1_column2")
    private Double vLifeMK1Column2;

    @Column(name="vlife_mk1_column3")
    private Double vLifeMK1Column3;

    @Column(name="vlife_mk1_column4")
    private Double vLifeMK1Column4;

    @Column(name = "vlife_Mode")
    private Double vLifeMode;

    @Column(name= "vlife_param")
    private Double vLifeParam;

    @Column( name="vlife_voltage")
    private Double vLifeVoltage;

    @Column(name="vlim_mk2_type3_column1")
    private String vLimMK2TY3Column1;

    @Column(name="vlim_mk2_type3_column2")
    private String vLimMK2TY3Column2;

    @Column(name="vlim_mk2_type3_column3")
    private String vLimMK2TY3Column3;

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


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getReadingNumber() {
        return readingNumber;
    }

    public void setReadingNumber(Long readingNumber) {
        this.readingNumber = readingNumber;
    }

    public Double getLimVoltage() {
        return limVoltage;
    }

    public void setLimVoltage(Double limVoltage) {
        this.limVoltage = limVoltage;
    }

    public String getLimCurrent() {
        return limCurrent;
    }

    public void setLimCurrent(String limCurrent) {
        this.limCurrent = limCurrent;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getLineVoltage2() {
        return lineVoltage2;
    }

    public void setLineVoltage2(Double lineVoltage2) {
        this.lineVoltage2 = lineVoltage2;
    }

    public Double getLimResistance() {
        return limResistance;
    }

    public void setLimResistance(Double limResistance) {
        this.limResistance = limResistance;
    }

    public Double getvLifeMK1Column1() {
        return vLifeMK1Column1;
    }

    public void setvLifeMK1Column1(Double vLifeMK1Column1) {
        this.vLifeMK1Column1 = vLifeMK1Column1;
    }

    public Double getvLifeMK1Column2() {
        return vLifeMK1Column2;
    }

    public void setvLifeMK1Column2(Double vLifeMK1Column2) {
        this.vLifeMK1Column2 = vLifeMK1Column2;
    }

    public Double getvLifeMK1Column3() {
        return vLifeMK1Column3;
    }

    public void setvLifeMK1Column3(Double vLifeMK1Column3) {
        this.vLifeMK1Column3 = vLifeMK1Column3;
    }

    public Double getvLifeMK1Column4() {
        return vLifeMK1Column4;
    }

    public void setvLifeMK1Column4(Double vLifeMK1Column4) {
        this.vLifeMK1Column4 = vLifeMK1Column4;
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

    public String getvLimMK2TY3Column1() {
        return vLimMK2TY3Column1;
    }

    public void setvLimMK2TY3Column1(String vLimMK2TY3Column1) {
        this.vLimMK2TY3Column1 = vLimMK2TY3Column1;
    }

    public String getvLimMK2TY3Column2() {
        return vLimMK2TY3Column2;
    }

    public void setvLimMK2TY3Column2(String vLimMK2TY3Column2) {
        this.vLimMK2TY3Column2 = vLimMK2TY3Column2;
    }

    public String getvLimMK2TY3Column3() {
        return vLimMK2TY3Column3;
    }

    public void setvLimMK2TY3Column3(String vLimMK2TY3Column3) {
        this.vLimMK2TY3Column3 = vLimMK2TY3Column3;
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

    @Override
    public String toString() {
        Long projectInfoId = this.getProjectInfo() != null ? this.getProjectInfo().getProjectInfoId(): null;
        return "ProductData{" +
                "time='" + time + '\'' +
                ", vNetAddress='" + vNetAddress + '\'' +
                ", dataType=" + dataType +
                ", status='" + status + '\'' +
                ", projectInfoId=" + projectInfoId +
                '}';
    }
}
