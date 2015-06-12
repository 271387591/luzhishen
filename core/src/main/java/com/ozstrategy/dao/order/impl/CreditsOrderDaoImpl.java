package com.ozstrategy.dao.order.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.order.CreditsOrderDao;
import com.ozstrategy.model.order.CreditsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/28/15.
 */
@Repository("creditsOrderDao")
public class CreditsOrderDaoImpl extends GenericDaoHibernate<CreditsOrder,Long> implements CreditsOrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public CreditsOrderDaoImpl() {
        super(CreditsOrder.class);
    }

    public List<Map<String, Object>> getOrderChart() {
        String sql="select count(*) as count,status from CreditsOrder where loseDate>=now() GROUP BY status";
        return jdbcTemplate.queryForList(sql);
    }
}
