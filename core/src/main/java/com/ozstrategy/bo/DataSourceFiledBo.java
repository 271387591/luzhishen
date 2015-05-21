package com.ozstrategy.bo;

import java.io.Serializable;

/**
 * Created by lihao1 on 5/1/15.
 */
public class DataSourceFiledBo implements Serializable{
    private String name;
    private int type;

    public DataSourceFiledBo() {
    }

    public DataSourceFiledBo(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
