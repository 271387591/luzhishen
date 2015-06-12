package com.ozstrategy.dao.order.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.order.CreditsBillDao;
import com.ozstrategy.model.order.CreditsBill;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/26/15.
 */
@Repository("creditsBillDao")
public class CreditsBillDaoImpl extends GenericDaoHibernate<CreditsBill,Long> implements CreditsBillDao {
    public CreditsBillDaoImpl() {
        super(CreditsBill.class);
    }
}
