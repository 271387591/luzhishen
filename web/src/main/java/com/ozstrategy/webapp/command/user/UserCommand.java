package com.ozstrategy.webapp.command.user;

import com.ozstrategy.model.user.User;

/**
 * Created by lihao1 on 5/10/15.
 */
public class UserCommand {
    private Long id;
    private String name;
    private String username;
    private RoleCommand role=new RoleCommand();
    private Long roleId;
    private Long depId;

    public UserCommand() {
    }
    public UserCommand(User user,RoleCommand command) {
        this.id= user.getId();
        this.name= user.getName();
        this.username= user.getUsername();
        this.role=command;
        this.roleId= user.getRoleId();
        this.depId=user.getDepId();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleCommand getRole() {
        return role;
    }

    public void setRole(RoleCommand role) {
        this.role = role;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }
}
