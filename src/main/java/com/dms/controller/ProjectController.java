/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.controller;

import com.dms.dto.ProjectDTO;
import com.dms.model.*;
import com.dms.repository.*;
import com.dms.utils.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 *TODO: All the logic needs to be moved to Service layer and make this controller a light weight component.
 * Endpoints written to perform following functionality:
 * 1. Upload CSV file.
 * 2. Create Menu for list of CSV uploaded.
 * 3. Delete CSV file.
 * 4. 
 * @author vamsikrishna_a
 *
 */
@Controller

@RequestMapping(value="/project")
public class ProjectController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    ObjectMapper mapper = new ObjectMapper();

    @Resource
    private DataCoreMeasurementRepository dataCoreMeasurementRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ClientRepository clientRepository;

    @Resource
    private CommonRepository commonRepository;

    @Resource
    private JsonBuilder jsonBuilder;

    /**
     * List all network units present in DMS database
     * @return
     */
    @RequestMapping(
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method= RequestMethod.GET)
    public ModelAndView getAllProjects(@RequestParam(required = false, defaultValue = "projectInfoId", value = "orderBy") String orderBy) throws IOException {
        Collection<ProjectInfo> units = null;
        if(orderBy!=null && !orderBy.equals("")){
            units = projectRepository.getAll(orderBy);
        } else {
            units = projectRepository.getAll();
        }
        ModelAndView mav = new ModelAndView("projects");
        mav.addObject("projects", mapper.writeValueAsString(units));
        mav.addObject("clients", mapper.writeValueAsString(clientRepository.getAll()));
        mav.addObject("availableProducts", mapper.writeValueAsString(productRepository.getAllAvailable()));

        //mav.addObject("companies", mapper.writeValueAsString(productRepository.getAllCompanies()));
        //mav.addObject("products", mapper.writeValueAsString(productRepository.getAllDataMeasurements()));
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
    public ModelAndView createNewProject(@PathVariable("projectId") String projectId,
                                         HttpSession session,
                                         ProjectDTO projectDTO, BindingResult result) throws Exception {
        String addStatus = "New project with Id:"+ projectId+" created successfully";
        // plz write validations here for each input field.

        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(projectId);
        projectInfo.setPlatform(projectDTO.getPlatform());
        projectInfo.setControlSystem(projectDTO.getControlSystem());
        projectInfo.setChannel(projectDTO.getChannel());
        projectInfo.setDescription(projectDTO.getDescription());
        projectInfo.setIpAddress("10.10.10.10");
        projectInfo.setAlive(false);
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
            projectInfo.setClientInfo(clientRepository.getClient(projectDTO.getClientId()));
            ProductInfo productInfo = productRepository.get(projectDTO.getProductId());
            projectInfo.setProductInfo(productInfo);
            ProjectHistoryInfo historyInfo = new ProjectHistoryInfo();
            historyInfo.setInstallationDate(DateUtils.getDate(projectDTO.getInstallationDate(), DateUtils.DISPLAY_DT_FMT));
            historyInfo.setUnitSerialNo(productInfo.getUnitSerialNo());
            historyInfo.setConsignedEngineer(projectDTO.getConsignedEngineer());
            SortedSet<ProjectHistoryInfo> historyInfoList = new TreeSet<ProjectHistoryInfo>();
            historyInfoList.add(historyInfo);
            projectInfo.setProjectHistoryInfoList(historyInfoList);
            projectRepository.create(projectInfo);
            mav.addObject("status", addStatus);
            mav.addObject("flag", "0");
        } catch (HibernateException he){
            logger.error(he.getCause().getMessage());
            he.printStackTrace();
            mav.addObject("status", "An error while creating project:"+ projectId);
            mav.addObject("flag", "3");
        } catch (Exception he){
            logger.error(he.getCause().getMessage());
            he.printStackTrace();
            mav.addObject("status", "An error while creating project:"+ projectId);
            mav.addObject("flag", "3");
        }
        return mav;
    }

    /**
     * Not used now.
     * Update a network unit on DMS db.
     * @return
     */
    @RequestMapping(value = "/{projectInfoId}",
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method= RequestMethod.POST)
    public String updateProject(@PathVariable("projectInfoId") Long projectInfoId, @RequestParam("projectId") String projectId,
                                @RequestParam("companyName") String companyName,
                                @RequestParam("platform") String platform,
                                @RequestParam("controlSystem") String controlSystem,
                                @RequestParam("channel") String channel,
                                @RequestParam("ipAddress") String ipAddress,
                                @RequestParam("unitSerialNo") String unitSerialNo,
                                @RequestParam("consignedEngineer") String consignedEngineer,
                                @RequestParam("installationDate") java.util.Date installationDate,
                                @RequestParam("restUrl") String url,
                                @RequestParam("headers") String headers,
                                @RequestParam("method") String method) throws Exception {
        String status= "New project with Id:"+ projectId+" created successfully";
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

        ProjectInfo projectInfo = projectRepository.get(projectInfoId);
        if(null == projectInfo){
            throw new Exception("No Unit configured with the given projectInfoId:" + projectInfoId);
        }
        projectInfo.setClientInfo(clientRepository.getClient(companyName));
        projectInfo.setChannel(channel);
        projectInfo.setAlive(false);
        projectInfo.setControlSystem(controlSystem);
        projectInfo.setIpAddress(ipAddress);

        SortedSet<ProjectHistoryInfo> projectHistoryInfos = projectInfo.getProjectHistoryInfoList();
        if(projectHistoryInfos!=null){
            ProjectHistoryInfo projectHistoryInfo = new ProjectHistoryInfo();
            projectHistoryInfo.setUnitSerialNo(unitSerialNo);
            // Make sure this installation date is greater than previous installation dates of other units.
            projectHistoryInfo.setInstallationDate(installationDate);
            projectHistoryInfo.setConsignedEngineer(consignedEngineer);
            projectHistoryInfos.add(projectHistoryInfo);
            if (projectHistoryInfos.size() ==0) {
                // throw an exception here.
                // It is not expected to have zero history of project, when we are updating it.
            }
            /* NOTE: It is by convention that new product unit installation date is taken as the
              decommissioned date of old product unit.
              */
            if (projectHistoryInfos.size()==2) {
                projectHistoryInfos.first().setDeCommissionedDate(projectHistoryInfo.getInstallationDate());
            } else {
                Iterator<ProjectHistoryInfo> itr = projectHistoryInfos.iterator();
                ProjectHistoryInfo prev =null;
                while (itr.hasNext()){
                    if(prev == null){
                        prev = itr.next();
                        continue;
                    }
                    ProjectHistoryInfo current = itr.next();
                    if (current.equals(projectHistoryInfo)){
                        break;
                    }
                    prev = current;
                }
                // now i got the previous element of the current history object inserted
                prev.setDeCommissionedDate(projectHistoryInfo.getInstallationDate());
            }
        }
        // TODO - need this field
        //projectInfo.setUnitSerialNo(unitSerialNo);
        UnitConnectionConfig config = new UnitConnectionConfig();
        config.setUrl(url);
        config.setHeaders(headers);
        config.setMethod(EnumHelper.load(RequestMethod.class, method));
        projectInfo.setUnitConnectionConfig(config);
        projectRepository.update(projectInfo);
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
    public ModelAndView getDataForProject(@PathVariable("projectInfoId") Long projectInfoId) throws IOException {
        ModelAndView mav = new ModelAndView("project_data");
        List<DataCoreMeasurement> dataCoreMeasurement = dataCoreMeasurementRepository.getDataMeasurements(projectInfoId, 0);
        if(dataCoreMeasurement !=null && dataCoreMeasurement.size()>0){
            mav.addObject("companyName", dataCoreMeasurement.get(0).getProjectInfo().getCompanyName());
            mav.addObject("unitSerialNo", dataCoreMeasurement.get(0).getProjectInfo().getUnitSerialNo());
        }
        mav.addObject("data_core_measurements", mapper.writeValueAsString(dataCoreMeasurement));
        mav.addObject("project_info", projectRepository.get(projectInfoId));
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
    public ModelAndView getAllVNetAddressNodes(@PathVariable("projectInfoId") Long projectInfoId) throws IOException {
        ModelAndView mav = new ModelAndView();
        List<String> nodes = dataCoreMeasurementRepository.getVNetAddress(projectInfoId);
        mav.addObject("nodes", nodes);
        return mav;
    }



}
