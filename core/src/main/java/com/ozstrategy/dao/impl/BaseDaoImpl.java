package com.ozstrategy.dao.impl;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.jdbc.SqlBuilder;
import com.ozstrategy.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
@Repository("baseDao")
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {
    protected JdbcTemplate jdbcTemplate;
    protected BaseEntity entity;
    Class<? extends BaseEntity> clazz;

    public BaseDaoImpl(Class<? extends BaseEntity> clazz) {
        this.clazz = clazz;
        try {
            this.entity = clazz.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public List<T> listAll() throws RuntimeException {
        String sql = this.entity.selectAllSql();
        return (List<T>) jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(entity.getClass()));
    }

    public List<T> listAll(Map<String, Object> params) {
        return listPage(params,0,Integer.MAX_VALUE);
    }


    public List<T> listPage(Map<String, Object> params, Integer start, Integer limit) {
        Object[] objects = this.entity.selectPageSql(params, start, limit);
        return (List<T>) jdbcTemplate.query(objects[0].toString(), (Object[]) objects[1], BeanPropertyRowMapper.newInstance(entity.getClass()));
    }

    public Integer listPageCount(Map<String, Object> params) {
        Object[] objects = this.entity.selectPageCount(params);
        return jdbcTemplate.queryForObject(objects[0].toString(), Integer.class, (Object[]) objects[1]);
    }

    public T save(T entity) {
        Object[] objects = entity.insertSql();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = objects[0].toString();
        final Object[] args = (Object[]) objects[1];
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                new ArgumentPreparedStatementSetter(args).setValues(ps);
                return ps;
            }
        }, keyHolder);
        Long generatedId = keyHolder.getKey().longValue();
        entity.setIdValue(generatedId);
        return entity;
    }

    public void update(T entity) {
        Object[] objects = entity.updateSql();
        final String sql = objects[0].toString();
        final Object[] args = (Object[]) objects[1];
        jdbcTemplate.update(sql, args);
    }

    public void delete(T entity) {
        Object[] objects = entity.deleteSql();
        final String sql = objects[0].toString();
        final Object[] args = (Object[]) objects[1];
        jdbcTemplate.update(sql, args);
    }

    public T get(Serializable id) {
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(this.entity.getTableName());
        SqlBuilder.WHERE(this.entity.getIdColumn() + "=?");
        String sql = SqlBuilder.SQL();
        return (T) jdbcTemplate.queryForObject(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(entity.getClass()));
    }

    public void deleteById(Serializable id) {
        SqlBuilder.BEGIN();
        SqlBuilder.DELETE_FROM(this.entity.getTableName());
        SqlBuilder.WHERE(this.entity.getIdColumn() + "=?");
        String sql = SqlBuilder.SQL();
        jdbcTemplate.update(sql, new Object[]{id});
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
