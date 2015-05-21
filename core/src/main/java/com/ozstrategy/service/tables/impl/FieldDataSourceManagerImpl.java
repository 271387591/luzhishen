package com.ozstrategy.service.tables.impl;

import com.ozstrategy.bo.DataSourceFiledBo;
import com.ozstrategy.dao.tables.FieldDataSourceDao;
import com.ozstrategy.model.tables.FieldDataSource;
import com.ozstrategy.service.tables.FieldDataSourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
@Service("fieldDataSourceManager")
public class FieldDataSourceManagerImpl implements FieldDataSourceManager {
    @Autowired
    private FieldDataSourceDao fieldDataSourceDao;
    public List<FieldDataSource> listAll() throws Exception{
        return fieldDataSourceDao.listAll();
    }

    public List<DataSourceFiledBo> getDataSourceFields(FieldDataSource source) {
        return fieldDataSourceDao.getDataSourceFields(source);
    }

    public FieldDataSource getById(Long id)  {
        return fieldDataSourceDao.get(id);
    }

    public List<Map<String, Object>> listDataSourceDatas(FieldDataSource source, Map<String, Object> params, Integer start, Integer limit) {
        return fieldDataSourceDao.listDataSourceDatas(source, params, start, limit);
    }

    public Integer listDataSourceDatasCount(FieldDataSource source, Map<String, Object> params) {
        return fieldDataSourceDao.listDataSourceDatasCount(source,params);
    }

    public List<Map<String, Object>> listDataSourceDatas(FieldDataSource source) {
        return fieldDataSourceDao.listDataSourceDatas(source);
    }

    public List<Map<String, Object>> listDataSourceTreeDatas(FieldDataSource source, String parent, Object parentValue) {
        return fieldDataSourceDao.listDataSourceTreeDatas(source, parent, parentValue);
    }

    public Map<String, Object> getDataByFieldValue(FieldDataSource source, String field, Object fieldValue) {
        return fieldDataSourceDao.getDataByFieldValue(source,field,fieldValue);
    }

}
