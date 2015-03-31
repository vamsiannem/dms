/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.dto;

import org.supercsv.cellprocessor.ift.CellProcessor;

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
}
