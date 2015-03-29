/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.exception.NoResultsException;
import com.dms.model.ProductData;
import com.dms.repository.NetworkUnitRepository;
import com.dms.repository.ProductRepository;
import com.dms.utils.DMSConstants;
import com.dms.utils.DateUtils;
import com.dms.utils.DefaultString;
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
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by VamsiKrishna on 14/3/15.
 */
@Controller
@RequestMapping(value="/unit/data")
public class ProductDataController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    private Random random = new Random(100000);
    private static final String VAR_NETWORK_UNIT = "networkUnitSelect";
    private static final String VAR_FILE_PATH = "path";
    ObjectMapper mapper = new ObjectMapper();

    @Resource
    private ProductRepository productRepository;

    @Resource
    private NetworkUnitRepository unitRepository;



    @RequestMapping(value="/import", method = RequestMethod.GET)
    public ModelAndView renderProductUploadView(){
        ModelAndView mav = new ModelAndView("upload_unit_data");
        try {
            mav.addObject("networkUnits", mapper.writeValueAsString(unitRepository.getAll()));
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
    public ModelAndView uploadProductDataFromCSV(HttpServletRequest request) throws Exception {
        String statusMessage = "File uploaded Successfully.";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart){
            System.out.println("Not Multipart!!!");
            throw new Exception("Required multipart form input");
        }
        ModelAndView mav= new ModelAndView("upload_unit_data");
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 1 MB is the file size limit.
        factory.setSizeThreshold(1048576);
        // Configure a repository (to ensure a secure temp location is used)
        File repository = FileUtils.getTempDirectory();
        factory.setRepository(repository);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1048576*2);
        String uploadedFileName = null;
        try {
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            Map<String, Object> map = createProduct(items);
            List<ProductData> productData = (List<ProductData>) map.get("productData");

            Map<String, String> formFields = (Map<String, String>) map.get("formFields");
            uploadedFileName = formFields.get("filePath");
            productRepository.saveProducts(productData, Long.parseLong(formFields.get(VAR_NETWORK_UNIT)));
        } catch (FileUploadException ex) {
            logger.error("Error while uploading the CSV", ex);
            statusMessage = "An error occurred while uploading CSV";
            mav.addObject("status", statusMessage);
            mav.addObject("flag", "red");
            return mav;
        } catch (Exception e) {
            logger.error("Error while uploading the CSV", e);
            statusMessage = "An error occurred while uploading CSV";
            mav.addObject("status", statusMessage);
            mav.addObject("flag", "red");
            return mav;
        } finally {
            mav.addObject("networkUnits", mapper.writeValueAsString(unitRepository.getAll()));
        }

        mav.addObject("status", statusMessage);
        mav.addObject("flag", "green");
        mav.addObject("file", uploadedFileName );
        return mav;
    }


    @RequestMapping(value="/export", method = RequestMethod.GET)
    public ModelAndView renderProductDownloadView(){
        ModelAndView mav = new ModelAndView("download_unit_data");
        try {
            mav.addObject("networkUnits", mapper.writeValueAsString(unitRepository.getAll()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void exportProductData(@RequestParam("networkUnitSelect") Long projectInfoId,
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
            products = productRepository.getUnitData(projectInfoId, fromDt, toDt);
            String unitSerialNo =  unitRepository.getUnitIfo(projectInfoId).getUnitSerialNo();
            String tempFileName = DMSConstants.EXPORT_FILE_PREFIX + unitSerialNo+"_"+DateUtils.getDisplayDate(new Date())+DMSConstants.SUFFIX_CSV;
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    tempFileName);
            response.setHeader(headerKey, headerValue);
            CellProcessor[] processors = getProcessors();
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

    private Map<String, Object> createProduct(List<FileItem> items) throws Exception {
        // Currently we are expecting only one product.
        Map<String, Object> stringObjectMap = new HashMap<String, Object>(2);
        List<ProductData> productDataList = null;
        Map<String, String> formFields = new HashMap<String, String>(2);
        String companyName = null;

        Iterator<FileItem> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            FileItem item = itemIterator.next();

            if (item.isFormField()) {
                processFormField(item, formFields);
            } else {
                productDataList =  processUploadedFile(item);
            }
        }
        stringObjectMap.put("productData", productDataList);
        stringObjectMap.put("formFields", formFields);
        return stringObjectMap;
    }

    private List<ProductData> processUploadedFile(FileItem file) throws Exception {
        if (file.isFormField()) {
            return null;
        }
        List<ProductData> productDataList = new ArrayList<ProductData>(50);

        String fileName = file.getName();
        CsvBeanReader csvBeanReader = null;
        String tempFileName = "temp_dms_"+ random.nextInt() +".csv";
        File tempFile =  new File(FileUtils.getTempDirectoryPath() + File.separator+tempFileName);
        FileUtils.touch(tempFile);
        try {
            if (fileName.endsWith(".csv")) {
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                final CellProcessor[] processors = getProcessors();
                csvBeanReader = new CsvBeanReader(new FileReader(tempFile), CsvPreference.STANDARD_PREFERENCE);
                csvBeanReader.getHeader(false);
                final String[] header = csvBeanReader.getHeader(false);
                ProductData product;
                while( (product = csvBeanReader.read(ProductData.class, header, processors)) !=null){
                    String row = csvBeanReader.getUntokenizedRow();
                    if(row!=null && !row.trim().startsWith(",,,,,,,,,,,,") ){
                        if(product !=null){
                            productDataList.add(product);
                        }
                    } else {
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
        return productDataList;
    }

    private void processFormField(FileItem item, Map<String, String> formFields) {
        if (item.isFormField()) {
            String name = item.getFieldName();
            if(name !=null && name.equalsIgnoreCase(VAR_NETWORK_UNIT)){
                formFields.put(VAR_NETWORK_UNIT, item.getString());
            }
            if(name !=null && name.equalsIgnoreCase(VAR_FILE_PATH)){
                formFields.put("filePath", item.getString());
            }

        }
    }

    private CellProcessor[] getProcessors(){
        // Currently This supports cell processors for V-LIM mk1 only
        // TODO : Extend this to support new type of Network Units.
        return new CellProcessor[] {
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
    }


}
