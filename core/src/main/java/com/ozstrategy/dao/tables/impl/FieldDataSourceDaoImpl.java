package com.ozstrategy.dao.tables.impl;

import com.ozstrategy.bo.DataSourceFiledBo;
import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.tables.FieldDataSourceDao;
import com.ozstrategy.jdbc.PageContext;
import com.ozstrategy.jdbc.QueryField;
import com.ozstrategy.jdbc.SqlBuilder;
import com.ozstrategy.model.tables.FieldDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
@Repository("fieldDataSourceDao")
public class FieldDataSourceDaoImpl extends BaseDaoImpl<FieldDataSource> implements FieldDataSourceDao {

    public FieldDataSourceDaoImpl() {
        super(FieldDataSource.class);
    }

    public List<DataSourceFiledBo> getDataSourceFields(FieldDataSource source) {
        String table=source.getMtableName();
        String sql="select * from "+table;

        return jdbcTemplate.execute(sql, new PreparedStatementCallback<List<DataSourceFiledBo>>(){
            List<DataSourceFiledBo> list=new ArrayList<DataSourceFiledBo>();
            public List<DataSourceFiledBo> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                for( int i=1; i<=rsmd.getColumnCount(); i++ ){
                    String field = rsmd.getColumnName(i);
                    int type = rsmd.getColumnType(i);
                    DataSourceFiledBo bo=new DataSourceFiledBo(field,type);
                    list.add(bo);
                }
                return list;
            }
        });
    }

    public List<Map<String, Object>> listDataSourceDatas(FieldDataSource source) {
        String tableName=source.getMtableName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);
        return jdbcTemplate.queryForList(SqlBuilder.SQL());
    }

    public List<Map<String, Object>> listDataSourceDatas(FieldDataSource source, Map<String, Object> params, Integer start, Integer limit) {
        String tableName=source.getMtableName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);

        QueryField queryField=new QueryField(params);
        String condition=queryField.getCondition();
        if(StringUtils.isNotEmpty(condition)){
            SqlBuilder.WHERE(condition);
        }
        Object[] args=queryField.getArgs();
        String sql=SqlBuilder.SQL();
        sql=new PageContext(sql,start,limit).pageSql();

        return jdbcTemplate.queryForList(sql,args);
    }

    public Integer listDataSourceDatasCount(FieldDataSource source, Map<String, Object> params) {
        String tableName=source.getMtableName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("count(*)");
        SqlBuilder.FROM(tableName);
        QueryField queryField=new QueryField(params);
        String condition=queryField.getCondition();
        if(StringUtils.isNotEmpty(condition)){
            SqlBuilder.WHERE(condition);
        }
        Object[] args=queryField.getArgs();
        String sql=SqlBuilder.SQL();
        return jdbcTemplate.queryForObject(sql, Integer.class, args);
    }

    public List<Map<String, Object>> listDataSourceTreeDatas(FieldDataSource source, String parent, Object parentValue) {
        String tableName=source.getMtableName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);
        if(parentValue==null){
            SqlBuilder.WHERE(parent+" is null");
            return jdbcTemplate.queryForList(SqlBuilder.SQL());
        }else{
            SqlBuilder.WHERE(parent+" =?");
            return jdbcTemplate.queryForList(SqlBuilder.SQL(),parentValue);
        }
    }

    public Map<String, Object> getDataByFieldValue(FieldDataSource source, String field, Object fieldValue) {
        String tableName=source.getMtableName();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(tableName);
        SqlBuilder.WHERE(field+" =?");
        List<Map<String,Object>> list=jdbcTemplate.queryForList(SqlBuilder.SQL(),fieldValue);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public void deleteFieldDataSourceByName(String name) {
        FieldDataSource source=getFieldDataSourceByName(name);
        if(source!=null){
            delete(source);
        }
    }

    public FieldDataSource getFieldDataSourceByName(String name) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_mtableName_EQ",name);
        List<FieldDataSource> list=listPage(map,0,1);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
