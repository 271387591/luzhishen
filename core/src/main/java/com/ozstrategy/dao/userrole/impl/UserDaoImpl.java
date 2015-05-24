package com.ozstrategy.dao.userrole.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.userrole.UserDao;
import com.ozstrategy.model.userrole.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {
    public UserDaoImpl() {
        super(User.class);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getHibernateTemplate().find("from User where upper(username)=?", username.toUpperCase());
        if (users != null && users.size() > 0) {
            return (UserDetails) users.get(0);
        } else {
            users = getHibernateTemplate().find("from User where mobile=?", username.toUpperCase());
        }
        if (users == null || users.size() < 1) {
            users = getHibernateTemplate().find("from User where email=?", username.toUpperCase());
        }
        if ((users == null) || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

} 
