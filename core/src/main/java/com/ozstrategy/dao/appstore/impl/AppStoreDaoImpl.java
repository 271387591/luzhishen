package com.ozstrategy.dao.appstore.impl;

import com.ozstrategy.dao.appstore.AppStoreDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.appstore.AppStore;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/24/15.
 */
@Repository("appStoreDao")
public class AppStoreDaoImpl extends GenericDaoHibernate<AppStore,Long> implements AppStoreDao {
    public AppStoreDaoImpl() {
        super(AppStore.class);
    }
}
