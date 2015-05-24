package com.ozstrategy.service.userrole;

import com.ozstrategy.model.userrole.Feature;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.service.GenericManager;

import java.util.List;
import java.util.Set;


public interface RoleManager extends GenericManager<Role, Long> {
    Boolean hasFeature(Set<String> roles,String feature);
    List<Feature> listFeatureNoParent();
    List<Feature> listFeatureByParent(Long parentId);
    Role getRoleByName(String name);
    Feature getFeatureById(Long id);
    void removeRole(Role role);
}
