package com.ozstrategy.model.user;

import com.ozstrategy.annotations.Id;
import com.ozstrategy.annotations.Table;
import com.ozstrategy.annotations.Transient;
import com.ozstrategy.model.BaseEntity;

import java.io.Serializable;

/**
 * Created by lihao1 on 5/5/15.
 */
@Table(name = "t_rolefieldfeature")
public class RoleFieldFeature extends BaseEntity implements Serializable {
    @Id
    private Long id;
    private Long roleId;
    private Long fieldId;
    private Long tableId;
    private Boolean canUpdate;
    private Boolean canAdd;
    private Boolean canQuery;
    private Boolean isDept;
    private Boolean isCreator;
    @Transient
    private String fieldCid;
    @Transient
    private String fieldName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(Boolean canAdd) {
        this.canAdd = canAdd;
    }

    public Boolean getCanQuery() {
        return canQuery;
    }

    public void setCanQuery(Boolean canQuery) {
        this.canQuery = canQuery;
    }

    public String getFieldCid() {
        return fieldCid;
    }

    public void setFieldCid(String fieldCid) {
        this.fieldCid = fieldCid;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getIsDept() {
        return isDept;
    }

    public void setIsDept(Boolean isDept) {
        this.isDept = isDept;
    }

    public Boolean getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Boolean isCreator) {
        this.isCreator = isCreator;
    }
}
