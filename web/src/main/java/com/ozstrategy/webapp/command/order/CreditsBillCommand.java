package com.ozstrategy.webapp.command.order;

import com.ozstrategy.model.order.CreditsBill;

import java.util.Date;

/**
 * Created by lihao1 on 5/26/15.
 */
public class CreditsBillCommand {
    private Long id;
    private String creator;
    private Long creatorId;
    private Date createDate;
    private Double creditsSum;
    private Double price;
    private Date lastUpdateDate;

    public CreditsBillCommand() {
    }
    public CreditsBillCommand(CreditsBill bill) {
        this.id=bill.getId();
        this.creator=bill.getCreator().getUsername();
        this.creatorId=bill.getCreator().getId();
        this.createDate=bill.getCreateDate();
        this.creditsSum=bill.getCreditsSum();
        this.price= bill.getPrice();
        this.lastUpdateDate=bill.getLastUpdateDate();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getCreditsSum() {
        return creditsSum;
    }

    public void setCreditsSum(Double creditsSum) {
        this.creditsSum = creditsSum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
