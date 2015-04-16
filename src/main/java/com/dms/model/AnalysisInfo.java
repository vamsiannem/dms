/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by VamsiKrishna on 12/4/15.
 */
@Entity
@Table(name = "analysis_info")
public class AnalysisInfo implements Serializable {

    private static final long serialVersionUID = 1436166722651101053L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_point")
    private float startPoint;

    @Column(name = "end_point")
    private float endPoint;

    @Column(name = "plot_location")
    private String plotLocation;

    @Column(name = "plot_color")
    private String plotColor;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "chart_type")
    private String chartType;

    @Column(name = "analysis_comments")
    private String analysisComments;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_info_id", referencedColumnName = "project_info_id", nullable = false)
    private ProjectInfo projectInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(float startPoint) {
        this.startPoint = startPoint;
    }

    public float getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(float endPoint) {
        this.endPoint = endPoint;
    }

    public String getPlotLocation() {
        return plotLocation;
    }

    public void setPlotLocation(String plotLocation) {
        this.plotLocation = plotLocation;
    }

    public String getPlotColor() {
        return plotColor;
    }

    public void setPlotColor(String plotColor) {
        this.plotColor = plotColor;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getAnalysisComments() {
        return analysisComments;
    }

    public void setAnalysisComments(String analysisComments) {
        this.analysisComments = analysisComments;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }
}
