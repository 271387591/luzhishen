package com.ozstrategy.dao.userrole.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.userrole.FeatureDao;
import com.ozstrategy.model.userrole.Feature;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("featureDao")
public class FeatureDaoImpl extends GenericDaoHibernate<Feature, Long> implements FeatureDao {
  
  public FeatureDaoImpl() {
    super(Feature.class);
  }

}
