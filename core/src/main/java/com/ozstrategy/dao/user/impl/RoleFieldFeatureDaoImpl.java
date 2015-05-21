package com.ozstrategy.dao.user.impl;

import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.user.RoleFieldFeatureDao;
import com.ozstrategy.jdbc.SqlBuilder;
import com.ozstrategy.model.user.RoleFieldFeature;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lihao1 on 5/5/15.
 */
@Repository("roleFieldFeatureDao")
public class RoleFieldFeatureDaoImpl extends BaseDaoImpl<RoleFieldFeature> implements RoleFieldFeatureDao {
    public RoleFieldFeatureDaoImpl() {
        super(RoleFieldFeature.class);
    }

    public void deleteByTableId(Long tableId) {
        SqlBuilder.BEGIN();
        SqlBuilder.DELETE_FROM(this.entity.getTableName());
        SqlBuilder.WHERE("tableId=?");
        jdbcTemplate.update(SqlBuilder.SQL(),tableId);
    }

    public List<RoleFieldFeature> listRoleFieldFeatureByRoleId(Long roleId) {
        String sql="select rf.*,tf.cid as fieldCid,tf.name as fieldName from t_rolefieldfeature rf join t_tablefield tf on rf.fieldId=tf.id where rf.roleId=?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RoleFieldFeature.class),roleId);
    }
}
