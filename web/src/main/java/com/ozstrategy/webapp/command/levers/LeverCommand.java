package com.ozstrategy.webapp.command.levers;

import com.ozstrategy.model.levers.Lever;

import java.util.Date;

/**
 * Created by lihao on 1/23/15.
 */
public class LeverCommand {
    private Long id;
    private String xnName;
    private String xnNo;
    private String xzNo;
    private String police;
    private String policeNo;
    private String village;
    private String villageNo;
    private String leverNo;
    private String longitude;
    private String latitude;
    private String allNo;
    private String leverType;
    private String leverAddress;
    private String leverAddressAlias;
    private Date createDate;
    private Date lastUpdateDate;
    public LeverCommand(Lever lever){
        this.id= lever.getId();
        this.xnName= lever.getXnName();
        this.xnNo= lever.getXnNo();
        this.xzNo= lever.getXzNo();
        this.police= lever.getPolice();
        this.policeNo= lever.getPoliceNo();
        this.village= lever.getVillage();
        this.villageNo= lever.getVillageNo();
        this.leverNo= lever.getLeverNo();
        this.leverType= lever.getLeverType();
        this.longitude= lever.getLongitude();
        this.latitude= lever.getLatitude();
        this.allNo= lever.getAllNo();
        this.leverAddress= lever.getLeverAddress();
        this.leverAddressAlias= lever.getLeverAddressAlias();
        this.createDate= lever.getCreateDate();
        this.lastUpdateDate= lever.getLastUpdateDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXnName() {
        return xnName;
    }

    public void setXnName(String xnName) {
        this.xnName = xnName;
    }

    public String getXnNo() {
        return xnNo;
    }

    public void setXnNo(String xnNo) {
        this.xnNo = xnNo;
    }

    public String getXzNo() {
        return xzNo;
    }

    public void setXzNo(String xzNo) {
        this.xzNo = xzNo;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getVillageNo() {
        return villageNo;
    }

    public void setVillageNo(String villageNo) {
        this.villageNo = villageNo;
    }

    public String getLeverNo() {
        return leverNo;
    }

    public void setLeverNo(String leverNo) {
        this.leverNo = leverNo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAllNo() {
        return allNo;
    }

    public void setAllNo(String allNo) {
        this.allNo = allNo;
    }

    public String getLeverType() {
        return leverType;
    }

    public void setLeverType(String leverType) {
        this.leverType = leverType;
    }

    public String getLeverAddress() {
        return leverAddress;
    }

    public void setLeverAddress(String leverAddress) {
        this.leverAddress = leverAddress;
    }

    public String getLeverAddressAlias() {
        return leverAddressAlias;
    }

    public void setLeverAddressAlias(String leverAddressAlias) {
        this.leverAddressAlias = leverAddressAlias;
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
}
