package com.ozstrategy.dao.aps.impl;

import com.ozstrategy.dao.aps.ApsDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.aps.Aps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lihao1 on 5/25/15.
 */
@Repository("apsDao")
public class ApsDaoImpl extends GenericDaoHibernate<Aps,Long> implements ApsDao {
    public ApsDaoImpl() {
        super(Aps.class);
    }

    public List<Aps> getAp(Long user_id, String SSID, String BSSID) {
        String[] ssids = StringUtils.split(SSID, ",");
        String hql="from Aps where ssid in (:ssid)";
        return getSession().createQuery(hql).setParameterList("ssid", Arrays.asList(ssids)).list();
    }
}
