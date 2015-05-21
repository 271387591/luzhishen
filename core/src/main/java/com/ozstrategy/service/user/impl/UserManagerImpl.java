package com.ozstrategy.service.user.impl;

import com.ozstrategy.dao.user.RoleDao;
import com.ozstrategy.dao.user.RoleTableFeatureDao;
import com.ozstrategy.dao.user.UserDao;
import com.ozstrategy.dao.user.RoleFieldFeatureDao;
import com.ozstrategy.model.user.Role;
import com.ozstrategy.model.user.RoleTableFeature;
import com.ozstrategy.model.user.User;
import com.ozstrategy.model.user.RoleFieldFeature;
import com.ozstrategy.service.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/5/15.
 */
@Service("userManager")
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleFieldFeatureDao roleFieldFeatureDao;
    @Autowired
    private RoleTableFeatureDao roleTableFeatureDao;


    public List<User> listAllUsers() {
        return userDao.listAll();
    }

    public List<Role> listAllRoles() {
        return roleDao.listAll();
    }

    public List<Role> listTableRoles(Long tableId) {
        return userDao.listTableRoles(tableId);
    }

    public List<RoleFieldFeature> listTableRoleFieldFeature(Long userId,Long tableId) {
        return userDao.listTableRoleFieldFeature(userId, tableId);
    }

    public List<RoleTableFeature> listRoleTableFeature(Long roleId, Long tableId) {
        return userDao.listRoleTableFeature(roleId,tableId);
    }

    public void saveFeature(List<RoleFieldFeature> fieldFeatures,List<RoleTableFeature> tableFeatures,Long tableId) {
        roleFieldFeatureDao.deleteByTableId(tableId);
        roleTableFeatureDao.deleteByTableId(tableId);
        for(RoleFieldFeature feature:fieldFeatures){
            roleFieldFeatureDao.save(feature);
        }
        for(RoleTableFeature feature:tableFeatures){
            roleTableFeatureDao.save(feature);
        }
    }

    public User getUserByUsername(String username) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_username_EQ",username);
        List<User> fieldList=userDao.listPage(map, 0, 1);
        if(fieldList!=null && fieldList.size()>0){
            return fieldList.get(0);
        }
        return null;
    }

    public List<RoleFieldFeature> listUserFieldFeatureByRoleId(Long roleId) {
        return roleFieldFeatureDao.listRoleFieldFeatureByRoleId(roleId);
    }

    public List<RoleTableFeature> listRoleTableFeatureByRoleId(Long roleId) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_roleId_EQ",roleId);
        List<RoleTableFeature> fieldList= roleTableFeatureDao.listAll(map);
        return fieldList;
    }

    public Role getRoleById(Long id) {
        return roleDao.get(id);
    }
}
