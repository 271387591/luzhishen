package com.ozstrategy.model.money;

import com.ozstrategy.model.CreatorEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lihao1 on 5/30/15.
 */
@Entity
public class SystemRmb extends CreatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Double total=new Double(0);
    @Column
    @org.hibernate.annotations.Index(columnNames = {"bachNo"},name = "bachNoIndex")
    private String bachNo;
    @Column
    private Integer status;
    @Column(columnDefinition = "TEXT")
    private String successDetails;
    @Column(columnDefinition = "TEXT")
    private String failDetails;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "systemRmb")
    private Set<UserApply> userApplies=new HashSet<UserApply>();


    public SystemRmb() {
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

    public String getBachNo() {
        return bachNo;
    }

    public void setBachNo(String bachNo) {
        this.bachNo = bachNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuccessDetails() {
        return successDetails;
    }

    public void setSuccessDetails(String successDetails) {
        this.successDetails = successDetails;
    }

    public String getFailDetails() {
        return failDetails;
    }

    public void setFailDetails(String failDetails) {
        this.failDetails = failDetails;
    }

    public Set<UserApply> getUserApplies() {
        return userApplies;
    }

    public void setUserApplies(Set<UserApply> userApplies) {
        this.userApplies = userApplies;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SystemRmb user = (SystemRmb) o;
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
