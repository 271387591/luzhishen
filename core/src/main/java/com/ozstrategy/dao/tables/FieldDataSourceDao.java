package com.ozstrategy.dao.tables;

import com.ozstrategy.bo.DataSourceFiledBo;
import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.tables.FieldDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
public interface FieldDataSourceDao extends BaseDao<FieldDataSource> {
    List<DataSourceFiledBo> getDataSourceFields(FieldDataSource source);
    List<Map<String,Object>> listDataSourceDatas(FieldDataSource source);
    List<Map<String,Object>> listDataSourceDatas(FieldDataSource source,Map<String,Object> params,Integer start,Integer limit);
    Integer listDataSourceDatasCount(FieldDataSource source,Map<String,Object> params);

    List<Map<String,Object>> listDataSourceTreeDatas(FieldDataSource source,String parent,Object parentValue);
    Map<String,Object> getDataByFieldValue(FieldDataSource source,String field,Object fieldValue);
    void deleteFieldDataSourceByName(String name);
}
