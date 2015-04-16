/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.SortedSet;

/**
 * Created by VamsiKrishna on 11/4/15.
 * This table is related to the product which has the master data about the UNIT
 */

@Entity
@Table(name = "product_info")
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 2765686997919770081L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_serial_no")
    private String unitSerialNo;

    @Column(name = "part_no")
    private String partNo;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "productInfo", fetch = FetchType.LAZY, targetEntity = ProjectInfo.class)
    @JsonIgnore
    private ProjectInfo projectInfo;

    @Sort(type = SortType.NATURAL)
    @OneToMany(mappedBy = "productInfo", targetEntity = ProductVersionHistory.class, fetch = FetchType.EAGER)
    private SortedSet<ProductVersionHistory> productVersionHistories;

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

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public SortedSet<ProductVersionHistory> getProductVersionHistories() {
        return productVersionHistories;
    }

    public void setProductVersionHistories(SortedSet<ProductVersionHistory> productVersionHistories) {
        this.productVersionHistories = productVersionHistories;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", unitSerialNo='" + unitSerialNo + '\'' +
                ", partNo='" + partNo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
