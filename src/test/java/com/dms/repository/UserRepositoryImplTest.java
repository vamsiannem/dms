package com.dms.repository;

import com.dms.model.Permission;
import com.dms.model.Role;
import com.dms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

//@ContextConfiguration("/testContext.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class UserRepositoryImplTest {

    private Role role;

    //@Resource
    UserRepository userRepository;

    //@Resource
    SessionFactory sessionFactory;

   // @Before
    public void createDummyRolesNPermissions(){
        Session session = sessionFactory.getCurrentSession();
        Set<Permission> permissions = new HashSet<Permission>(3);
        permissions.add(createPermission("UPLOAD_CSV", "Permission to upload a CSV file."));
        permissions.add(createPermission("MANAGE_USER", "Permission to manage users."));
        permissions.add(createPermission("VIEW_CSV_CHARTS", "Permission to view CSV data in the form of graphs."));
        for(Permission permission: permissions){
            session.save(permission);
        }
        session.flush();
        role = new Role();
        role.setName("ADMIN");
        role.setPermissions(permissions);
        session.save(role);
        session.flush();
        session.clear();
    }

   // @Test
    public void testAddUser() throws Exception {
        com.dms.dto.User dtoUser = createSampleDTOUser();
        userRepository.addUser(dtoUser);
        //Integer userId = user.getId();
        User actualValue = (User) sessionFactory.getCurrentSession()
                .createCriteria(User.class).add(Restrictions.eq("name", dtoUser.getUsername())).uniqueResult();
        assertNotNull(actualValue);
        assertEquals(dtoUser.getEmail(), actualValue.getEmail());
    }

   // @Test
    public void testDeleteUser() throws Exception {
        User user = createSampleUser();
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        session.flush();
        Integer userId = user.getId();
        Integer roleId = user.getRole().getId();
        userRepository.deleteUser(user.getId());
        User _user = (User) session.get(User.class, userId);
        Role _role = (Role) session.get(Role.class, roleId );
        assertNotNull(_role);
        assertEquals(role.getName(), _role.getName());
        assertNull(_user);
    }


    private User createSampleUser(){
        User user = new User();
        user.setName("Vamsi");
        user.setPassword("test");
        user.setRole(role);
        return user;
    }

    private com.dms.dto.User createSampleDTOUser(){
        com.dms.dto.User dtoUser = new com.dms.dto.User();
        dtoUser.setUsername("TEST");
        dtoUser.setEmail("adfs");
        dtoUser.setPassword("FDADDA");
        dtoUser.setMappedRoleId(role.getId());
        return dtoUser;
    }

    private Permission createPermission(String name, String desc){
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(desc);
        return permission;
    }
}