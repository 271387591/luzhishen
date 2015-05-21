package com.ozstrategy.webapp.command.user;

import com.ozstrategy.model.user.RoleTableFeature;

/**
 * Created by lihao1 on 5/10/15.
 */
public class RoleTableFeatureCommand {
    private Long id;
    private Long tableId;
    private Long roleId;
    private Integer queryType;

    public RoleTableFeatureCommand() {
    }
    public RoleTableFeatureCommand(RoleTableFeature feature) {
        this.id= feature.getId();
        this.tableId= feature.getTableId();
        this.roleId= feature.getRoleId();
        this.queryType= feature.getQueryType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}
