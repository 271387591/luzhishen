package com.ozstrategy.dao.user;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.user.Role;
import com.ozstrategy.model.user.RoleTableFeature;
import com.ozstrategy.model.user.User;
import com.ozstrategy.model.user.RoleFieldFeature;

import java.util.List;

/**
 * Created by lihao1 on 5/5/15.
 */
public interface UserDao extends BaseDao<User> {
    List<Role> listTableRoles(Long tableId);
    List<RoleFieldFeature> listTableRoleFieldFeature(Long userId,Long tableId);
    List<RoleTableFeature> listRoleTableFeature(Long roleId, Long tableId);
}
