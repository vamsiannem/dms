/*
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.controller;

import com.dms.model.Permission;
import com.dms.model.Role;
import com.dms.model.User;
import com.dms.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vamsikrishna on 18/10/14.
 */

@Controller
public class UserController extends BaseController {

    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = "/user",method = RequestMethod.GET, headers = {"Content-Type=application/json", "Content-Type=application/xml"})
    @ModelAttribute("users")
    public List<User> listAllUsers(){
        return userRepository.getAllUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, headers = {"Content-Type=application/json", "Content-Type=application/xml"})
    @ModelAttribute("userId")
    public Integer createUser(@RequestBody com.dms.dto.User user){
        Integer userId = userRepository.addUser(user);
        return userId;
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE, headers = {"Content-Type=application/json", "Content-Type=application/xml"})
    @ModelAttribute("status")
    public String deleteUser(@RequestParam("userId") Integer userId ){
        userRepository.deleteUser(userId);
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, headers = {"Content-Type=application/json", "Content-Type=application/xml"})
    @ModelAttribute("user")
    public User updateUser(@RequestParam("userId") Integer userId, @RequestParam("newRoleId") Integer newRoleId ){
        userRepository.updateUserRole(userId, newRoleId);
        return userRepository.getUser(userId);
    }

    @RequestMapping(value ="/role",method = RequestMethod.GET, headers = {"Content-Type=application/json", "Content-Type=application/xml"})
    @ModelAttribute("roles")
    public List<Role> listAllRoles(){
        return userRepository.getAllRoles();
    }

    @RequestMapping(value = "/permission", method = RequestMethod.GET, headers = {"Content-Type=application/json", "Content-Type=application/xml"})
    @ModelAttribute("permissions")
    public List<Permission> listAllPermissions(){
        return userRepository.getAllPermissions();
    }

// TODO: Add other API to perform: 1. Create Role, Update existing Role.

}
