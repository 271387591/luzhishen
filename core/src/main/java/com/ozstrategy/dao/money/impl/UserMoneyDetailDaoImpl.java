package com.ozstrategy.dao.money.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.money.UserMoneyDetailDao;
import com.ozstrategy.model.money.UserMoneyDetail;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/29/15.
 */
@Repository("userMoneyDetailDao")
public class UserMoneyDetailDaoImpl extends GenericDaoHibernate<UserMoneyDetail,Long> implements UserMoneyDetailDao {
    public UserMoneyDetailDaoImpl() {
        super(UserMoneyDetail.class);
    }
}
