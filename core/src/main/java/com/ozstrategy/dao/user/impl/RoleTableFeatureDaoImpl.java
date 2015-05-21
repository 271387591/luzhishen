package com.ozstrategy.dao.user.impl;

import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.user.RoleTableFeatureDao;
import com.ozstrategy.jdbc.SqlBuilder;
import com.ozstrategy.model.BaseEntity;
import com.ozstrategy.model.user.RoleTableFeature;
import org.springframework.stereotype.Repository;

/**
 * Created by lihao1 on 5/10/15.
 */
@Repository("roleTableFeatureDao")
public class RoleTableFeatureDaoImpl extends BaseDaoImpl<RoleTableFeature> implements RoleTableFeatureDao {
    public RoleTableFeatureDaoImpl() {
        super(RoleTableFeature.class);
    }

    public void deleteByTableId(Long tableId) {
        SqlBuilder.BEGIN();
        SqlBuilder.DELETE_FROM(this.entity.getTableName());
        SqlBuilder.WHERE("tableId=?");
        jdbcTemplate.update(SqlBuilder.SQL(),tableId);
    }
}
