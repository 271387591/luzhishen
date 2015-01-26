package com.ozstrategy.service;

import com.ozstrategy.dao.GenericDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
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
        return dao.get(id);
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
} 
