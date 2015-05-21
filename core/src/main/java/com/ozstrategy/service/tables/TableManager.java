package com.ozstrategy.service.tables;

import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
public interface TableManager {
    List<MTable> listTables(Map<String,Object> params,Integer start,Integer limit);
    Integer listTablesCount(Map<String,Object> params);
    void saveTable(MTable table,List<TableField> fields);
    MTable getById(Long id);
    List<TableField> getFieldsByTableId(Long tableId);
    void deleteTable(MTable table) throws Exception;
    List<MTable> listAll() throws Exception;
    MTable getByName(String name);
    List<MTable> listNoMenu();

}
