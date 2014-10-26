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
    
    @Column( name="entry_date")
    private String entryDate;
    
    @Column( name="ty")
    private String ty;
    
    @Column( name="status")
    private String status;
    
    @Column(name="mode")
    private Long mode;
    
    @Column(name="read_no")
    private Long readNo;
    
    @Column(name="IRreading")
    private Float IRreading;
    
    @Column(name= "limV")
    private Float limV;
   
    @Column( name="limC")
    private Float limC;
    
    @Column(name="temperature")
    private Float temperature;
    
    @Column(name="EPUIn")
    private Float EPUIn;
    
    @Column(name="EPUOut")
    private Float EPUOut;
    
    @Column(name="limR")
    private Float limR;
    
    @Column(name="c12")
    private Integer c12;
    
    @Column(name="c13")
    private Integer c13;
    
    @Column(name="c14")
    private Integer c14;
    
    @Column(name="c15")
    private Integer c15;

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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMode() {
        return mode;
    }

    public void setMode(Long mode) {
        this.mode = mode;
    }

    public Long getReadNo() {
        return readNo;
    }

    public void setReadNo(Long readNo) {
        this.readNo = readNo;
    }

    public Float getIRreading() {
        return IRreading;
    }

    public void setIRreading(Float IRreading) {
        this.IRreading = IRreading;
    }

    public Float getLimV() {
        return limV;
    }

    public void setLimV(Float limV) {
        this.limV = limV;
    }

    public Float getLimC() {
        return limC;
    }

    public void setLimC(Float limC) {
        this.limC = limC;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getEPUIn() {
        return EPUIn;
    }

    public void setEPUIn(Float EPUIn) {
        this.EPUIn = EPUIn;
    }

    public Float getEPUOut() {
        return EPUOut;
    }

    public void setEPUOut(Float EPUOut) {
        this.EPUOut = EPUOut;
    }

    public Float getLimR() {
        return limR;
    }

    public void setLimR(Float limR) {
        this.limR = limR;
    }

    public Integer getC12() {
        return c12;
    }

    public void setC12(Integer c12) {
        this.c12 = c12;
    }

    public Integer getC13() {
        return c13;
    }

    public void setC13(Integer c13) {
        this.c13 = c13;
    }

    public Integer getC14() {
        return c14;
    }

    public void setC14(Integer c14) {
        this.c14 = c14;
    }

    public Integer getC15() {
        return c15;
    }

    public void setC15(Integer c15) {
        this.c15 = c15;
    }
   
}
