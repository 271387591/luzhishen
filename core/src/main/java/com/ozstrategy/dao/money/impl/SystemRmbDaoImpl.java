package com.ozstrategy.dao.money.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.money.SystemRmbDao;
import com.ozstrategy.model.money.SystemRmb;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/30/15.
 */
@Repository("systemRmbDao")
public class SystemRmbDaoImpl extends GenericDaoHibernate<SystemRmb,Long> implements SystemRmbDao {
    public SystemRmbDaoImpl() {
        super(SystemRmb.class);
    }
}
