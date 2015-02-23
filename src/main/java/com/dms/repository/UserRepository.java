/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.repository;

import com.dms.model.Permission;
import com.dms.model.Role;
import com.dms.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by vamsikrishna on 18/10/14.
 */
public interface UserRepository {


    Integer addUser(com.dms.dto.User user);

    void deleteUser(Integer id);
    User getUser(Integer id);

    Role getRole(Integer id);

    java.util.List<User> getAllUsers();

    List<Role> getAllRoles();

    List<Permission> getAllPermissions();

    void updateUserRole(Integer userId, Integer newRole);

    void createPermissions(List<Permission> permissions);

    @Transactional
    void createRole(String roleName, List<Integer> permissionIds);

    void updateRole(Integer roleId, List<Integer> permissionIds);

    void deleteRole(Integer roleId);
}

