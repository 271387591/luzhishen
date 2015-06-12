package com.ozstrategy.dao.credits.impl;

import com.ozstrategy.dao.credits.UserCreditsDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.credits.UserCredits;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/24/15.
 */
@Repository("userCreditsDao")
public class UserCreditsDaoImpl extends GenericDaoHibernate<UserCredits,Long> implements UserCreditsDao {
    public UserCreditsDaoImpl() {
        super(UserCredits.class);
    }
}
