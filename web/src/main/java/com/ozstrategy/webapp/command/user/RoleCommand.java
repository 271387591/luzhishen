package com.ozstrategy.webapp.command.user;

import com.ozstrategy.model.user.Role;
import com.ozstrategy.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao1 on 5/5/15.
 */
public class RoleCommand {
    private Long id;
    private String name;
    List<RoleFieldFeatureCommand> features=new ArrayList<RoleFieldFeatureCommand>();
    List<RoleTableFeatureCommand> tableFeatures=new ArrayList<RoleTableFeatureCommand>();

    public RoleCommand() {
    }
    public RoleCommand(Role user, List<RoleFieldFeatureCommand> features,List<RoleTableFeatureCommand> tableFeatures) {
        this.id=user.getId();
        this.name=user.getName();
        this.features.clear();
        this.features.addAll(features);
        this.tableFeatures.addAll(tableFeatures);
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

    public List<RoleFieldFeatureCommand> getFeatures() {
        return features;
    }

    public void setFeatures(List<RoleFieldFeatureCommand> features) {
        this.features = features;
    }

    public List<RoleTableFeatureCommand> getTableFeatures() {
        return tableFeatures;
    }

    public void setTableFeatures(List<RoleTableFeatureCommand> tableFeatures) {
        this.tableFeatures = tableFeatures;
    }
}
