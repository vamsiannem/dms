/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by VamsiKrishna on 12/4/15.
 * This table is created for future discussion on implementation of reports.
 * Expected to get the data type of each field in further discussions
 */
@Entity
@Table(name = "report_info")
public class ReportInfo implements Serializable{

    private static final long serialVersionUID = 882499287796398700L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "present_ir")
    private String presentIr;

    @Column(name = "project_no")
    private String projectNo;

    @Column(name = "document_no")
    private String documentNo;

    @Column(name = "rev_no")
    private String revNo;

    @Column(name = "unit_events")
    private String unitEvents;

    @Column(name = "engg_comments")
    private String enggComments;

    @Column(name = "recommendation")
    private String recommendation;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = ProjectInfo.class)
    @JoinColumn(name = "project_info_id", referencedColumnName = "project_info_id", nullable = false)
    private ProjectInfo projectInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentIr() {
        return presentIr;
    }

    public void setPresentIr(String presentIr) {
        this.presentIr = presentIr;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getRevNo() {
        return revNo;
    }

    public void setRevNo(String revNo) {
        this.revNo = revNo;
    }

    public String getUnitEvents() {
        return unitEvents;
    }

    public void setUnitEvents(String unitEvents) {
        this.unitEvents = unitEvents;
    }

    public String getEnggComments() {
        return enggComments;
    }

    public void setEnggComments(String enggComments) {
        this.enggComments = enggComments;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }
}
