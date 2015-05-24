package com.ozstrategy.service.userrole.impl;

import com.ozstrategy.dao.userrole.FeatureDao;
import com.ozstrategy.dao.userrole.RoleDao;
import com.ozstrategy.dao.userrole.UserDao;
import com.ozstrategy.model.userrole.Feature;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.userrole.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("roleManager")
public class RoleManagerImpl extends GenericManagerImpl<Role, Long> implements RoleManager {
  @Autowired 
  RoleDao roleDao;
  @Autowired
  UserDao userDao;
    @Autowired
    FeatureDao featureDao;



    public Boolean hasFeature(Set<String> roles, String feature) {

        return true;
    }

    public List<Feature> listFeatureNoParent() {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_parentId_NULL","NULL");
        return featureDao.listAll(map);
    }

    public List<Feature> listFeatureByParent(Long parentId) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_parentId_EQ",parentId);
        return featureDao.listAll(map);
    }

    public Role getRoleByName(String name) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_name_EQ",name);
        return roleDao.getByParams(map);
    }

    public Feature getFeatureById(Long id) {
        return featureDao.get(id);
    }

    public void removeRole(Role role) {
        roleDao.remove(role);
    }

}
