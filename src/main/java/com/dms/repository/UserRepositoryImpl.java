package com.dms.repository;

import com.dms.model.Permission;
import com.dms.model.Role;
import com.dms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vamsikrishna on 18/10/14.
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public Integer addUser(com.dms.dto.User user) {
        Session session = sessionFactory.getCurrentSession();
        User boUser = new User();
        boUser.setName(user.getUsername());
        boUser.setEmail(user.getEmail());
        boUser.setPassword(user.getPassword());
        Role role = (Role) session.get(Role.class, user.getMappedRoleId());
        boUser.setRole(role);
        session.save(boUser);
        return boUser.getId();
    }

    @Override
    public void deleteUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User user= (User) session.get(User.class, id);
        if( user !=null)
            session.delete(user);
        else
            System.err.println("User does not exist to delete");
    }

    @Override
    public User getUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Object user = session.get(User.class, id);
        if(user != null)
            return (User) user;
        else {
            System.err.println("User Not Found!");
            return null;
        }
    }

    @Override
    public Role getRole(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Object user = session.get(Role.class, id);
        if(user != null)
            return (Role) user;
        else {
            System.err.println("Role Not Found!");
            return null;
        }
    }

    @Override
    public java.util.List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public java.util.List<Role> getAllRoles() {
        return sessionFactory.getCurrentSession().createCriteria(Role.class).list();
    }

    @Override
    public java.util.List<Permission> getAllPermissions() {
        return sessionFactory.getCurrentSession().createCriteria(Permission.class).list();
    }

    @Override
    public void updateUserRole(Integer userId, Integer newRole) {
        Session session = sessionFactory.getCurrentSession();
        User user = getUser(userId);
        Role role = getRole(newRole);
        if( user != null  && role !=null){
            user.setRole(role);
            session.saveOrUpdate(user);
        } else {
            System.err.println("Either User or Role does not exist in the system. Role Not Updated !!!");
        }

    }

    @Override
    public void createPermissions(List<Permission> permissions){
        Session session = sessionFactory.getCurrentSession();
        int count =1;
        for(Permission permission: permissions){
            session.save(permission);
            if(++count == 10){
                session.flush();
                session.clear();
                count = 0;
            }
        }
    }

    @Override
    public void createRole(String roleName, List<Integer> permissionIds){
        Session session = sessionFactory.getCurrentSession();
        Set<Permission> permissions = new HashSet<Permission>(permissionIds.size());
        for(Integer pId: permissionIds){
            permissions.addAll(session.createCriteria(Permission.class).add(Restrictions.in("id", permissionIds)).list());
        }
        Role role = new Role();
        role.setName(roleName);
        role.setPermissions(permissions);
        session.save(role);
    }

    @Override
    public void updateRole(Integer roleId, List<Integer> permissionIds){
        Session session = sessionFactory.getCurrentSession();
        Set<Permission> permissions = new HashSet<Permission>(permissionIds.size());
        for(Integer pId: permissionIds){
            permissions.addAll(session.createCriteria(Permission.class).add(Restrictions.in("id", permissionIds)).list());
        }
        Role role = getRole(roleId);
        if(role != null) {
            role.setPermissions(permissions);
            session.update(role);
        } else {
            System.err.println("Give Role Not found in the System:" + roleId);
        }
    }

    @Override
    public void deleteRole(Integer roleId){
        Session session = sessionFactory.getCurrentSession();
        session.delete(getRole(roleId));
    }




}
