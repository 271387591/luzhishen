package com.ozstrategy.dao.credits.impl;

import com.ozstrategy.dao.credits.CreditsDetailDao;
import com.ozstrategy.dao.credits.UserCreditsDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.credits.UserCredits;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lihao1 on 5/24/15.
 */
@Repository("creditsDetailDao")
public class CreditsDetailDaoImpl extends GenericDaoHibernate<CreditsDetail,Long> implements CreditsDetailDao {
    public CreditsDetailDaoImpl() {
        super(CreditsDetail.class);
    }

    public List<CreditsDetail> listDetails(String username, Long creditsId, Integer type, Integer start, Integer limit) {
        String hql="from CreditsDetail c where credits.id=? ";
        if(1==type || 2==type){
            hql+=" and c.type=? order by createDate DESC";
            return getSession().createQuery(hql).setParameter(0,creditsId).setParameter(1,type).setFirstResult(start).setMaxResults(limit).list();
        }else{
            hql+=" and c.type<>? and c.type<>? order by createDate DESC";
            return getSession().createQuery(hql).setParameter(0,creditsId).setParameter(1,1).setParameter(2,2).setFirstResult(start).setMaxResults(limit).list();
        }
    }

    public Integer listDetailsCount(String username, Long creditsId, Integer type) {
        String hql=" select count(*) from CreditsDetail c where credits.id=? ";
        if(1==type || 2==type){
            hql+=" and c.type=? order by createDate DESC";
            Object o = getSession().createQuery(hql).setParameter(0,creditsId).setParameter(1,type).uniqueResult();
            return NumberUtils.createInteger(o.toString());
        }else{
            hql+=" and c.type<>? and c.type<>? order by createDate DESC";
            Object o= getSession().createQuery(hql).setParameter(0,creditsId).setParameter(1,1).setParameter(2,2).uniqueResult();
            return NumberUtils.createInteger(o.toString());
        }
    }
}
