package com.ozstrategy.model.money;

import com.ozstrategy.model.CreatorEntity;
import com.ozstrategy.model.userrole.User;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Created by lihao1 on 5/30/15.
 */
@Entity
public class UserMoneyDetail extends CreatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @org.hibernate.annotations.Index(columnNames = {"type"},name = "typeIndex")
    private Integer type;
    @Column
    private Double total=new Double(0);
    @ManyToOne
    @JoinColumn(name = "moneyId")
    private UserMoney money;
    @ManyToOne
    @JoinColumn(name = "dealId")
    private User deal;
    @Column
    private Double credits;
    @Column
    private Double price;

    public UserMoneyDetail() {
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

    public UserMoney getMoney() {
        return money;
    }

    public void setMoney(UserMoney money) {
        this.money = money;
    }

    public User getDeal() {
        return deal;
    }

    public void setDeal(User deal) {
        this.deal = deal;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserMoneyDetail user = (UserMoneyDetail) o;
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
