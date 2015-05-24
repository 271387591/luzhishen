package com.ozstrategy.service.appstore;

import com.ozstrategy.model.appstore.AppStore;
import com.ozstrategy.service.GenericManager;

/**
 * Created by lihao1 on 5/24/15.
 */
public interface AppStoreManager extends GenericManager<AppStore,Long> {
    AppStore getCurrent(String platform);
}
