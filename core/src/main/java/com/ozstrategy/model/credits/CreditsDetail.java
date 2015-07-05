package com.ozstrategy.model.credits;

import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.userrole.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lihao1 on 5/24/15.
 */
@Entity
public class CreditsDetail extends CreatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    @Column
    @org.hibernate.annotations.Index(columnNames = {"type"},name = "typeIndex")
    private Integer type;
    @Column
    private Double gprs=new Double(0);
    @Column
    private Double wifi=new Double(0);
    @Column
    private Double total=new Double(0);
    @Column
    private String ssid;
    @Column
    private String bssid;
    @OneToOne
    @JoinColumn(name = "dealId")
    private User deal;
    @ManyToOne
    @JoinColumn(name = "creditsId")
    private UserCredits credits;
    @Column
    private Double price;

    public CreditsDetail() {
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

    public User getDeal() {
        return deal;
    }

    public void setDeal(User deal) {
        this.deal = deal;
    }

    public UserCredits getCredits() {
        return credits;
    }

    public void setCredits(UserCredits credits) {
        this.credits = credits;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CreditsDetail user = (CreditsDetail) o;
        return new EqualsBuilder()
                .append(id,user.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .hashCode();

    }
}
