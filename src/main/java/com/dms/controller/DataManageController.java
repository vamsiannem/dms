/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.dms.model.ProductData;
import com.dms.repository.ProductRepository;
import com.dms.utils.DefaultString;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
@RequestMapping(value="/product")
public class DataManageController extends BaseController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DataManageController.class);

    private Random random = new Random(100000);
    private static final String VAR_COMP_NAME = "companyName";
    private static final String VAR_UNIT_NO = "unitSerialNo";

    @Resource
    private ProductRepository productRepository;

    @RequestMapping(value="/upload",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
            method= RequestMethod.POST)
    public @ModelAttribute("status") String uploadProductDateFromCSV(HttpServletRequest request){
        String statusMessage = "File uploaded Successfully.";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart){
            System.out.println("Not Multipart!!!");
            return null;
        }
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
        try {
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            Map<String, Object> map = createProduct(items);
            List<ProductData> productData = (List<ProductData>) map.get("productData");
            Map<String, String> formFields = (Map<String, String>) map.get("formFields");
            productRepository.saveProducts(productData, formFields.get(VAR_COMP_NAME), formFields.get(VAR_UNIT_NO) );
        } catch (FileUploadException ex) {
            logger.error("Error while uploading the CSV", ex);
            statusMessage = "An error occurred while uploading CSV";
        } catch (Exception e) {
            logger.error("Error while uploading the CSV", e);
            statusMessage = "An error occurred while uploading CSV";
        }
        return statusMessage;
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

        if (fileName.endsWith(".csv")) {

            String tempFileName = "temp_dms_"+ random.nextInt() +".csv";
            File tempFile =  new File(FileUtils.getTempDirectoryPath() + File.separator+tempFileName);
            FileUtils.touch(tempFile);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);

            CsvBeanReader csvBeanReader = null;
            try {
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
            } finally {
                csvBeanReader.close();
            }

        } else {
            throw new Exception("Unknown File Format uploaded");
        }
        return productDataList;
    }

    private void processFormField(FileItem item, Map<String, String> formFields) {
        if (item.isFormField()) {
            String name = item.getFieldName();
            if(name !=null && name.equalsIgnoreCase(VAR_COMP_NAME)){
                formFields.put(VAR_COMP_NAME, item.getString());
            }
            if(name !=null && name.equalsIgnoreCase(VAR_UNIT_NO)){
                formFields.put(VAR_UNIT_NO, item.getString());
            }

        }
    }

    @RequestMapping(
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public List<String> getAvailableProducts(){
        return productRepository.getAllAvailableProducts();
    }

    @RequestMapping(value= "/data/{companyName}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public List<ProductData> getDataForProduct(@PathVariable("companyName") String companyName){
        return productRepository.getDataForProduct(companyName);
    }






}
