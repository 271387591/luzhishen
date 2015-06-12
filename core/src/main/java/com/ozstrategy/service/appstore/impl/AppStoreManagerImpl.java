package com.ozstrategy.service.appstore.impl;

import com.ozstrategy.dao.appstore.AppStoreDao;
import com.ozstrategy.model.appstore.AppStore;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.appstore.AppStoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihao1 on 5/24/15.
 */
@Repository("appStoreManager")
public class AppStoreManagerImpl extends GenericManagerImpl<AppStore,Long> implements AppStoreManager {
    @Autowired
    private AppStoreDao appStoreDao;
    public AppStore getCurrent(String platform) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_platform_EQ_S",platform);
        return appStoreDao.getByParams(map);
    }
}
