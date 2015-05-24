package com.ozstrategy.service.userrole.impl;

import com.ozstrategy.dao.userrole.UserDao;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.userrole.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userManager")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    public Boolean existByUserName(String username) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_username_EQ",username);
        User user = userDao.getByParams(map);
        return user != null;
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_username_EQ",username);
        return userDao.getByParams(map);
    }

    public User getUserByEmail(String email) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_email_EQ",email);
        return userDao.getByParams(map);
    }

    public User getUserByMobile(String mobile) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_mobile_EQ",mobile);
        return userDao.getByParams(map);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Integer updateUserPassword(User user, String oldPassword, String newPassword, boolean admin) {
        if (admin) {
            user.setPassword(passwordEncoder.encodePassword(newPassword, null));
            userDao.saveOrUpdate(user);
            return 0;
        }
        if (user.getPassword().equals(passwordEncoder.encodePassword(oldPassword, null))) {
            user.setPassword(passwordEncoder.encodePassword(newPassword, null));
            userDao.saveOrUpdate(user);
            return 0;
        }
        return 1;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeUser(User user) {
        user.setEnabled(false);
        userDao.saveOrUpdate(user);
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        }
        userDao.saveOrUpdate(user);
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void lockOrUnLockUser(User user, boolean lock) {
        if (lock) {
            user.setAccountLocked(true);
        } else {
            user.setAccountLocked(false);
        }
        userDao.saveOrUpdate(user);
    }
} // end class UserManagerImpl
