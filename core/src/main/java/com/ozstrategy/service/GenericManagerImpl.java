package com.ozstrategy.service;

import com.ozstrategy.dao.GenericDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Observable;

@Service("genericManager")
public class GenericManagerImpl<T, PK extends Serializable> extends Observable implements GenericManager<T, PK> {
    @Autowired
    protected GenericDao<T, PK> dao;

    protected final Log log = LogFactory.getLog(getClass());

    public GenericManagerImpl() {
    }

    public GenericManagerImpl(GenericDao<T, PK> genericDao) {
        this.dao = genericDao;
    }

    public boolean exists(PK id) {
        return dao.exists(id);
    }

    public T get(PK id) {
        try{
            return dao.get(id);
        }catch (Exception e){
        }
        return null;
    }

    public T load(PK id) {
        return dao.get(id);
    }

    public List<T> getAll() {
        return dao.getAll();
    }

    public void remove(PK id) {
        dao.remove(id);
    }

    public void remove(T object) {
        dao.remove(object);
    }

    public T save(T object) {
        return dao.save(object);
    }

    public void saveOrUpdate(T object) {
        dao.saveOrUpdate(object);
    }

    public List<T> listSortPage(Map<String, Object> params, Integer start, Integer limit, Map<String, String> sort) {
        return dao.listSortPage(params, start, limit, sort);
    }

    public List<T> listPage(Map<String, Object> params, Integer start, Integer limit) {
        return dao.listPage(params,start,limit);
    }

    public Integer count(Map<String, Object> params) {
        return dao.count(params);
    }

    public List<T> listAll(Map<String, Object> params) {
        return dao.listAll(params);
    }

    public T getByParams(Map<String, Object> params) {
        return dao.getByParams(params);
    }
} 
