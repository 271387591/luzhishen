package com.ozstrategy.dao;

import com.ozstrategy.model.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 4/30/15.
 */
public interface BaseDao<T extends BaseEntity> {
    List<T> listAll()  throws RuntimeException;
    List<T> listAll(Map<String,Object> params);
    List<T> listPage(Map<String,Object> params,Integer start,Integer limit);
    Integer listPageCount(Map<String,Object> params);
    T save(T entity);
    void update(T entity);
    void delete(T entity);
    T get(Serializable id);
    void deleteById(Serializable id);
}
