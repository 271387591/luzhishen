package com.ozstrategy.webapp.command.credits;

import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.userrole.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lihao1 on 5/24/15.
 */
public class CreditsDetailCommand {
    private Long     id;
    private Integer type;
    private Long dealId;
    private String dealName;
    private Long creditsId;
    protected Date createDate;
    protected Date lastUpdateDate;
    private Double gprs;
    private Double wifi;
    private Double total;
    private String ssid;
    private String bssid;
    private Double price;
    public CreditsDetailCommand() {
    }
    public CreditsDetailCommand(CreditsDetail detail) {
        this.id= detail.getId();
        this.type= detail.getType();
        User deal=detail.getDeal();
        if(deal!=null){
            this.dealId= deal.getId();
            this.dealName=deal.getUsername();
        }
        this.createDate=detail.getCreateDate();
        this.lastUpdateDate=detail.getLastUpdateDate();
        this.creditsId=detail.getCredits().getId();
        this.gprs= detail.getGprs();
        this.wifi= detail.getWifi();
        this.ssid= detail.getSsid();
        this.bssid= detail.getBssid();
        this.total=detail.getTotal();
        this.price=detail.getPrice();
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

    public Long getCreditsId() {
        return creditsId;
    }

    public void setCreditsId(Long creditsId) {
        this.creditsId = creditsId;
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

    public Double getGprs() {
        return gprs;
    }

    public void setGprs(Double gprs) {
        this.gprs = gprs;
    }

    public Double getWifi() {
        return wifi;
    }

    public void setWifi(Double wifi) {
        this.wifi = wifi;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
