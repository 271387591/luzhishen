package com.ozstrategy.webapp.command.standingbook;

import com.ozstrategy.model.standingbook.StandingBook;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.webapp.command.tables.TableFieldCommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lihao1 on 5/1/15.
 */
public class StandingBookCommand {
    private Long id;
    private String serialNum;
    private String name;
    private Date createDate;
    private String creator;
    private Long tableId;
    private String url;
    private Boolean hasMenu;
    private String tableName;
    private List<TableFieldCommand> fields=new ArrayList<TableFieldCommand>();

    public StandingBookCommand() {
    }

    public StandingBookCommand(StandingBook book){
        this.id= book.getId();
        this.serialNum= book.getSerialNum();
        this.name= book.getName();
        this.createDate= book.getCreateDate();
        this.creator= book.getCreator();
        this.tableId= book.getTableId();
        this.url= book.getUrl();
        this.hasMenu= book.getHasMenu();
    }
    public StandingBookCommand populateFields(List<TableField> fieldList){
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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(Boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableFieldCommand> getFields() {
        return fields;
    }

    public void setFields(List<TableFieldCommand> fields) {
        this.fields = fields;
    }
}
