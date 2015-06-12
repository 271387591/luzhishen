package com.ozstrategy.webapp.command.money;

import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.userrole.User;

import java.util.Date;

/**
 * Created by lihao1 on 5/30/15.
 */
public class UserMoneyCommand {
    private Long id;
    private Double total=new Double(0);
    private Integer lastType;
    protected Date createDate;
    protected Date lastUpdateDate;
    private Long userId;
    private String username;
    private String mobile;

    public UserMoneyCommand() {
    }
    public UserMoneyCommand(UserMoney money) {
        this.id= money.getId();
        this.total= money.getTotal();
        this.lastType= money.getLastType();
        this.createDate= money.getCreateDate();
        this.lastUpdateDate= money.getLastUpdateDate();
        this.userId= money.getUser().getId();
        this.username= money.getUser().getUsername();
        this.mobile= money.getUser().getMobile();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getLastType() {
        return lastType;
    }

    public void setLastType(Integer lastType) {
        this.lastType = lastType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
