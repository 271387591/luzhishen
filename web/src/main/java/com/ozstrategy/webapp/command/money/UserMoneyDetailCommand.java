package com.ozstrategy.webapp.command.money;

import com.ozstrategy.model.money.UserMoneyDetail;
import com.ozstrategy.model.userrole.User;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by lihao1 on 5/30/15.
 */
public class UserMoneyDetailCommand {
    private Long id;
    private Integer type;
    private Double total=new Double(0);
    private Long moneyId;
    private Long dealId;
    private String dealName;
    protected Date createDate;
    protected Date lastUpdateDate;
    private String lastUsername;
    private Double credits;
    private Double price;

    public UserMoneyDetailCommand() {
    }
    public UserMoneyDetailCommand(UserMoneyDetail detail) {
        this.id= detail.getId();
        this.type= detail.getType();
        this.total= detail.getTotal();
        this.moneyId= detail.getMoney().getId();
        this.dealId= detail.getDeal().getId();
        this.dealName= detail.getDeal().getUsername();
        this.createDate= detail.getCreateDate();
        this.lastUpdateDate= detail.getLastUpdateDate();
        this.credits=detail.getCredits();
        this.price=detail.getPrice();
        User last=detail.getLastUpdater();
        if(last!=null){
            this.lastUsername=last.getUsername();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Long moneyId) {
        this.moneyId = moneyId;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
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

    public String getLastUsername() {
        return lastUsername;
    }

    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
