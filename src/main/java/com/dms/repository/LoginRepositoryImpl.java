package com.dms.repository;

import com.dms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vamsikrishna on 19/10/14.
 */
@Repository
@Transactional
public class LoginRepositoryImpl implements LoginRepository {

    @Resource
    SessionFactory sessionFactory;

    public User doLogin(String username, String password){
        Session session = sessionFactory.getCurrentSession();
        Map<String, String> restrictions = new HashMap<String, String>(2);
        restrictions.put("name", username);
        restrictions.put("password", password);
        User user = (User) session.createCriteria(User.class).add(Restrictions.allEq(restrictions)).uniqueResult();
        if(user !=null){
            System.out.println("Login Successful, returning User model");
            return user;
        }
        System.err.println("Either username or password is incorrect");
        return null;
    }
}
