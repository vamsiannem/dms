/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.controller;

import com.dms.model.ProjectInfo;
import com.dms.model.ProductData;
import com.dms.model.UnitConnectionConfig;
import com.dms.repository.ClientRepository;
import com.dms.repository.NetworkUnitRepository;
import com.dms.repository.ProductRepository;
import com.dms.utils.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
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
@SessionAttributes("user")
@RequestMapping(value="/unit")
public class UnitController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);
    ObjectMapper mapper = new ObjectMapper();

    @Resource
    private ProductRepository productRepository;

    @Resource
    private NetworkUnitRepository unitRepository;

    @Resource
    private ClientRepository clientRepository;

    @Resource
    private JsonBuilder jsonBuilder;

    /**
     * List all network units present in DMS database
     * @return
     */
    @RequestMapping(
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public ModelAndView getAllUnits(@RequestParam(required = false, defaultValue = "projectInfoId", value = "orderBy") String orderBy) throws IOException {
        Collection<ProjectInfo> units = null;
        if(orderBy!=null && !orderBy.equals("")){
            units = unitRepository.getAllUnitsInOrder(orderBy);
        } else {
            units = unitRepository.getAll();
        }
        ModelAndView mav = new ModelAndView("networks");
        mav.addObject("networkUnits", mapper.writeValueAsString(units));

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
    public ModelAndView addNetworkUnit(@PathVariable("projectId") String projectId,
                                                  @RequestParam("companyName")String companyName,
                                                  @RequestParam("platform")String platform,
                                                  @RequestParam("controlSystem")String controlSystem,
                                                  @RequestParam("channel")String channel,
                                                  @RequestParam("ipAddress")String ipAddress,
                                                  @RequestParam("installationDate")String installationDate,
                                                  @RequestParam("unitSerialNo")String unitSerialNo,
                                                  HttpSession session
                                                  /*@RequestParam("restUrl")String url,
                                                  @RequestParam("headers") String headers,
                                                  @RequestParam("method")String method*/) throws Exception {
        String addStatus = "success";
        // plz write validations here for each input field.
        if(null == projectId || null == companyName || null == platform || null == controlSystem
                || null == channel || null == ipAddress || null == unitSerialNo || null == installationDate
                /*|| null == headers || null == method*/)
            throw new Exception("All fields are mandatory.");
        if(projectId.trim().length()==0 ||
                companyName.trim().length()==0 ||
                platform.trim().length()==0 ||
                controlSystem.trim().length()==0 ||
                channel.trim().length()==0 ||
                ipAddress.trim().length()==0 ||
                unitSerialNo.trim().length()==0 ){
            throw new Exception("Some of the required fields cannot be left blank or empty.");
        }

        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(projectId);
        projectInfo.setClientInfo(clientRepository.getClient(companyName));
        projectInfo.setPlatform(platform);
        projectInfo.setControlSystem(controlSystem);
        projectInfo.setChannel(channel);
        projectInfo.setIpAddress(ipAddress);
        projectInfo.setInstallationDate(DateUtils.getDate(installationDate, DateUtils.DISPLAY_DT_FMT));
        projectInfo.setAlive(false);
        projectInfo.setUnitSerialNo(unitSerialNo);
        projectInfo.setLastModifiedBy((String) session.getAttribute(DMSConstants.SESSION_USER_FULL_NAME));
        projectInfo.setLastModifiedDate(new Date(System.currentTimeMillis()));
        UnitConnectionConfig config = new UnitConnectionConfig();
        config.setUrl(DMSConstants.DUMMY_URL);
        config.setHeaders(DMSConstants.DUMMY_HEADERS);
        config.setMethod(EnumHelper.load(RequestMethod.class, DMSConstants.DUMMY_METHOD));
        config.setBodyParams(DMSConstants.DUMMY_PARAM);
        projectInfo.setUnitConnectionConfig(config);
        ModelAndView mav = new ModelAndView();
        try {
            unitRepository.create(projectInfo);
            mav.addObject("status", addStatus);
        } catch (HibernateException he){
            logger.error(he.getCause().getMessage());
            he.printStackTrace();
            mav.addObject("status", "Failed");
        }
        return mav;
    }

    /**
     * Update a network unit on DMS db.
     * @return
     */
    @RequestMapping(value = "/{projectInfoId}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method= RequestMethod.POST)
    public String updateNetworkUnit(@PathVariable("projectInfoId") Long projectInfoId, @RequestParam("projectId") String projectId,
                                 @RequestParam("companyName")String companyName,
                                 @RequestParam("platform")String platform,
                                 @RequestParam("controlSystem")String controlSystem,
                                 @RequestParam("channel")String channel,
                                 @RequestParam("ipAddress")String ipAddress,
                                 @RequestParam("unitSerialNo")String unitSerialNo,
                                 @RequestParam("restUrl")String url,
                                 @RequestParam("headers") String headers,
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

        ProjectInfo projectInfo = unitRepository.getUnitIfo(projectInfoId);
        if(null == projectInfo){
            throw new Exception("No Unit configured with the given projectInfoId:" + projectInfoId);
        }
        projectInfo.setClientInfo(clientRepository.getClient(companyName));
        projectInfo.setChannel(channel);
        projectInfo.setAlive(false);
        projectInfo.setControlSystem(controlSystem);
        projectInfo.setIpAddress(ipAddress);
        projectInfo.setUnitSerialNo(unitSerialNo);
        UnitConnectionConfig config = new UnitConnectionConfig();
        config.setUrl(url);
        config.setHeaders(headers);
        config.setMethod(EnumHelper.load(RequestMethod.class, method));
        projectInfo.setUnitConnectionConfig(config);
        unitRepository.update(projectInfo);
        return status;
    }

    /**
     * Show Network Unit Image Data based on projectInfoId
     * @return
     * @throws IOException
     */
    @RequestMapping(value= "/data/{projectInfoId}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public ModelAndView getDataForProductNUnit(@PathVariable("projectInfoId") Long projectInfoId) throws IOException {
        ModelAndView mav = new ModelAndView("network_unit");
        List<ProductData> productData = productRepository.getDataForProduct(projectInfoId);
        if(productData!=null && productData.size()>0){
            mav.addObject("companyName", productData.get(0).getProjectInfo().getCompanyName());
            mav.addObject("unitSerialNo", productData.get(0).getProjectInfo().getUnitSerialNo());
        }
        mav.addObject("network_unit_data", mapper.writeValueAsString(productData));
        mav.addObject("network_unit", unitRepository.getUnitIfo(projectInfoId));
        return mav;
    }

    /**
     * Fetch all nodes (vNetAddress) for a Network Unit.
     * @return
     * @throws IOException
     */
    @RequestMapping(value= "/{projectInfoId}/node",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public ModelAndView getAllNodes(@PathVariable("projectInfoId") Long projectInfoId) throws IOException {
        ModelAndView mav = new ModelAndView();
        List<String> nodes = productRepository.getVNetAddress(projectInfoId);
        mav.addObject("nodes", nodes);
        return mav;
    }



}
