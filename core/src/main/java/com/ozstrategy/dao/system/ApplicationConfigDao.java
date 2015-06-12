package com.ozstrategy.dao.system;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.model.system.ApplicationConfig;

/**
 * Created by lihao1 on 5/29/15.
 */
public interface ApplicationConfigDao extends GenericDao<ApplicationConfig,Long> {

    ApplicationConfig getConfig(String key);
}
