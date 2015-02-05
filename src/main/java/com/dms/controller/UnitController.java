/*
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.dms.model.NetworkUnit;
import com.dms.model.ProductData;
import com.dms.model.UnitConnectionConfig;
import com.dms.repository.NetworkUnitRepository;
import com.dms.repository.ProductRepository;
import com.dms.utils.DefaultString;
import com.dms.utils.EnumHelper;
import com.dms.utils.JsonBuilder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * Endpoints written to perform following functionality:
 * 1. Upload CSV file.
 * 2. Create Menu for list of CSV uploaded.
 * 3. Delete CSV file.
 * 4. 
 * @author vamsikrishna_a
 *
 */
@Controller
@RequestMapping(value="/unit")
public class UnitController extends BaseController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UnitController.class);

    private Random random = new Random(100000);
    private static final String VAR_PROJECT_ID = "projectId";
    private static final String VAR_UNIT_NO = "unitSerialNo";
    ObjectMapper mapper = new ObjectMapper();

    @Resource
    private ProductRepository productRepository;

    @Resource
    private NetworkUnitRepository unitRepository;

    @Resource
    private JsonBuilder jsonBuilder;

    /**
     * Go to file selection page where you can select and upload data file.
     * @return
     */
    @RequestMapping(value="/data/view", method = RequestMethod.POST)
    public ModelAndView renderProductUploadView(){
        ModelAndView mav = new ModelAndView("fileselection");
        return mav;
    }

    /**
     * Upload csv data file for a company/unit.
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/data/upload",
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
        ModelAndView mav= new ModelAndView("fileselection");
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
            productRepository.saveProducts(productData, formFields.get(VAR_PROJECT_ID));
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
        }

        mav.addObject("status", statusMessage);
        mav.addObject("flag", "green");
        mav.addObject("file", uploadedFileName );
        return mav;
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
        CSVReader reader;
        CsvBeanReader csvBeanReader = null;
        String tempFileName = "temp_dms_"+ random.nextInt() +".csv";
        File tempFile =  new File(FileUtils.getTempDirectoryPath() + File.separator+tempFileName);
        FileUtils.touch(tempFile);
        try {
            if (fileName.endsWith(".csv")) {
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                final CellProcessor[] processors = new CellProcessor[] {
                        new NotNull(), // time
                        new DefaultString(), // vNetAddress
                        new ParseInt(), // type
                        new NotNull(), // status
                        new Optional(new ParseDouble()), // limImbalance
                        new Optional(new ParseDouble()), // limResistance
                        new Optional(new ConvertNullTo("-")), // limCapacitance
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // limResistanceCm
                        new Optional(new ConvertNullTo("-")), // limCapacitanceCm
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineVoltage
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineCurrent
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())), // lineFrequency
                        new Optional(new ConvertNullTo(0.0, new ParseDouble())) //  linePhase
                };
                csvBeanReader = new CsvBeanReader(new FileReader(tempFile), CsvPreference.STANDARD_PREFERENCE);
                final String[] header = csvBeanReader.getHeader(true);
                ProductData product;
                while( (product = csvBeanReader.read(ProductData.class, header, processors)) !=null){
                    if(product !=null){
                        productDataList.add(product);
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
            if(name !=null && name.equalsIgnoreCase(VAR_PROJECT_ID)){
                formFields.put(VAR_PROJECT_ID, item.getString());
            }
            if(name !=null && name.equalsIgnoreCase(VAR_UNIT_NO)){
                formFields.put(VAR_UNIT_NO, item.getString());
            }
            if(name !=null && name.equalsIgnoreCase("path")){
                formFields.put("filePath", item.getString());
            }

        }
    }

    /**
     * List all network units present in DMS database
     * @return
     */
    @RequestMapping(
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public ModelAndView getAllUnits() throws IOException {
        ModelAndView mav = new ModelAndView("networks");
        mav.addObject("networkUnits", mapper.writeValueAsString(unitRepository.getAll()));
        //mav.addObject("companies", mapper.writeValueAsString(productRepository.getAllCompanies()));
        //mav.addObject("products", mapper.writeValueAsString(productRepository.getAllAvailableProducts()));
        return mav;
    }

    /**
     * Add a network unit to DMS db.
     * @return
     */
    @RequestMapping(value = "/{projectId}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method= RequestMethod.PUT)
    public String addNetworkUnit(@PathVariable("projectId")String projectId,
                                                  @RequestParam("companyName")String companyName,
                                                  @RequestParam("platform")String platform,
                                                  @RequestParam("controlSystem")String controlSystem,
                                                  @RequestParam("channel")String channel,
                                                  @RequestParam("ipAddress")String ipAddress,
                                                  @RequestParam("unitSerialNo")String unitSerialNo,
                                                  @RequestParam("restUrl")String url,
                                                  @RequestParam("headers")String headers,
                                                  @RequestParam("method")String method) throws Exception {
        String status = "success";
        // plz write validations here for each input field.
        if(null == projectId || null == companyName || null == platform || null == controlSystem
                || null == channel || null == ipAddress || null == unitSerialNo || null ==url
                || null == headers || null == method)
            throw new Exception("All fields are mandatory.");
        if(projectId.trim().length()==0 ||
                companyName.trim().length()==0 ||
                platform.trim().length()==0 ||
                controlSystem.trim().length()==0 ||
                channel.trim().length()==0 ||
                ipAddress.trim().length()==0 ||
                unitSerialNo.trim().length()==0 ){
            throw new Exception("Empty spaces are not allowed.");
        }
        if( unitRepository.getUnitIfo(projectId)!=null){
            throw new Exception("A unit is already configured with same projectId:"+ projectId);
        }
        NetworkUnit networkUnit = new NetworkUnit();
        networkUnit.setCompanyName(companyName);
        networkUnit.setProjectId(projectId);
        networkUnit.setChannel(channel);
        networkUnit.setAlive(false);
        networkUnit.setControlSystem(controlSystem);
        networkUnit.setIpAddress(ipAddress);
        networkUnit.setUnitSerialNo(unitSerialNo);
        UnitConnectionConfig config = new UnitConnectionConfig();
        config.setUrl(url);
        config.setHeaders(headers);
        config.setMethod(EnumHelper.load(RequestMethod.class, method));
        networkUnit.setUnitConnectionConfig(config);
        unitRepository.create(networkUnit);
        return status;
    }

    /**
     * Update a network unit on DMS db.
     * @return
     */
    @RequestMapping(value = "/{projectId}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method= RequestMethod.POST)
    public String updateNetworkUnit(@PathVariable("projectId")String projectId,
                                 @RequestParam("companyName")String companyName,
                                 @RequestParam("platform")String platform,
                                 @RequestParam("controlSystem")String controlSystem,
                                 @RequestParam("channel")String channel,
                                 @RequestParam("ipAddress")String ipAddress,
                                 @RequestParam("unitSerialNo")String unitSerialNo,
                                 @RequestParam("restUrl")String url,
                                 @RequestParam("headers")String headers,
                                 @RequestParam("method")String method) throws Exception {
        String status = "success";
        // plz write validations here for each input field.
        if(null == projectId || null == companyName || null == platform || null == controlSystem
                || null == channel || null == ipAddress || null == unitSerialNo || null ==url
                || null == headers || null == method)
            throw new Exception("All fields are mandatory.");
        if(projectId.trim().length()==0 ||
                companyName.trim().length()==0 ||
                platform.trim().length()==0 ||
                controlSystem.trim().length()==0 ||
                channel.trim().length()==0 ||
                ipAddress.trim().length()==0 ||
                unitSerialNo.trim().length()==0 ){
            throw new Exception("Empty spaces are not allowed.");
        }

        NetworkUnit networkUnit = unitRepository.getUnitIfo(projectId);
        if(null == networkUnit){
            throw new Exception("No Unit configured with the given projectId:" + projectId);
        }
        networkUnit.setCompanyName(companyName);
        networkUnit.setChannel(channel);
        networkUnit.setAlive(false);
        networkUnit.setControlSystem(controlSystem);
        networkUnit.setIpAddress(ipAddress);
        networkUnit.setUnitSerialNo(unitSerialNo);
        UnitConnectionConfig config = new UnitConnectionConfig();
        config.setUrl(url);
        config.setHeaders(headers);
        config.setMethod(EnumHelper.load(RequestMethod.class, method));
        networkUnit.setUnitConnectionConfig(config);
        unitRepository.update(networkUnit);
        return status;
    }

    /**
     * Show Network Unit Image Data based on projectId
     * @return
     * @throws IOException
     */
    @RequestMapping(value= "/data/{projectId}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public ModelAndView getDataForProductNUnit(@PathVariable("projectId") String projectId) throws IOException {
        ModelAndView mav = new ModelAndView("network_unit");
        List<ProductData> productData = productRepository.getDataForProduct(projectId);
        if(productData!=null && productData.size()>0){
            mav.addObject("companyName", productData.get(0).getNetworkUnit().getCompanyName());
            mav.addObject("unitSerialNo", productData.get(0).getNetworkUnit().getUnitSerialNo());
        }
        mav.addObject("network_unit_data", mapper.writeValueAsString(productData));
        return mav;
    }



}
