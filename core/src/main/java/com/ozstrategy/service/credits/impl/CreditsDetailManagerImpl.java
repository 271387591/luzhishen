package com.ozstrategy.service.credits.impl;

import com.ozstrategy.dao.credits.CreditsDetailDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.credits.CreditsDetailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lihao1 on 5/24/15.
 */
@Service("creditsDetailManager")
public class CreditsDetailManagerImpl extends GenericManagerImpl<CreditsDetail,Long> implements CreditsDetailManager {
    @Autowired
    private CreditsDetailDao creditsDetailDao;

    public List<CreditsDetail> listDetails(String username, Long creditsId, Integer type, Integer start, Integer limit) {
        return creditsDetailDao.listDetails(username, creditsId, type, start, limit);
    }

    public Integer listDetailsCount(String username, Long creditsId, Integer type) {
        return creditsDetailDao.listDetailsCount(username, creditsId, type);
    }
}
