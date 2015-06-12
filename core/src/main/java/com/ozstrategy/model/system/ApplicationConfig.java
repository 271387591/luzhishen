package com.ozstrategy.model.system;

import javax.persistence.*;

/**
 * Created by lihao1 on 5/29/15.
 */
@Entity
public class ApplicationConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String systemKey;
    @Column(columnDefinition = "TEXT")
    private String systemValue;

    public ApplicationConfig() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }
}
