package com.ozstrategy.model.tables;

/**
 * Created by lihao1 on 5/1/15.
 */
public enum  TableFieldTypes {
    INT(4),BIGINT(-5),DOUBLE(8),CHAR(1),VARCHAR(12),DATE(91),DATETIME(93);
    private int type;
    private TableFieldTypes(int type) {
        this.type = type;
    }

    public static TableFieldTypes get(int name) {
        TableFieldTypes[] types = TableFieldTypes.values();

        for (TableFieldTypes type : types) {
            if (type.getType()==name) {
                return type;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

}
