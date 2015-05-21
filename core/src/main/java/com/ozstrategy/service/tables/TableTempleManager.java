package com.ozstrategy.service.tables;

import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/2/15.
 */
public interface TableTempleManager {
    List<Map<String,Object>> listPage(MTable table,Map<String,Object> map,Integer start,Integer limit);
    Integer listPageCount(MTable table,Map<String,Object> map);
    void save(MTable table,List<TableField> tableFields,Map<String,Object> map);
    void update(MTable table,List<TableField> tableFields,Map<String,Object> map,Long id);
    void delete(MTable table,Long id);
    Map<String,Object> getById(MTable table,Long id);
    Map<String,Object> getByUniqueField(MTable table,String field,Object value);
}
