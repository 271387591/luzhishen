package com.ozstrategy.dao.userrole.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.userrole.RoleDao;
import com.ozstrategy.model.userrole.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoHibernate<Role, Long> implements RoleDao {
 
  public RoleDaoImpl() {
    super(Role.class);
  }

}
