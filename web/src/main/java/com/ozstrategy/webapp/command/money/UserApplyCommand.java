package com.ozstrategy.webapp.command.money;

import com.ozstrategy.model.money.UserAliInfo;
import com.ozstrategy.model.money.UserApply;
import com.ozstrategy.model.userrole.User;

import java.util.Date;

/**
 * Created by lihao1 on 5/30/15.
 */
public class UserApplyCommand {
    private Long id;
    private Double total=new Double(0);
    protected Date createDate;
    protected Date lastUpdateDate;
    private Long userId;
    private String username;
    private String mobile;
    private String lastUsername;
    private Long lastUserId;
    private String applyNo;
    private Integer status;
    private String email;
    private String name;
    private String batchNo;
    private String successDetail;
    private String failDetail;
    private String cancelDetail;

    public UserApplyCommand() {
    }
    public UserApplyCommand(UserApply apply) {
        this.id= apply.getId();
        this.total= apply.getTotal();
        this.createDate= apply.getCreateDate();
        this.lastUpdateDate= apply.getLastUpdateDate();
        this.userId= apply.getCreator().getId();
        this.username= apply.getCreator().getUsername();
        this.mobile= apply.getCreator().getMobile();
        this.applyNo=apply.getApplyNo();
        this.status= apply.getStatus();
        UserAliInfo info=apply.getUserAliInfo();
        if(info!=null){
            this.email=info.getEmail();
            this.name=info.getName();
        }
        User last=apply.getLastUpdater();
        if(last!=null){
            this.lastUsername=last.getUsername();
            this.lastUserId=last.getId();
        }
        this.batchNo=apply.getSystemRmb().getBachNo();
        this.successDetail=apply.getSuccessDetail();
        this.failDetail= apply.getFailDetail();
        this.cancelDetail=apply.getCancelDetail();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastUsername() {
        return lastUsername;
    }

    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

    public Long getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(Long lastUserId) {
        this.lastUserId = lastUserId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
}
