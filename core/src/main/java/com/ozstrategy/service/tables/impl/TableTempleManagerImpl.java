package com.ozstrategy.service.tables.impl;

import com.ozstrategy.dao.tables.TableTempleDao;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.service.tables.TableTempleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/2/15.
 */
@Service("tableTempleManager")
public class TableTempleManagerImpl implements TableTempleManager {
    @Autowired
    private TableTempleDao tableTempleDao;
    public List<Map<String, Object>> listPage(MTable table, Map<String, Object> map, Integer start, Integer limit) {
        return tableTempleDao.listPage(table, map, start, limit);
    }

    public Integer listPageCount(MTable table, Map<String, Object> map) {
        return tableTempleDao.listPageCount(table, map);
    }

    public void save(MTable table, List<TableField> tableFields, Map<String, Object> map) {
        tableTempleDao.save(table, tableFields, map);
    }

    public void update(MTable table, List<TableField> tableFields, Map<String, Object> map, Long id) {
        tableTempleDao.update(table, tableFields, map, id);
    }

    public void delete(MTable table, Long id) {
        tableTempleDao.delete(table, id);
    }

    public Map<String, Object> getById(MTable table, Long id) {
        return tableTempleDao.getById(table, id);
    }

    public Map<String, Object> getByUniqueField(MTable table, String field, Object value) {
        return tableTempleDao.getByUniqueField(table, field, value);
    }
}
