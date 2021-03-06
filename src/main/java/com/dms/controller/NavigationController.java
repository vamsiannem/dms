/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.model.DataCoreMeasurement;
import com.dms.repository.DataCoreMeasurementRepository;
import com.dms.repository.ProjectRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: All the logic needs to be moved to Service layer and make this controller a light weight component.
 * Created by support on 8/12/14.
 */
@Controller

public class NavigationController extends BaseController {

    ObjectMapper mapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(NavigationController.class);
    @Resource
    private DataCoreMeasurementRepository dataCoreMeasurementRepository;

    @Resource
    private ProjectRepository projectRepository;

    @RequestMapping(value="/home", method = RequestMethod.POST)
    public ModelAndView renderDashboardView(){
        ModelAndView mav = new ModelAndView("dashboard");
        try {
            mav.addObject("projects", mapper.writeValueAsString(projectRepository.getAll()));
        } catch (IOException e) {
            //
        }
        return mav;
    }

    @RequestMapping(value="/assets", method = RequestMethod.POST)
    public ModelAndView renderAssetsView(){
        ModelAndView mav = new ModelAndView("assets");
        return mav;
    }

    @RequestMapping(value="/graph", method = RequestMethod.POST)
    public ModelAndView renderGraphView(@RequestParam("frm_projectInfoId") Long projectInfoId) {
        ModelAndView mav = new ModelAndView("project_data_graph");
        try {

            List<DataCoreMeasurement> dataMeasurements =  dataCoreMeasurementRepository.getDataMeasurements(projectInfoId, 10000);
            List<Object[]> plotGraphData = new ArrayList<Object[]>(dataMeasurements.size());
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(9);
            String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
            String newFormat = "yyyy-MM-dd HH:mm";

            SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat, Locale.UK );
            SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat, Locale.UK);



            for(DataCoreMeasurement data: dataMeasurements){
                Object[] objects = new Object[2];
                try {
                    objects[0] = (sdf2.format(sdf1.parse(data.getTime())));

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }


                //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                objects[1] = data.getInsulationResistance()!=null ? df.format(data.getInsulationResistance()): 0;
                plotGraphData.add(objects);
            }

            // mav.addObject("dataMeasurements", mapper.writeValueAsString(dataMeasurements));
            mav.addObject("plotData", mapper.writeValueAsString(plotGraphData));
            if(dataMeasurements!=null && dataMeasurements.size()>0){
                mav.addObject("companyName", dataMeasurements.get(0).getProjectInfo().getCompanyName());
                mav.addObject("unitSerialNo", dataMeasurements.get(0).getProjectInfo().getUnitSerialNo());
                mav.addObject("node", dataMeasurements.get(0).getvNetAddress());
            }
            mav.addObject("project_info", projectRepository.get(projectInfoId));
        } catch (IOException ioe){
            logger.error("Error while json conversion:"+ ioe);
            mav.addObject("status", "An error occurred while graph generation.");
            mav.addObject("flag", "3");
        } catch (Exception e){
            logger.error("An unknown error occurred: "+e);
            mav.addObject("status", "An error occurred while graph generation.");
            mav.addObject("flag", "3");
        }
        return mav;
    }


}
