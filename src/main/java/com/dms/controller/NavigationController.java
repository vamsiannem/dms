/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.model.ProductData;
import com.dms.repository.ProductRepository;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
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

/**
 * Created by support on 8/12/14.
 */
@Controller

public class NavigationController extends BaseController {

    ObjectMapper mapper = new ObjectMapper();
    @Resource
    private ProductRepository productRepository;

    @RequestMapping(value="/home", method = RequestMethod.POST)
    public ModelAndView renderDashboardView(){
        ModelAndView mav = new ModelAndView("dashboard");
        return mav;
    }

    @RequestMapping(value="/assets", method = RequestMethod.POST)
    public ModelAndView renderAssetsView(){
        ModelAndView mav = new ModelAndView("assets");
        return mav;
    }

    @RequestMapping(value="/graph", method = RequestMethod.POST)
    public ModelAndView renderGraphView(@RequestParam("frm_projectId") String projectId) throws IOException {
        ModelAndView mav = new ModelAndView("network_graph");
        List<ProductData> products =  productRepository.getDataForProduct(projectId);
        List<Object[]> plotGraphData = new ArrayList<Object[]>(products.size());
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(9);
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "yyyy-MM-dd HH:mm";

        SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);



        for(ProductData product: products){
            Object[] objects = new Object[2];
            try {
                objects[0] = (sdf2.format(sdf1.parse(product.getTime())));

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }


            //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            objects[1] = product.getLimResistance()!=null ? df.format(product.getLimResistance()): 0;
            plotGraphData.add(objects);
        }

        mav.addObject("products", mapper.writeValueAsString(products));
        mav.addObject("plotData", mapper.writeValueAsString(plotGraphData));
        if(products!=null && products.size()>0){
            mav.addObject("companyName", products.get(0).getNetworkUnit().getCompanyName());
            mav.addObject("unitSerialNo", products.get(0).getNetworkUnit().getUnitSerialNo());
        }
        return mav;
    }


}
