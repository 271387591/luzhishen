package com.ozstrategy.webapp.command.credits;

import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.userrole.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lihao1 on 5/24/15.
 */
public class UserCreditsCommand {
    private Long     id;
    private Double total=new Double(0);
    private Integer lastType;
    protected Date createDate;
    protected Date lastUpdateDate;
    private Long userId;
    private String username;
    private String mobile;
    private String lastUsername;
    private Long lastUserId;
    private String exceed;


    public UserCreditsCommand() {
    }
    public UserCreditsCommand(UserCredits credits) {
        this.id=credits.getId();
        this.total= credits.getTotal();
        this.lastType= credits.getLastType();
        this.createDate= credits.getCreateDate();
        this.lastUpdateDate= credits.getLastUpdateDate();
        User user=credits.getUser();
        this.userId=user.getId();
        this.username= user.getUsername();
        this.mobile=user.getMobile();
        this.exceed=this.total<0?"1":"0";
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

    public String getLastUsername() {
        return lastUsername;
    }

    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

    public Long getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(Long lastUserId) {
        this.lastUserId = lastUserId;
    }

    public String getExceed() {
        return exceed;
    }

    public void setExceed(String exceed) {
        this.exceed = exceed;
    }
}
