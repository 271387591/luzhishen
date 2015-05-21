package com.ozstrategy.dao.user.impl;

import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.user.UserDao;
import com.ozstrategy.model.user.Role;
import com.ozstrategy.model.user.RoleTableFeature;
import com.ozstrategy.model.user.User;
import com.ozstrategy.model.user.RoleFieldFeature;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lihao1 on 5/5/15.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

    public UserDaoImpl() {
        super(User.class);
    }

    public List<Role> listTableRoles(Long tableId) {
        String sql="select * from t_role where id in (select DISTINCT(uf.roleId) from t_rolefieldfeature uf join t_role u on uf.roleId=u.id where uf.tableId=?)";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Role.class), tableId);
    }

    public List<RoleFieldFeature> listTableRoleFieldFeature(Long roleId,Long tableId) {
        String sql="select uf.*,tf.cid as fieldCid,tf.name as fieldName  from t_rolefieldfeature uf JOIN t_tablefield tf on uf.fieldId=tf.id where uf.roleId=? and uf.tableId=?";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(RoleFieldFeature.class), roleId,tableId);
    }

    public List<RoleTableFeature> listRoleTableFeature(Long roleId, Long tableId) {
        String sql="select uf.*  from t_roletablefeature uf JOIN t_table tf on uf.tableId=tf.id where uf.roleId=? and uf.tableId=?";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(RoleTableFeature.class), roleId,tableId);
    }
}
