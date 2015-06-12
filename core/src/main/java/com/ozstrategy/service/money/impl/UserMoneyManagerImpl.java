package com.ozstrategy.service.money.impl;

import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.money.UserMoneyManager;
import org.springframework.stereotype.Service;

/**
 * Created by lihao1 on 5/30/15.
 */
@Service("userMoneyManager")
public class UserMoneyManagerImpl extends GenericManagerImpl<UserMoney,Long> implements UserMoneyManager {
    public void updateMoney(Long id, Double money, User user) {

    }
}
