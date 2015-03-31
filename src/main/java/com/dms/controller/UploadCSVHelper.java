/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.dto.UploadCSVMetadata;
import com.dms.sysenum.UploadCSVType;
import com.dms.utils.DefaultString;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;

import javax.annotation.PostConstruct;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.dms.sysenum.UploadCSVType.*;
import static com.dms.utils.DMSConstants.*;

/**
 * Created by VamsiKrishna on 31/3/15.
 */
@Component
public class UploadCSVHelper {

    Map<UploadCSVType, UploadCSVMetadata> metadataMap = new HashMap<UploadCSVType, UploadCSVMetadata>(4);

    @PostConstruct
    private void init(){
        UploadCSVMetadata vlife_mk1 = new UploadCSVMetadata(VLIFE_MK1_HEADERS, VLIFE_MK1_NUM_OF_COLS, VLIFE_MK1_TYPE_IDFR_STR);
        UploadCSVMetadata vlife_mk2_ty4 =  new UploadCSVMetadata(VLIFE_MK2_TY4_HEADERS, VLIFE_MK2_TY4_NUM_OF_COLS, VLIFE_MK2_TY4_TYPE_IDFR_STR);
        UploadCSVMetadata vlim_mk1 =  new UploadCSVMetadata(VLIM_MK1_HEADERS, VLIM_MK1_NUM_OF_COLS, VLIM_TYPE_IDFR_STR);
        UploadCSVMetadata vlim_mk2_ty3 =  new UploadCSVMetadata(VLIM_MK2_TY3_HEADERS, VLIM_MK2_TY3_NUM_OF_COLS, VLIM_TYPE_IDFR_STR);
        vlife_mk1.setCellProcessors(getCellProcessors(VLIFE_MK1));
        vlife_mk2_ty4.setCellProcessors(getCellProcessors(VLIFE_MK2_TY4));
        vlim_mk1.setCellProcessors(getCellProcessors(VLIM_MK1));
        vlim_mk2_ty3.setCellProcessors(getCellProcessors(VLIM_MK2_TY3));
        metadataMap.put(VLIFE_MK1, vlife_mk1);
        metadataMap.put(VLIFE_MK2_TY4, vlife_mk2_ty4);
        metadataMap.put(VLIM_MK1, vlim_mk1);
        metadataMap.put(VLIM_MK2_TY3, vlim_mk2_ty3);
    }

    public CellProcessor[] getProcessors(UploadCSVType csvType){
        return metadataMap.get(csvType).getCellProcessors();
    }

    public String[] getHeaders(UploadCSVType csvType){
        return metadataMap.get(csvType).getCsvHeaders();
    }

    public UploadCSVMetadata getMetaData(UploadCSVType csvType){
        return metadataMap.get(csvType);
    }

