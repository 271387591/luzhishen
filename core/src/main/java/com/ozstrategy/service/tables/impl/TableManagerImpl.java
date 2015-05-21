package com.ozstrategy.service.tables.impl;

import com.ozstrategy.dao.tables.FieldDataSourceDao;
import com.ozstrategy.dao.tables.TableFieldDao;
import com.ozstrategy.dao.tables.TableDao;
import com.ozstrategy.dao.user.RoleFieldFeatureDao;
import com.ozstrategy.dao.user.RoleTableFeatureDao;
import com.ozstrategy.model.tables.FieldDataSource;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.service.tables.TableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
@Service("tableManager")
public class TableManagerImpl implements TableManager {
    @Autowired
    private TableDao tableDao;
    @Autowired
    private TableFieldDao tableFieldDao;
    @Autowired
    private FieldDataSourceDao fieldDataSourceDao;
    @Autowired
    private RoleFieldFeatureDao roleFieldFeatureDao;
    @Autowired
    private RoleTableFeatureDao roleTableFeatureDao;



    public List<MTable> listTables(Map<String, Object> params, Integer start, Integer limit) {
        return tableDao.listPage(params,start,limit);
    }

    public Integer listTablesCount(Map<String, Object> params) {
        return tableDao.listPageCount(params);
    }
    public void saveTable(MTable table, List<TableField> fields) {
        table.setAliasName("c_"+table.getName());
        tableDao.dropTable(table);//每次保存现试图删除一次表，这句必须放在事物语句块前先执行，
        fieldDataSourceDao.deleteFieldDataSourceByName(table.getAliasName());

        if(table.getId()==null){
            tableDao.save(table);
        }else{
            tableDao.update(table);
            List<TableField> fieldList=getFieldsByTableId(table.getId());
            for(TableField field:fieldList){
                tableFieldDao.delete(field);
            }
            roleFieldFeatureDao.deleteByTableId(table.getId());
            roleTableFeatureDao.deleteByTableId(table.getId());
        }
        for(TableField field:fields){
            field.setTableId(table.getId());
           tableFieldDao.save(field);
        }
        tableDao.createTable(table,fields);
        FieldDataSource source=new FieldDataSource();
        source.setDisplayName(table.getDisplayName());
        source.setMtableName(table.getAliasName());
        fieldDataSourceDao.save(source);

    }

    public MTable getById(Long id) {
        return tableDao.get(id);
    }

    public List<TableField> getFieldsByTableId(Long tableId){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_tableId_EQ",tableId);
        List<TableField> fieldList=tableFieldDao.listAll(map);
        return fieldList;
    }

    public void deleteTable(MTable table) throws Exception{
        List<TableField> fields=getFieldsByTableId(table.getId());
        for(TableField tableField:fields){
            tableFieldDao.delete(tableField);
        }
        tableDao.delete(table);
        roleFieldFeatureDao.deleteByTableId(table.getId());
        roleTableFeatureDao.deleteByTableId(table.getId());

        tableDao.dropTable(table);
    }

    public List<MTable> listAll() throws Exception {
        return tableDao.listAll();
    }

    public MTable getByName(String name) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_name_EQ",name);
        List<MTable> fieldList=tableDao.listPage(map, 0, 1);
        if(fieldList!=null && fieldList.size()>0){
            return fieldList.get(0);
        }
        return null;
    }

    public List<MTable> listNoMenu() {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_hasMenu_EQ",0);
        return tableDao.listAll(map);
    }

}
