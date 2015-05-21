package com.ozstrategy.webapp.command.tables;

import com.ozstrategy.bo.DataSourceFiledBo;

import java.sql.Types;

/**
 * Created by lihao1 on 5/1/15.
 */
public class DataSourceFieldCommand {
    private String name;
    private String type;
    private String displayName;
    public DataSourceFieldCommand(DataSourceFiledBo bo){
        this.name=bo.getName();
        this.type=convertType(bo.getType());
    }
    private String convertType(int type){
        switch (type){
            case Types.CHAR:return "char";
            case Types.VARCHAR:return "varchar";
            case Types.INTEGER:return "int";
            case Types.DATE:return "date";
            case Types.TIMESTAMP:return "datetime";
            case Types.DOUBLE:return "double";
            case Types.BIGINT:return "bigint";
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}