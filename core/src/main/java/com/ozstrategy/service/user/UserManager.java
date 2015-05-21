package com.ozstrategy.service.user;

import com.ozstrategy.model.user.Role;
import com.ozstrategy.model.user.RoleTableFeature;
import com.ozstrategy.model.user.User;
import com.ozstrategy.model.user.RoleFieldFeature;

import java.util.List;

/**
 * Created by lihao1 on 5/5/15.
 */
public interface UserManager {
    List<User> listAllUsers();
    List<Role> listAllRoles();
    List<Role> listTableRoles(Long tableId);
    List<RoleFieldFeature> listTableRoleFieldFeature(Long roleId,Long tableId);
    List<RoleTableFeature> listRoleTableFeature(Long roleId,Long tableId);
    void saveFeature(List<RoleFieldFeature> fieldFeatures,List<RoleTableFeature> tableFeatures,Long tableId);
    User getUserByUsername(String username);
    List<RoleFieldFeature> listUserFieldFeatureByRoleId(Long userId);
    List<RoleTableFeature> listRoleTableFeatureByRoleId(Long roleId);
    Role getRoleById(Long id);

}
