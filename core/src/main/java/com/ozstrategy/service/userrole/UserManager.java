package com.ozstrategy.service.userrole;

import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManager;

public interface UserManager extends GenericManager<User, Long> {

    Boolean existByUserName(String username);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByMobile(String mobile);

    Integer updateUserPassword(User user, String oldPassword, String newPassword, boolean admin);

    void removeUser(User user);

    User saveUser(User user);

    void lockOrUnLockUser(User user,boolean lock);
} 
