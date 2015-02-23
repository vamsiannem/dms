/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.model.User;
import com.dms.repository.LoginRepository;
import com.dms.repository.NetworkUnitRepository;
import com.dms.utils.DMSConstants;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vamsikrishna on 19/10/14.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Resource
    private LoginRepository loginRepository;

    @Resource
    private NetworkUnitRepository unitRepository;

    @RequestMapping(method = RequestMethod.POST, headers = {"content-type=application/x-www-form-urlencoded"})
    public ModelAndView doLogin(HttpServletRequest request, @RequestParam("username") String username,@RequestParam("password") String password) throws Exception {
        User user = loginRepository.doLogin(username, password);
        if(user != null){
            request.getSession(true).setAttribute(DMSConstants.SESSION_USER, user);
        } else {
           // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new Exception("Invalid username or password");
        }
        ModelAndView modelAndView = new ModelAndView("dashboard");
        try {
            modelAndView.addObject("networkUnits", mapper.writeValueAsString(unitRepository.getAll()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("status", "SUCCESS");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", "Unable to login, Please verify your credentials");
        mav.setViewName("index");
        return mav;
    }
}
