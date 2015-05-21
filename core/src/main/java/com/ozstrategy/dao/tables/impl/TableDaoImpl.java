package com.ozstrategy.dao.tables.impl;

import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.tables.TableDao;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.model.tables.TableFieldTypes;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao1 on 4/30/15.
 */
@Repository("tableDao")
public class TableDaoImpl extends BaseDaoImpl<MTable> implements TableDao {
    public TableDaoImpl() {
        super(MTable.class);
    }

    public void createTable(MTable table, List<TableField> fields){
        String tableName=table.getAliasName();
        String sql="CREATE TABLE "+tableName+"(\n";
        sql+="id bigint(20) NOT NULL AUTO_INCREMENT,\n";
        List<String> fieldSqls=new ArrayList<String>();
        for(TableField field:fields){
            Integer length=field.getLength();
            Boolean isNull=BooleanUtils.toBoolean(field.getIsnull());
            Boolean isUnique=BooleanUtils.toBoolean(field.getIsPrimaryKey());
            String cid=field.getCid();


            String type=field.getType();
            if(StringUtils.equalsIgnoreCase(TableFieldTypes.INT.name(), type)){
                length=10;
            }else if(StringUtils.equalsIgnoreCase(TableFieldTypes.BIGINT.name(),type)){
                length=20;
            }else if(StringUtils.equalsIgnoreCase(TableFieldTypes.BIGINT.name(),type)){
                length=20;
            }
            if(length==null){
                length=255;
            }
            String fieldSql=cid+ " "+field.getType()+"("+length+")"+(isNull?" DEFAULT NULL":" NOT NULL")+(isUnique?" unique":"")+"\n";
            if(StringUtils.equalsIgnoreCase(TableFieldTypes.DATE.name(),type)
                    || StringUtils.equalsIgnoreCase(TableFieldTypes.DATETIME.name(),type)
                    ||StringUtils.equalsIgnoreCase(TableFieldTypes.DOUBLE.name(),type)){
                fieldSql=cid+ " "+field.getType()+(isNull?" DEFAULT NULL":" NOT NULL")+"\n";
            }
            fieldSqls.add(fieldSql);
        }
        sql+=StringUtils.join(fieldSqls.iterator(),",");
        sql+=",PRIMARY KEY (id)\n) ENGINE=InnoDB DEFAULT CHARSET=utf8";
        System.out.println("sql=="+sql);
        jdbcTemplate.update(sql);

    }

    public void dropTable(MTable table) {
        String tableName=table.getAliasName();
        String sql="DROP TABLE IF EXISTS "+tableName;
        jdbcTemplate.update(sql);
    }


}