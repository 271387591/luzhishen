package com.ozstrategy.dao.standingbook.impl;

import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.standingbook.StandingBookDao;
import com.ozstrategy.jdbc.QueryField;
import com.ozstrategy.model.standingbook.StandingBook;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
@Repository("standingBookDao")
public class StandingBookDaoImpl extends BaseDaoImpl<StandingBook> implements StandingBookDao {
    public StandingBookDaoImpl() {
        super(StandingBook.class);
    }

    public List<Map<String,Object>> listBooks(Map<String, Object> params, Integer start, Integer limit) throws Exception {
        QueryField queryField=new QueryField(params,"s");
        Object[] args=queryField.getArgs();
        String condition=queryField.getCondition();
        String sql="select s.*,t.name as tableName from t_standingbook s join t_table t on s.tableId=t.id where ";
        sql+=condition;
        return jdbcTemplate.queryForList(sql,args);
    }

    public Integer listBooksCount(Map<String, Object> params) throws Exception {
        QueryField queryField=new QueryField(params,"s");
        Object[] args=queryField.getArgs();
        String condition=queryField.getCondition();
        String sql="select count(*) from t_standingbook s join t_table t on s.tableId=t.id where ";
        sql+=condition;
        return jdbcTemplate.queryForObject(sql, Integer.class,args);
    }
}
