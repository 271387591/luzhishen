package com.ozstrategy.dao.money.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.money.UserMoneyDao;
import com.ozstrategy.model.money.UserMoney;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/29/15.
 */
@Repository("userMoneyDao")
public class UserMoneyDaoImpl extends GenericDaoHibernate<UserMoney,Long> implements UserMoneyDao {
    public UserMoneyDaoImpl() {
        super(UserMoney.class);
    }
}
