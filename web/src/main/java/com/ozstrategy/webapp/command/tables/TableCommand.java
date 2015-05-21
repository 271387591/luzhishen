package com.ozstrategy.webapp.command.tables;

import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lihao1 on 5/1/15.
 */
public class TableCommand {
    private Long id;
    private String name;
    private String displayName;
    private Boolean hasMenu=Boolean.FALSE;
    private List<TableFieldCommand> fields=new ArrayList<TableFieldCommand>();
    private Date createDate;

    public TableCommand() {
    }
    public TableCommand(MTable table){
        this.id=table.getId();
        this.name= table.getName();
        this.displayName= table.getDisplayName();
        this.hasMenu= table.getHasMenu();
        this.createDate=table.getCreateDate();
    }
    public TableCommand populateFields(List<TableField> fieldList){
        for(TableField field:fieldList){
            this.fields.add(new TableFieldCommand(field));
        }
        return this;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(Boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public List<TableFieldCommand> getFields() {
        return fields;
    }

    public void setFields(List<TableFieldCommand> fields) {
        this.fields = fields;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
