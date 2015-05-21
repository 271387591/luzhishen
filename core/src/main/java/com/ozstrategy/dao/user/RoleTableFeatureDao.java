package com.ozstrategy.dao.user;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.user.RoleTableFeature;

/**
 * Created by lihao1 on 5/10/15.
 */
public interface RoleTableFeatureDao extends BaseDao<RoleTableFeature> {
    void deleteByTableId(Long tableId);
}
