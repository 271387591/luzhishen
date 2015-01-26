package com.ozstrategy.webapp.command.userrole;

import com.ozstrategy.model.userrole.Role;


public class SimpleRoleCommand {
    protected String displayName;

    protected Long id;

    protected String name;

    public SimpleRoleCommand() {
    }


    public SimpleRoleCommand(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.displayName = role.getDisplayName();

    }

    public SimpleRoleCommand(String name, Long id) {
        this.id = id;
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public Role toRole() {
        Role role = new Role();
        role.setId(this.id);
        role.setName(this.name);

        return role;
    }
} 
