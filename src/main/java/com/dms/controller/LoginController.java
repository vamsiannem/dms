package com.dms.controller;

import com.dms.model.User;
import com.dms.repository.LoginRepository;
import com.dms.utils.DMSConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vamsikrishna on 19/10/14.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginRepository loginRepository;

    @RequestMapping(method = RequestMethod.POST, headers = {"content-type=application/x-www-form-urlencoded"})
    public ModelAndView doLogin(HttpServletRequest request, @RequestParam("username") String username,@RequestParam("username") String password) throws Exception {
        User user = loginRepository.doLogin(username, password);
        if(user != null){
            request.getSession(true).setAttribute(DMSConstants.SESSION_USER, user);
        } else {
           // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new Exception("Invalid username or password");
        }
        ModelAndView modelAndView = new ModelAndView("view_graph");
        modelAndView.addObject("status", "SUCCESS");
        return modelAndView;
    }
}
