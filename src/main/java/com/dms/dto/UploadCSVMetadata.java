/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.dto;

import com.dms.utils.DateUtils;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.util.Date;

/**
 * Created by VamsiKrishna on 31/3/15.
 */
public class UploadCSVMetadata {

    public UploadCSVMetadata(String[] csvHeaders, int numOfcols, String typeIdentifierString) {
        this.csvHeaders = csvHeaders;
        this.numOfcols = numOfcols;
        this.typeIdentifierString = typeIdentifierString;
    }

    private CellProcessor[] cellProcessors;
    private String[] csvHeaders;
    private int numOfcols;
    private String typeIdentifierString;
    private String[] beanMappings;
    private String exportCSVFileName;
    private String[] ormMappings;



    public CellProcessor[] getCellProcessors() {
        return cellProcessors;
    }

    public String[] getCsvHeaders() {
        return csvHeaders;
    }

    public int getNumOfcols() {
        return numOfcols;
    }

    public void setNumOfcols(int numOfcols) {
        this.numOfcols = numOfcols;
    }

    public String getTypeIdentifierString() {
        return typeIdentifierString;
    }

    public void setTypeIdentifierString(String typeIdentifierString) {
        this.typeIdentifierString = typeIdentifierString;
    }

    public void setCellProcessors(CellProcessor[] cellProcessors) {
        this.cellProcessors = cellProcessors;
    }

    public String[] getBeanMappings() {
        return beanMappings;
    }

    public void setBeanMappings(String[] beanMappings) {
        this.beanMappings = beanMappings;
    }

    public String getExportCSVFileName()  {
        String date = DateUtils.getDateInFormat(new Date(), DateUtils.DISPLAY_DT_FMT_WITH_TIME_SS);
        return exportCSVFileName.replace("DATE", date);
    }

    public void setExportCSVFileName(String exportCSVFileName) {
        this.exportCSVFileName = exportCSVFileName;
    }

    public String[] getOrmMappings() {
        return ormMappings;
    }

    public void setOrmMappings(String[] ormMappings) {
        this.ormMappings = ormMappings;
    }
}
