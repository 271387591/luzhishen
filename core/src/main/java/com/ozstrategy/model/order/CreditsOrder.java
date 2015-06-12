package com.ozstrategy.model.order;

import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.money.UserMoney;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lihao1 on 5/26/15.
 */
@Entity
public class CreditsOrder extends CreatorEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @org.hibernate.annotations.Index(columnNames = {"orderNo"},name = "orderNoIndex")
    private String orderNo;
    @Column
    private Double money;
    @Column
    private Double credits;
    @Column
    private Double price;
    @Column
    private Integer status;
    @Column
    private String tradeStatus;
    @Column
    private String tradeNo;
    @Column(
            name = "loseDate"
    )
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Index(columnNames = {"loseDate"},name = "loseDateIndex")
    private Date loseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId")
    private CreditsBill bill;




    public CreditsOrder() {
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

    public CreditsBill getBill() {
        return bill;
    }

    public void setBill(CreditsBill bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CreditsOrder user = (CreditsOrder) o;
        return new EqualsBuilder()
                .append(id, user.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .hashCode();

    }
}
