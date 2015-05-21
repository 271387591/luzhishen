package com.ozstrategy.dao.tables;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
public interface TableDao extends BaseDao<MTable>{
    void createTable(MTable table,List<TableField> fields);
    void dropTable(MTable table);
}
