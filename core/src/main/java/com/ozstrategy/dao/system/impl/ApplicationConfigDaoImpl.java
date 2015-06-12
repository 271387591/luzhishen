package com.ozstrategy.dao.system.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.system.ApplicationConfigDao;
import com.ozstrategy.model.system.ApplicationConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lihao1 on 5/29/15.
 */
@Repository("applicationConfigDao")
public class ApplicationConfigDaoImpl extends GenericDaoHibernate<ApplicationConfig,Long> implements ApplicationConfigDao {
    public ApplicationConfigDaoImpl() {
        super(ApplicationConfig.class);
    }
    @CacheEvict(value = "configsCache")
    public ApplicationConfig getConfig(String key) {
        String hql="from ApplicationConfig u where u.systemKey=?";
        List<ApplicationConfig> configs=getSession().createQuery(hql).setParameter(0,key).list();
        if (configs != null && configs.size() > 0) {
            return configs.get(0);
        }
        return null;
    }
}
