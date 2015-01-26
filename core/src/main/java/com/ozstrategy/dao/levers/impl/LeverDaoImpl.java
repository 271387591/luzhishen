package com.ozstrategy.dao.levers.impl;

import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.dao.levers.LeverDao;
import com.ozstrategy.model.levers.Lever;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lihao on 1/23/15.
 */
@Repository("leverDao")
public class LeverDaoImpl extends GenericDaoHibernate<Lever,Long> implements LeverDao {
    public LeverDaoImpl() {
        super(Lever.class);
    }

    public List<Lever> list(String keyword, Integer start, Integer limit) {
        String hql="from Lever where 1=1";
        
        if(StringUtils.isNotEmpty(keyword)){
            keyword="%"+keyword+"%";
            hql+=" and (leverAddress like ?)";
            return getSession().createQuery(hql).setParameter(0,keyword).setMaxResults(limit).setFirstResult(start).list();
        }
        return getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
    }

    public Integer listCount(String keyword) {
        String hql="select count(id) from Lever where 1=1";
        if(StringUtils.isNotEmpty(keyword)){
            keyword="%"+keyword+"%";
            hql+=" and (leverAddress like ?)";
            Object o =  getSession().createQuery(hql).setParameter(0,keyword).setFetchSize(1).uniqueResult();
            if(o!=null){
                return NumberUtils.toInt(o.toString());
            }
        }
        Object o =  getSession().createQuery(hql).setFetchSize(1).uniqueResult();
        if(o!=null){
            return NumberUtils.toInt(o.toString());
        }
        return 0;
    }
}
