package com.ozstrategy.dao;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

public interface GenericDao<T, PK extends Serializable> {

    boolean exists(PK id);

    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    T get(PK id);
    
    T load(PK id);

    List<T> getAll();

    List<T> getAllDistinct();

    void remove(PK id);

    T save(T object);

    void saveOrUpdate(T object);

    void remove(T object);

    List<T> listSortPage(Map<String,Object> params,Integer start,Integer limit,Map<String,String> sort);
    List<T> listPage(Map<String,Object> params,Integer start,Integer limit);
    Integer count(Map<String,Object> params);
    List<T> listAll(Map<String,Object> params);
    T getByParams(Map<String,Object> params);
}
