package com.dms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by support on 8/12/14.
 */
@Controller

public class NavigationController extends BaseController {

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

}
