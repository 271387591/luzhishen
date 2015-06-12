package com.ozstrategy.service.money;

import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManager;

/**
 * Created by lihao1 on 5/30/15.
 */
public interface UserMoneyManager extends GenericManager<UserMoney,Long> {
    void updateMoney(Long id,Double money,User user);
}