    public UploadCSVType getCSVFileType(File tempFile) {
        UploadCSVType csvType = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            // I want to check only first  ${numOfLinesToScan} lines to get the CSV type.
            int numOfLinesToScan = 10;
            String thisLine = null;
            while ( (thisLine = br.readLine()) !=null){
                if(thisLine!=null ){
                    if(thisLine.contains(metadataMap.get(VLIFE_MK1).getTypeIdentifierString()) &&
                            thisLine.split(UPLOAD_CSV_SPLIT).length == metadataMap.get(VLIFE_MK1).getNumOfcols()){
                        csvType = VLIFE_MK1;
                        break;
                    }
                    if(thisLine.contains(metadataMap.get(VLIFE_MK2_TY4).getTypeIdentifierString()) &&
                            thisLine.split(UPLOAD_CSV_SPLIT).length == metadataMap.get(VLIFE_MK1).getNumOfcols()){
                        csvType = VLIFE_MK2_TY4;
                        break;
                    }
                    if(thisLine.contains(metadataMap.get(VLIM_MK1).getTypeIdentifierString())){
                        int numOfColumns = thisLine.split(UPLOAD_CSV_SPLIT).length;
                        if(numOfColumns == metadataMap.get(VLIM_MK1).getNumOfcols()){
                            csvType = VLIM_MK1;
                            break;
                        } else if (numOfColumns == metadataMap.get(VLIM_MK2_TY3).getNumOfcols()){
                            csvType = VLIM_MK2_TY3;
                            break;
                        }
                    }
                }
                if(numOfLinesToScan-- == 0 ){
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvType;
    }


    private CellProcessor[] getCellProcessors(UploadCSVType csvType){
        CellProcessor[] cellProcessors = null;
        String[] headers = null;
        switch (csvType) {
            case VLIFE_MK1:
                // 2013-10-25T08:49:53,1,0x40,0x0,64,999990016,103.02,-7.32E-08,23.4,0,0,0,0,0,0,0,0
                // "time","dataType","status","mode", "readingNumber", "insulationResistance", "limVoltage", "limCurrent", "temperature",
                // "lineVoltage", "lineVoltage2", "lineCurrent", "limResistance", "vLifeMK1Column1", "vLifeMK1Column2", "vLifeMK1Column3", "vLifeMK1Column4"
                cellProcessors = new CellProcessor[] {
                        new DefaultString(), // time
                        new Optional(new ParseInt()), // dataType
                        new Optional(), // status
                        new Optional(), // mode
                        new Optional(new ParseLong()), // reading No
                        new Optional(new ParseDouble()), // IR -> insulationResistance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // LIMVoltage
                        new Optional(new ConvertNullTo("-")), // LIMCurrent
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // Temperature
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineVoltage
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineVoltage2
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineCurrent
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // LimResistance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // col_n
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // col_o
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // col_p
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // col_q

                };
                break;
            case VLIFE_MK2_TY4:
                //2015-02-17T21:18:33,1,4,0x0,62990104,249.371689,1,98.593529,92.734093
                // "time" , "vNetAddress" , "dataType" , "status" , "insulationResistance" , "lineVoltage" ,
                // "vLifeMode" ,"vLifeParam" , "vLifeVoltage"
                cellProcessors = new CellProcessor[] {
                        new DefaultString(), //time
                        new DefaultString(), // vNetAddress
                        new Optional(new ParseInt()), // dataType
                        new Optional(), // status
                        new Optional(new ParseDouble()), // insulationResistance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineVoltage
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // VlifeMode
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // VlifeParam
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())) // VlifeVoltage
                };
                break;
            case VLIM_MK1:
                cellProcessors = new CellProcessor[] {
                        new DefaultString(), // time
                        new DefaultString(), // vNetAddress
                        new Optional(new ParseInt()), // dataType
                        new Optional(), // status
                        new Optional(new ParseDouble()), // l1l2Ratio
                        new Optional(new ParseDouble()), // insulationResistance
                        new Optional(new ConvertNullTo("-")), // insulationCapacitance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // downstream_insulation_resistance
                        new Optional(new ConvertNullTo("-")), // downstream_insulation_capacitance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineVoltage
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineCurrent
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineFrequency
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())) //  linePhase
                };
                break;
            case VLIM_MK2_TY3:
                cellProcessors = new CellProcessor[] {
                        new DefaultString(), // time
                        new DefaultString(), // vNetAddress
                        new Optional(new ParseInt()), // dataType
                        new Optional(), // status
                        new Optional(new ParseDouble()), // l1l2Ratio
                        new Optional(new ParseDouble()), // insulationResistance
                        new Optional(new ConvertNullTo("-")), // insulationCapacitance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // downstream_insulation_resistance
                        new Optional(new ConvertNullTo("-")), // downstream_insulation_capacitance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineVoltage
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineCurrent
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineFrequency
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), //  linePhase
                        new Optional(new ConvertNullTo("")), // Field -1 for Ty-3
                        new Optional(new ConvertNullTo("")), // Field -2 for Ty-3
                        new Optional(new ConvertNullTo("")), // Field -3 for Ty-3
                };
                break;
        }

        return cellProcessors;

    }
}
