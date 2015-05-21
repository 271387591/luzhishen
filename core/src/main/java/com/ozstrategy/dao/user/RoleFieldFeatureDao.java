package com.ozstrategy.dao.user;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.user.RoleFieldFeature;

import java.util.List;

/**
 * Created by lihao1 on 5/5/15.
 */
public interface RoleFieldFeatureDao extends BaseDao<RoleFieldFeature> {
    void deleteByTableId(Long tableId);
    List<RoleFieldFeature> listRoleFieldFeatureByRoleId(Long roleId);
}
