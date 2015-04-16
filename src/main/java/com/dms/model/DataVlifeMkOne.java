/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by VamsiKrishna on 11/4/15.
 * This table is child table of DataCoreMeasurement, to persist additional fields for VLIFE MK1.
 */
@Entity
@Table(name="data_vlife_mkone")
public class DataVlifeMkOne implements Serializable{

    private static final long serialVersionUID = 5769604242250378152L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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

    @Column(name="line_voltage_two")
    private Double lineVoltageTwo;

    @Column(name="lim_resistance")
    private Double limResistance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getLineVoltageTwo() {
        return lineVoltageTwo;
    }

    public void setLineVoltageTwo(Double lineVoltageTwo) {
        this.lineVoltageTwo = lineVoltageTwo;
    }

    public Double getLimResistance() {
        return limResistance;
    }

    public void setLimResistance(Double limResistance) {
        this.limResistance = limResistance;
    }

    @Override
    public String toString() {
        return "DataVlifeMkOne{" +
                "id=" + id +
                ", mode='" + mode + '\'' +
                ", readingNumber=" + readingNumber +
                ", limVoltage=" + limVoltage +
                ", limCurrent='" + limCurrent + '\'' +
                ", temperature=" + temperature +
                ", lineVoltageTwo=" + lineVoltageTwo +
                ", limResistance=" + limResistance +
                '}';
    }
}
