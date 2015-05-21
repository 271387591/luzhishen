package com.ozstrategy.dao.tables.impl;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.dao.impl.BaseDaoImpl;
import com.ozstrategy.dao.tables.TableFieldDao;
import com.ozstrategy.model.BaseEntity;
import com.ozstrategy.model.tables.MTable;
import com.ozstrategy.model.tables.TableField;
import com.ozstrategy.model.tables.TableFieldTypes;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao1 on 5/1/15.
 */
@Repository("tableFieldDao")
public class TableFieldDaoImpl extends BaseDaoImpl<TableField> implements TableFieldDao {
    public TableFieldDaoImpl() {
        super(TableField.class);
    }

}
