package com.ozstrategy.service.tables;

import com.ozstrategy.bo.DataSourceFiledBo;
import com.ozstrategy.model.tables.FieldDataSource;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
public interface FieldDataSourceManager {
    List<FieldDataSource> listAll()throws Exception;
    List<DataSourceFiledBo> getDataSourceFields(FieldDataSource source);
    FieldDataSource getById(Long id) ;
    List<Map<String,Object>> listDataSourceDatas(FieldDataSource source);
    List<Map<String,Object>> listDataSourceDatas(FieldDataSource source,Map<String,Object> params,Integer start,Integer limit);
    Integer listDataSourceDatasCount(FieldDataSource source,Map<String,Object> params);
    List<Map<String,Object>> listDataSourceTreeDatas(FieldDataSource source,String parent,Object parentValue);
    Map<String,Object> getDataByFieldValue(FieldDataSource source,String field,Object fieldValue);
}
