package com.ozstrategy.dao.money.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.money.UserApplyDao;
import com.ozstrategy.model.money.UserApply;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/30/15.
 */
@Repository("userApplyDao")
public class UserApplyDaoImpl extends GenericDaoHibernate<UserApply,Long> implements UserApplyDao
{
    public UserApplyDaoImpl() {
        super(UserApply.class);
    }
}
