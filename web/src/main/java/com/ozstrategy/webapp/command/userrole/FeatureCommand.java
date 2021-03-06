package com.ozstrategy.webapp.command.userrole;

import com.ozstrategy.model.userrole.Feature;
import com.ozstrategy.model.userrole.Role;
import com.ozstrategy.model.userrole.RoleFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;


public class FeatureCommand {
    protected static final transient Log log = LogFactory.getLog(
            FeatureCommand.class);

    protected Date createDate;


    protected String description;

    protected String displayName;

    protected String name;

    protected Long id;

    protected Date lastUpdateDate;
    private String criteria;


    public FeatureCommand() {
    }

    public FeatureCommand(RoleFeature roleFeature) {
        this(roleFeature.getFeature(), "");
    }

    public FeatureCommand(Feature feature) {
        id = feature.getId();
        description = feature.getDescription();
        displayName = feature.getDisplayName();
        createDate = feature.getCreateDate();
        lastUpdateDate = feature.getLastUpdateDate();
        this.name = feature.getName();
        this.criteria = feature.getCriteria();
    }

    public FeatureCommand(Feature feature, String criteria) {
        this(feature);
    }

    public static Log getLog() {
        return log;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Feature toFeature() {
        Feature feature = new Feature();
        feature.setId(id);
        feature.setDisplayName(displayName);
        feature.setDescription(description);
        feature.setName(name);

        return feature;
    }

    public RoleFeature toRoleFeature(Role role) {
        Feature feature = toFeature();
        RoleFeature roleFeature = new RoleFeature();
        roleFeature.setFeature(feature);
        roleFeature.setRole(role);

        return roleFeature;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
} 
