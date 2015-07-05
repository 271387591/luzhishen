package com.ozstrategy.webapp.command.order;

import com.ozstrategy.model.order.CreditsOrder;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by lihao1 on 5/28/15.
 */
public class CreditsOrderCommand {
    private Long id;
    private String orderNo;
    private Double money;
    private Double credits;
    private Double price;
    private Integer status;
    private Date loseDate;
    private Date createDate;
    private Long createId;
    private String createUsername;
    private Date lastUpdateDate;
    private String tradeStatus;
    private String tradeNo;
    private String dealUsername;

    public CreditsOrderCommand() {
    }
    public CreditsOrderCommand(CreditsOrder order) {
        this.id=order.getId();
        this.orderNo= order.getOrderNo();
        this.money= order.getMoney();
        this.credits=order.getCredits();
        this.price= order.getPrice();
        this.status=order.getStatus();
        this.loseDate=order.getLoseDate();
        this.createDate=order.getCreateDate();
        this.createId=order.getCreator().getId();
        this.createUsername=order.getCreator().getUsername();
        this.lastUpdateDate=order.getLastUpdateDate();
        this.tradeNo=order.getTradeNo();
        this.tradeStatus=order.getTradeStatus();
        this.dealUsername=order.getBill()!=null?order.getBill().getCreator().getUsername():null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getLoseDate() {
        return loseDate;
    }

    public void setLoseDate(Date loseDate) {
        this.loseDate = loseDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getDealUsername() {
        return dealUsername;
    }

    public void setDealUsername(String dealUsername) {
        this.dealUsername = dealUsername;
    }
}
