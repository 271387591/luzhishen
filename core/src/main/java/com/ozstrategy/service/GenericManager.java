package com.ozstrategy.service;

import java.io.Serializable;

import java.util.List;


public interface GenericManager<T, PK extends Serializable> {
    boolean exists(PK id);

    T get(PK id);

    T load(PK id);

    List<T> getAll();

    void remove(PK id);
    
    void remove(T object);

    T save(T object);

    void saveOrUpdate(T object);
} 
