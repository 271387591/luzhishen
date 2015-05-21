package com.ozstrategy.webapp.command.menus;

import com.ozstrategy.model.menus.Menu;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.webapp.command.tables.TableFieldCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao1 on 4/29/15.
 */
public class MenuCommand {
    private Long id;
    private String text;
    private Boolean leaf=Boolean.TRUE;
    private String widget;
    private String widgetItemId;
    private Long tableId;
    private List<MenuCommand> children=new ArrayList<MenuCommand>();
    private List<TableFieldCommand> tableFields=new ArrayList<TableFieldCommand>();

    public MenuCommand() {
    }

    public MenuCommand(Menu menu){
        this.id=menu.getId();
        this.text=menu.getName();
        this.widget=menu.getWidget();
        this.tableId=menu.getTableId();
        if(menu.getParentId()!=null){
            this.leaf=Boolean.FALSE;
        }
        this.widgetItemId=this.widget+"_"+this.id;
    }
    public MenuCommand populateFields(List<TableField> fieldList){
        for(TableField field:fieldList){
            this.tableFields.add(new TableFieldCommand(field));
        }
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public List<MenuCommand> getChildren() {
        return children;
    }

    public void setChildren(List<MenuCommand> children) {
        this.children = children;
    }

    public String getWidgetItemId() {
        return widgetItemId;
    }

    public void setWidgetItemId(String widgetItemId) {
        this.widgetItemId = widgetItemId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<TableFieldCommand> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<TableFieldCommand> tableFields) {
        this.tableFields = tableFields;
    }
}
