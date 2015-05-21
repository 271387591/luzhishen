package com.ozstrategy.dao.tables.impl;

import com.ozstrategy.annotations.Table;
import com.ozstrategy.dao.tables.TableTempleDao;
import com.ozstrategy.jdbc.PageContext;
import com.ozstrategy.jdbc.QueryField;
import com.ozstrategy.jdbc.SqlBuilder;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/2/15.
 */
@Repository("tableTempleDao")
public class TableTempleDaoImpl implements TableTempleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> listPage(MTable table, Map<String, Object> map, Integer start, Integer limit) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);
        QueryField queryField=new QueryField(map);
        String condition=queryField.getCondition();
        Object[] args=queryField.getArgs();
        SqlBuilder.WHERE(condition);
        String sql=new PageContext(SqlBuilder.SQL(),start,limit).pageSql();
        return jdbcTemplate.queryForList(sql,args);
    }

    public Integer listPageCount(MTable table, Map<String, Object> map) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("count(*)");
        SqlBuilder.FROM(tableName);
        QueryField queryField=new QueryField(map);
        String condition=queryField.getCondition();
        Object[] args=queryField.getArgs();
        SqlBuilder.WHERE(condition);
        String sql=SqlBuilder.SQL();
        return jdbcTemplate.queryForObject(sql, Integer.class,args);
    }

    public void save(MTable table, List<TableField> tableFields,Map<String, Object> map) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.INSERT_INTO(tableName);
        List<String> fields=new ArrayList<String>();
        List<Object> values=new ArrayList<Object>();
        List<String> pix=new ArrayList<String>();
        for(TableField tableField:tableFields){
            String cid=tableField.getCid();
            Object value=map.get(cid);
            fields.add(cid);
            values.add(value);
            pix.add("?");
        }
        String fieldSql= StringUtils.join(fields.iterator(),",");
        String pixes=StringUtils.join(pix.iterator(),",");
        SqlBuilder.VALUES(fieldSql,pixes);
        String sql=SqlBuilder.SQL();
        Object[] args=values.toArray();
        jdbcTemplate.update(sql,args);
    }

    public void update(MTable table, List<TableField> tableFields, Map<String, Object> map, Long id) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.UPDATE(tableName);
        List<String> fields=new ArrayList<String>();
        List<Object> values=new ArrayList<Object>();
        for(TableField tableField:tableFields){
            String cid=tableField.getCid();
            Object value=map.get(cid);
            fields.add(cid+"=?");
            values.add(value);
        }
        String fieldSql= StringUtils.join(fields.iterator(),",");
        SqlBuilder.SET(fieldSql);
        SqlBuilder.WHERE("id="+id);
        String sql=SqlBuilder.SQL();

        Object[] args=values.toArray();
        jdbcTemplate.update(sql, args);
    }

    public void delete(MTable table, Long id) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.DELETE_FROM(tableName);
        SqlBuilder.WHERE("id=?");
        String sql=SqlBuilder.SQL();
        Object[] args=new Object[]{id};
        jdbcTemplate.update(sql,args);
    }

    public Map<String, Object> getById(MTable table, Long id) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);
        SqlBuilder.WHERE("id=?");
        List<Map<String,Object>> list=jdbcTemplate.queryForList(SqlBuilder.SQL(),new Object[]{id});
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Map<String, Object> getByUniqueField(MTable table, String field, Object value) {
        String tableName=table.getAliasName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);
        SqlBuilder.WHERE(field+"=?");
        List<Map<String,Object>> list=jdbcTemplate.queryForList(SqlBuilder.SQL(),new Object[]{value});
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }


}
