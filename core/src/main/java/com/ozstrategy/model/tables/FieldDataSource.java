package com.ozstrategy.model.tables;

import com.ozstrategy.annotations.Id;
import com.ozstrategy.annotations.Table;
import com.ozstrategy.model.BaseEntity;

import java.io.Serializable;

/**
 * Created by lihao1 on 4/29/15.
 */
@Table(name="t_fieldfatasource")
public class FieldDataSource extends BaseEntity implements Serializable{
    @Id
    private Long id;
    private String mtableName;
    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMtableName() {
        return mtableName;
    }

    public void setMtableName(String mtableName) {
        this.mtableName = mtableName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

