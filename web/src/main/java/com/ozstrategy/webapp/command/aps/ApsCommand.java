package com.ozstrategy.webapp.command.aps;

import com.ozstrategy.model.aps.Aps;

/**
 * Created by lihao1 on 6/12/15.
 */
public class ApsCommand {
    private Long id;
    private String ssid;
    private String bssid;
    private String pass;

    public ApsCommand() {
    }
    public ApsCommand(Aps aps) {
        this.id=aps.getId();
        this.ssid=aps.getSsid();
        this.bssid= aps.getBssid();
        this.pass=aps.getPass();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
