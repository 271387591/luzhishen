package com.ozstrategy.dao.money.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.money.UserAliInfoDao;
import com.ozstrategy.model.money.UserAliInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/30/15.
 */
@Repository("userAliInfoDao")
public class UserAliInfoDaoImpl extends GenericDaoHibernate<UserAliInfo,Long> implements UserAliInfoDao {
    public UserAliInfoDaoImpl() {
        super(UserAliInfo.class);
    }
}
