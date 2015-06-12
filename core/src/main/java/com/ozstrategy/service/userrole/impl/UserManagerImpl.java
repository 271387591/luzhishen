package com.ozstrategy.service.userrole.impl;

import com.ozstrategy.dao.credits.UserCreditsDao;
import com.ozstrategy.dao.money.UserMoneyDao;
import com.ozstrategy.dao.userrole.UserDao;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.userrole.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("userManager")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCreditsDao userCreditsDao;
    @Autowired
    private UserMoneyDao userMoneyDao;



    public Boolean existByUserName(String username) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_username_EQ_S",username);
        map.put("Q_enabled_EQ_BL",true);
        User user = userDao.getByParams(map);
        return user != null;
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_username_EQ_S",username);
        map.put("Q_enabled_EQ_BL",true);
        return userDao.getByParams(map);
    }

    public User getUserByEmail(String email) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_email_EQ_S",email);
        map.put("Q_enabled_EQ_BL",true);
        return userDao.getByParams(map);
    }

    public User getUserByMobile(String mobile) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_mobile_EQ_S",mobile);
        map.put("Q_enabled_EQ_BL",true);
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
        boolean save=false;
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            save=true;
        }
        userDao.saveOrUpdate(user);
        if(save){
            UserCredits credits=new UserCredits();
            credits.setUser(user);
            credits.setCreateDate(new Date());
            userCreditsDao.save(credits);
            UserMoney money=new UserMoney();
            money.setUser(user);
            money.setCreateDate(new Date());
            userMoneyDao.save(money);
        }
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
