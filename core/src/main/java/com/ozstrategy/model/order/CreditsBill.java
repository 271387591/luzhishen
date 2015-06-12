package com.ozstrategy.model.order;

import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.credits.UserCredits;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by lihao1 on 5/26/15.
 */
@Entity
public class CreditsBill extends CreatorEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Double creditsSum;
    @Column
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCreditsId")
    private UserCredits userCredits;
    @Column
    private Integer status;

    public CreditsBill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserCredits getUserCredits() {
        return userCredits;
    }

    public void setUserCredits(UserCredits userCredits) {
        this.userCredits = userCredits;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CreditsBill user = (CreditsBill) o;
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
