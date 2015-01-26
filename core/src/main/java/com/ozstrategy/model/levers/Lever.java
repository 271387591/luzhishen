package com.ozstrategy.model.levers;

import com.ozstrategy.model.BaseEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by lihao on 1/23/15.
 */
@Entity
public class Lever extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String xnName;
    @Column
    private String xnNo;
    @Column
    private String xzNo;
    @Column
    private String police;
    @Column
    private String policeNo;
    @Column
    private String village;
    @Column
    private String villageNo;
    @Column
    private String leverNo;
    @Column
    private String longitude;
    @Column
    private String latitude;
    @Column
    private String allNo;
    @Column
    private String leverType;
    @Column(length = 2000)
    private String leverAddress;
    @Column(length = 2000)
    private String leverAddressAlias;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Lever user = (Lever) o;
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
