package com.ozstrategy.dao.hibernate;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.orm.HibernateTemplate;
import com.ozstrategy.orm.QuerySearch;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

@Repository("genericDao")
public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    protected final Log log = LogFactory.getLog(getClass());
    private HibernateTemplate hibernateTemplate;
    private Class<T> persistentClass;
    private SessionFactory sessionFactory;

    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = (T) hibernateTemplate.get(this.persistentClass, id);

        return entity != null;
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];

        int index = 0;

        for (String s : queryParams.keySet()) {
            params[index] = s;
            values[index++] = queryParams.get(s);
        }

        return hibernateTemplate.findByNamedQueryAndNamedParam(queryName, params, values);
    }

    public T get(PK id) {
        T entity = (T) hibernateTemplate.get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    public T load(PK id) {
        T entity = (T) hibernateTemplate.load(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return hibernateTemplate.loadAll(this.persistentClass);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());

        return new ArrayList(result);
    }

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void remove(PK id) {
        hibernateTemplate.delete(this.get(id));
    }

    @SuppressWarnings("unchecked")
    public T save(T object) {
        return (T) hibernateTemplate.save(object);
    }

    public void saveOrUpdate(T object) {
        hibernateTemplate.saveOrUpdate(object);
    }

    public void remove(T object) {
        hibernateTemplate.delete(object);
    }

    public List<T> listSortPage(Map<String, Object> params, Integer start, Integer limit, Map<String, String> sort) {
        String hql="from "+persistentClass.getName()+" where 1=1 ";
        params.put("start",start);
        params.put("limit",limit);
        QuerySearch querySearch=new QuerySearch(hql,params);
        if(!sort.isEmpty()){
            for(Iterator<Map.Entry<String,String>> it=sort.entrySet().iterator();it.hasNext();){
                Map.Entry<String,String> entry=it.next();
                String key=entry.getKey();
                String value=entry.getValue();
                querySearch.addSort(key,value);
            }
        }
        return querySearch.pageQuery(getSession()).list();
    }

    public List<T> listPage(Map<String, Object> params,Integer start, Integer limit) {
        String hql="from "+persistentClass.getName()+" where 1=1 ";
        params.put("start",start);
        params.put("limit",limit);
        return new QuerySearch(hql,params).addSort("createDate","DESC").pageQuery(getSession()).list();
    }

    public Integer count(Map<String, Object> params) {
        String hql="select count(*) from "+persistentClass.getName()+" where 1=1 ";
        Object o = new QuerySearch(hql,params).query(getSession()).uniqueResult();
        return NumberUtils.createInteger(o.toString());
    }

    public List<T> listAll(Map<String, Object> params) {
        String hql="from "+persistentClass.getName()+" where 1=1 ";
        return new QuerySearch(hql,params).addSort("createDate","DESC").query(getSession()).list();
    }

    public T getByParams(Map<String, Object> params) {
        params.put("start",0);
        params.put("limit",1);
        String hql="from "+persistentClass.getName()+" where 1=1 ";
        List<T> list= new QuerySearch(hql,params).pageQuery(getSession()).list();
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    protected Session getSession() {
        return this.hibernateTemplate.getSession();
    }
} 
