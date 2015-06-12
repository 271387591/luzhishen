package com.ozstrategy.model.money;

import com.ozstrategy.model.CreatorEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Created by lihao1 on 5/30/15.
 */
@Entity
public class UserApply extends CreatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Double total=new Double(0);
    @Column
    @org.hibernate.annotations.Index(columnNames = {"status"},name = "statusIndex")
    private Integer status;
    @Column
    @org.hibernate.annotations.Index(columnNames = {"applyNo"},name = "applyNoIndex")
    private String applyNo;

    @Column
    private Double rmb=new Double(0);

    @ManyToOne
    @JoinColumn(name = "userAliInfoId")
    private UserAliInfo userAliInfo;
    @ManyToOne
    @JoinColumn(name = "rmbId")
    private SystemRmb systemRmb;
    @ManyToOne
    @JoinColumn(name = "userMoneyId")
    private UserMoney userMoney;
    @Column(columnDefinition = "TEXT")
    private String successDetail;
    @Column(columnDefinition = "TEXT")
    private String failDetail;
    @Column(columnDefinition = "TEXT")
    private String cancelDetail;




    public UserApply() {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }


    public UserAliInfo getUserAliInfo() {
        return userAliInfo;
    }

    public void setUserAliInfo(UserAliInfo userAliInfo) {
        this.userAliInfo = userAliInfo;
    }

    public SystemRmb getSystemRmb() {
        return systemRmb;
    }

    public void setSystemRmb(SystemRmb systemRmb) {
        this.systemRmb = systemRmb;
    }

    public Double getRmb() {
        return rmb;
    }

    public void setRmb(Double rmb) {
        this.rmb = rmb;
    }

    public UserMoney getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(UserMoney userMoney) {
        this.userMoney = userMoney;
    }

    public String getSuccessDetail() {
        return successDetail;
    }

    public void setSuccessDetail(String successDetail) {
        this.successDetail = successDetail;
    }

    public String getFailDetail() {
        return failDetail;
    }

    public void setFailDetail(String failDetail) {
        this.failDetail = failDetail;
    }

    public String getCancelDetail() {
        return cancelDetail;
    }

    public void setCancelDetail(String cancelDetail) {
        this.cancelDetail = cancelDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserApply user = (UserApply) o;
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
