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
 * Created by VamsiKrishna on 11/4/15.
 * This table is used to maintain history of unit upgrade information.
 */
@Entity
@Table(name = "product_version_history")
public class ProductVersionHistory implements Comparable<ProductVersionHistory>, Serializable{

    private static final long serialVersionUID = 5997389020571989203L;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_info_id", referencedColumnName = "id", nullable = false)
    private ProductInfo productInfo;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "software_version")
    private String softwareVersion;

    @Column(name = "hardware_update")
    private String hardwareVersion;

    @Column(name = "description")
    private String description;

    @Column(name = "test_engineer")
    private String testEngineer;

    @Column(name = "tested_date", nullable = false, columnDefinition = "TIME")
    @Temporal(value = TemporalType.TIME)
    private Date testedDate;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestEngineer() {
        return testEngineer;
    }

    public void setTestEngineer(String testEngineer) {
        this.testEngineer = testEngineer;
    }

    public Date getTestedDate() {
        return testedDate;
    }

    public void setTestedDate(Date testedDate) {
        this.testedDate = testedDate;
    }

    @Override
    public int compareTo(ProductVersionHistory versionHistory) {
        if(versionHistory==null)
            return 1;
        return this.getTestedDate().compareTo(versionHistory.getTestedDate());
    }

    @Override
    public String toString() {
        return "ProductVersionHistory{" +
                "id=" + id +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", hardwareVersion='" + hardwareVersion + '\'' +
                ", description='" + description + '\'' +
                ", testEngineer='" + testEngineer + '\'' +
                ", testedDate=" + testedDate +
                '}';
    }
}
