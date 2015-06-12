package com.ozstrategy.webapp.command.money;

import com.ozstrategy.model.money.UserAliInfo;

/**
 * Created by lihao1 on 5/30/15.
 */
public class UserAliInfoCommand {
    private Long id;
    private String email;
    private String name;
    private Long userId;
    private String username;

    public UserAliInfoCommand() {
    }
    public UserAliInfoCommand(UserAliInfo info) {
        this.id= info.getId();
        this.email= info.getEmail();
        this.name= info.getName();
        this.userId=info.getCreator().getId();
        this.username=info.getCreator().getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
