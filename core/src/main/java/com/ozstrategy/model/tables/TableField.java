package com.ozstrategy.model.tables;

import com.ozstrategy.annotations.Id;
import com.ozstrategy.annotations.Order;
import com.ozstrategy.annotations.Table;
import com.ozstrategy.model.BaseEntity;

import java.io.Serializable;

/**
 * Created by lihao1 on 4/29/15.
 */
@Table(name="t_tablefield")
public class TableField extends BaseEntity implements Serializable{
    @Id
    private Long id;
    private String cid;
    private String name;
    private Boolean availability;
    private String type;
    private Integer length;
    private Boolean isPrimaryKey;
    private Boolean isnull;
    private String differential;
    private Boolean isNow;
    private Integer inputType;
    private Boolean isQuery;
    private Long tableId;
    private Long dataSourceId;
    private String inputValueFiled;
    private String inputDisplayField;
    private String gridHeader;
    @Order(type = "ASC")
    private Integer indexDex;
    private String treeParent;
    private String treeId;
    private Boolean isDept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public Boolean getIsnull() {
        return isnull;
    }

    public void setIsnull(Boolean isnull) {
        this.isnull = isnull;
    }

    public String getDifferential() {
        return differential;
    }

    public void setDifferential(String differential) {
        this.differential = differential;
    }

    public Boolean getIsNow() {
        return isNow;
    }

    public void setIsNow(Boolean isNow) {
        this.isNow = isNow;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getInputValueFiled() {
        return inputValueFiled;
    }

    public void setInputValueFiled(String inputValueFiled) {
        this.inputValueFiled = inputValueFiled;
    }

    public String getInputDisplayField() {
        return inputDisplayField;
    }

    public void setInputDisplayField(String inputDisplayField) {
        this.inputDisplayField = inputDisplayField;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Boolean getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(Boolean isQuery) {
        this.isQuery = isQuery;
    }

    public String getGridHeader() {
        return gridHeader;
    }

    public void setGridHeader(String gridHeader) {
        this.gridHeader = gridHeader;
    }

    public Integer getIndexDex() {
        return indexDex;
    }

    public void setIndexDex(Integer indexDex) {
        this.indexDex = indexDex;
    }

    public String getTreeParent() {
        return treeParent;
    }

    public void setTreeParent(String treeParent) {
        this.treeParent = treeParent;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public Boolean getIsDept() {
        return isDept;
    }

    public void setIsDept(Boolean isDept) {
        this.isDept = isDept;
    }
}
