/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.controller;

import com.dms.model.ProductData;
import com.dms.repository.ProductRepository;
import com.dms.repository.UserRepository;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Endpoints written to perform following functionalities:
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
    
    @Resource
    private ProductRepository productRepository;
    
    @RequestMapping(value="/upload",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
            method= RequestMethod.POST)
    public String uploadProductDateFromCSV(HttpServletRequest request){
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart){
            System.out.println("Not Multipart!!!");
            return null;
        }
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1048576);
        // Confgure a repository (to ensure a secure temp location is used)
        File repository = new File("java.io.tmpdir");
        factory.setRepository(repository);
        
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);        
        upload.setSizeMax(1048576*2);
        try {
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            
        } catch (FileUploadException ex) {
            Logger.getLogger(DataManageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
