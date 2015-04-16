/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.dto.UploadCSVMetadata;
import com.dms.exception.InvalidRequest;
import com.dms.exception.NoResultsException;
import com.dms.model.DataCoreMeasurement;
import com.dms.repository.DataCoreMeasurementRepository;
import com.dms.repository.ProjectRepository;
import com.dms.sysenum.UploadCSVType;
import com.dms.utils.DMSConstants;
import com.dms.utils.DateUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by VamsiKrishna on 14/3/15.
 */
@Controller
@RequestMapping(value="/project/data")
public class ProjectDataController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private Random random = new Random(100000);
    private static final String VAR_NETWORK_UNIT = "networkUnitSelect";
    private static final String VAR_FILE_NAME = "fileName";
    private static ServletFileUpload servletFileUpload = null;
    ObjectMapper mapper = new ObjectMapper();

    @Resource
    private DataCoreMeasurementRepository dataCoreMeasurementRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UploadCSVHelper uploadCSVHelper;


    @PostConstruct
    public void init(){
        initFileUploadServlet();
    }

    @RequestMapping(value="/import", method = RequestMethod.GET)
    public ModelAndView renderProjectUploadView(){
        ModelAndView mav = new ModelAndView("upload_project_data");
        try {
            mav.addObject("projects", mapper.writeValueAsString(projectRepository.getAll()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * Upload csv data file for a company/unit.
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/import",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
            method= RequestMethod.POST)
    public ModelAndView uploadProjectDataFromCSV(HttpServletRequest request) {
        String statusMessage = "File uploaded Successfully.";
        ModelAndView mav= new ModelAndView("upload_project_data");
        String uploadedFileName = null;
        try {
            // Parse the request
            List<FileItem> items = servletFileUpload.parseRequest(request);
            validateRequest(items, request);
            Map<String, Object> map = createProjectData(items);
            List<DataCoreMeasurement> dataCoreMeasurement = (List<DataCoreMeasurement>) map.get("dataCoreMeasurement");
            Map<String, String> formFields = (Map<String, String>) map.get("formFields");
            uploadedFileName = formFields.get("filePath");
            dataCoreMeasurementRepository.saveDataMeasurements(dataCoreMeasurement, Long.parseLong(formFields.get(VAR_NETWORK_UNIT)));
        } catch (FileUploadException ex) {
            logger.error("Error while uploading the CSV", ex);
            statusMessage = "An error occurred while uploading CSV.";
            mav.addObject("status", statusMessage);
            mav.addObject("flag", "red");
            return mav;
        } catch (InvalidRequest e) {
            logger.error("Error while uploading the CSV", e);
            statusMessage = e.getMessage();
            mav.addObject("status", statusMessage);
            mav.addObject("flag", "red");
            return mav;
        } catch (Exception e) {
            logger.error("Error while uploading the CSV", e);
            statusMessage = "Unknown error occurred while uploading CSV, Contact System Admin.";
            mav.addObject("status", statusMessage);
            mav.addObject("flag", "red");
            return mav;
        } finally {
            try {
                mav.addObject("projects", mapper.writeValueAsString(projectRepository.getAll()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mav.addObject("status", statusMessage);
        mav.addObject("flag", "green");
        mav.addObject("file", uploadedFileName );
        return mav;
    }

    @RequestMapping(value="/export", method = RequestMethod.GET)
    public ModelAndView renderProjectDownloadView(){
        ModelAndView mav = new ModelAndView("download_unit_data");
        try {
            mav.addObject("projects", mapper.writeValueAsString(projectRepository.getAll()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void exportProjectData(@RequestParam("networkUnitSelect") Long projectInfoId,
                                  @RequestParam("from") String fromDate,
                                  @RequestParam("to") String toDate,
                                  HttpServletResponse response,
                                  HttpServletRequest request) throws NoResultsException {
        response.setHeader("Content-type", "application/vnd.ms-excel");
        response.setContentType("text/csv");
        String fromDt = null;
        String toDt = null;
        try {
            fromDt = DateUtils.getMysqlDateStr(fromDate, DateUtils.DISPLAY_DT_FMT);
            toDt =DateUtils.getMysqlDateStr(toDate, DateUtils.DISPLAY_DT_FMT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Object[]> products;
        if( fromDt!=null && toDt!=null){
            products = dataCoreMeasurementRepository.getDataMeasurements(projectInfoId, fromDt, toDt);
            String unitSerialNo =  projectRepository.get(projectInfoId).getUnitSerialNo();
            String tempFileName = DMSConstants.EXPORT_FILE_PREFIX + unitSerialNo+"_"+DateUtils.getDisplayDate(new Date())+DMSConstants.SUFFIX_CSV;
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    tempFileName);
            response.setHeader(headerKey, headerValue);
            // TODO : Change the Upload CSV Type to take multiple values.
            CellProcessor[] processors = uploadCSVHelper.getProcessors(UploadCSVType.VLIM_MK1);
            ICsvListWriter writer = null;
            try {
                //String absFilePath = FileUtils.getTempDirectory()+File.separator+ tempFileName;
                //writer = new CsvListWriter(new FileWriter(absFilePath), CsvPreference.STANDARD_PREFERENCE);
                writer = new CsvListWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
                if(products.size()==0){
                    writer.write("No Results Found!!!");
                } else {
                    writer.writeHeader(DMSConstants.HEADERS_CSV);
                    for(Object[] var: products){
                        writer.write(Arrays.asList(var), processors);
                    }
                }

            } catch (IOException e) {
                logger.error("Exception while writing csv file: \n"+ e);
            } finally {
                if( writer != null ) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        // None
                        logger.error("Exception while closing the csv writer");
                    }
                }
            }
        }
    }

    private void validateRequest(List<FileItem> items, HttpServletRequest request) throws InvalidRequest {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String errorMsg = null;
        if(!isMultipart){
            logger.error("Request is expected to be of type:" + MediaType.MULTIPART_FORM_DATA_VALUE);
            errorMsg = "Required multi-part form input";
        }
        String uploadedFileName = "";
        Iterator<FileItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            FileItem item = itemIterator.next();

            if (!item.isFormField()) {
                String name = item.getFieldName();
                if(name !=null && name.equalsIgnoreCase("myfile")){
                    uploadedFileName = item.getName();
                }
            }
        }
        if(uploadedFileName.toLowerCase().startsWith(DMSConstants.UPLOAD_CSV_NAME_PREFIX.toLowerCase())
                && uploadedFileName.endsWith("csv")) {
            String[] fileNameSplit = uploadedFileName.split("_");
            if (fileNameSplit.length==3) {
                try {
                    // TODO : validate the Unit Serial No
                    Long.parseLong(fileNameSplit[1]);
                    DateUtils.getDate(fileNameSplit[2], DateUtils.MYSQL_DT_FMT);
                } catch (ParseException pe){
                    errorMsg = "File Name format should comply to: <vlim-datalog>_SerialNumber_<yyyy-mm-dd>.csv";
                } catch (NumberFormatException nfe) {
                    errorMsg = "File Name format should comply to: <vlim-datalog>_SerialNumber_<yyyy-mm-dd>.csv";
                }
            } else {
                errorMsg = "File Name format should comply to: <vlim-datalog>_SerialNumber_<yyyy-mm-dd>.csv";
            }
        } else {
            errorMsg = "File Name format should comply to: <vlim-datalog>_SerialNumber_<yyyy-mm-dd>.csv";
        }
        if (errorMsg != null){
            throw new InvalidRequest(errorMsg);
        }
    }

    private Map<String, Object> createProjectData(List<FileItem> items) throws Exception {
        // Currently we are expecting only one product.
        Map<String, Object> stringObjectMap = new HashMap<String, Object>(2);
        List<DataCoreMeasurement> dataCoreMeasurementList = null;
        Map<String, String> formFields = new HashMap<String, String>(2);
        String companyName = null;

        Iterator<FileItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            FileItem item = itemIterator.next();

            if (item.isFormField()) {
                processFormField(item, formFields);
            } else {
                dataCoreMeasurementList =  processUploadedFile(item);
            }
        }
        stringObjectMap.put("dataCoreMeasurement", dataCoreMeasurementList);
        stringObjectMap.put("formFields", formFields);
        return stringObjectMap;
    }

    private List<DataCoreMeasurement> processUploadedFile(FileItem file) throws Exception {
        if (file.isFormField()) {
            return null;
        }
        List<DataCoreMeasurement> dataCoreMeasurementList = new ArrayList<DataCoreMeasurement>(50);

        String fileName = file.getName();
        CsvBeanReader csvBeanReader = null;
        String tempFileName = "temp_dms_"+ random.nextInt() +".csv";
        File tempFile =  new File(FileUtils.getTempDirectoryPath() + File.separator+tempFileName);
        FileUtils.touch(tempFile);
        try {
            if (fileName.endsWith(".csv")) {
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                UploadCSVType type = uploadCSVHelper.getCSVFileType(tempFile);
                UploadCSVMetadata metadata = uploadCSVHelper.getMetaData(type);
                if(type == null){
                    throw new InvalidRequest("Unable to find CSV Type of the Input File. \n " +
                            "Allowed CSV Types:"+ UploadCSVType.values() );
                }
                final CellProcessor[] processors = metadata.getCellProcessors();
                csvBeanReader = new CsvBeanReader(new FileReader(tempFile), CsvPreference.STANDARD_PREFERENCE);
                final String[] header = metadata.getCsvHeaders();
                DataCoreMeasurement product = null;

                while( true ){
                    try {
                        product = csvBeanReader.read(DataCoreMeasurement.class, header, processors);
                    } catch (SuperCsvCellProcessorException e){
                        logger.error(e.getMessage());
                        logger.info("The above error is expected because of " +
                                "the headers spanning across multiple lines");
                        continue;
                    }
                    if ( product==null ){
                        break;
                    }
                    String row = csvBeanReader.getUntokenizedRow();
                    if(row!=null && !row.trim().contains(",,,,,,,") &&
                            !row.trim().contains(metadata.getTypeIdentifierString()) &&
                            product !=null){
                            dataCoreMeasurementList.add(product);
                    } else if(row == null ||  (row!=null && row.startsWith(",,,,,,")) ){
                        break;
                    }
                }
            } else {
                throw new Exception("Unknown File Format uploaded");
            }
        } finally {
            csvBeanReader.close();
            tempFile.delete();
        }
        return dataCoreMeasurementList;
    }

    private void processFormField(FileItem item, Map<String, String> formFields) {
        if (item.isFormField()) {
            String name = item.getFieldName();
            if(name !=null && name.equalsIgnoreCase(VAR_NETWORK_UNIT)){
                formFields.put(VAR_NETWORK_UNIT, item.getString());
            }
            if(name !=null && name.equalsIgnoreCase(VAR_FILE_NAME)){
                formFields.put("filePath", item.getString());
            }

        }
    }


    private void initFileUploadServlet(){
        if(servletFileUpload == null) {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 1 MB is the file size limit.
            factory.setSizeThreshold(1048576);
            // Configure a repository (to ensure a secure temp location is used)
            File repository = FileUtils.getTempDirectory();
            factory.setRepository(repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1048576*2);
            servletFileUpload = upload;
        }
    }


}
