package com.ozstrategy.service.userrole.impl;

import com.ozstrategy.dao.userrole.FeatureDao;
import com.ozstrategy.dao.userrole.RoleDao;
import com.ozstrategy.model.userrole.Feature;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.userrole.FeatureManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("featureManager")
public class FeatureManagerImpl extends GenericManagerImpl<Feature, Long> implements FeatureManager {

    @Autowired
    private FeatureDao featureDao;
    @Autowired
    private RoleDao roleDao;


}
