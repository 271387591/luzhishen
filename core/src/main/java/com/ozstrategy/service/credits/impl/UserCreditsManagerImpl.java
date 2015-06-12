package com.ozstrategy.service.credits.impl;

import com.ozstrategy.dao.credits.CreditsDetailDao;
import com.ozstrategy.dao.credits.UserCreditsDao;
import com.ozstrategy.dao.hibernate.GenericDaoHibernate;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.credits.CreditsType;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManager;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.credits.UserCreditsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lihao1 on 5/24/15.
 */
@Service("userCreditsManager")
public class UserCreditsManagerImpl extends GenericManagerImpl<UserCredits,Long> implements UserCreditsManager {
    @Autowired
    UserCreditsDao userCreditsDao;
    @Autowired
    CreditsDetailDao creditsDetailDao;

    public void updateCredits(Long id,Double credits,User deal) {
        Date date=new Date();
        UserCredits userCredits=userCreditsDao.get(id);
        userCredits.setTotal(credits);
        userCredits.setLastType(CreditsType.Admin.ordinal());
        userCredits.setLastUpdateDate(date);
        userCreditsDao.saveOrUpdate(userCredits);
        CreditsDetail detail=new CreditsDetail();


        detail.setCredits(userCredits);
        detail.setTotal(credits);
        detail.setCreator(deal);
        detail.setLastUpdater(deal);
        detail.setDeal(deal);
        detail.setLastUpdateDate(new Date());
        detail.setCreateDate(new Date());
        detail.setType(CreditsType.Admin.ordinal());
        creditsDetailDao.save(detail);
    }

    public void reportPoints(User user, String SSID, String BSSID, String points) {
        UserCredits userCredits=user.getUserCredits();
        Double total=userCredits.getTotal();
        userCredits.setTotal(total+new Double(points));
        userCredits.setLastUpdateDate(new Date());
        userCredits.setLastType(CreditsType.Normal.ordinal());
        userCreditsDao.saveOrUpdate(userCredits);
        CreditsDetail creditsDetail=new CreditsDetail();
        creditsDetail.setCredits(userCredits);
        creditsDetail.setTotal(new Double(points));
        creditsDetail.setLastUpdateDate(new Date());
        creditsDetail.setCreateDate(new Date());
        creditsDetail.setCreator(user);
        creditsDetail.setLastUpdater(user);
        creditsDetail.setType(CreditsType.Normal.ordinal());
        creditsDetail.setBssid(BSSID);
        creditsDetail.setSsid(SSID);
        creditsDetailDao.save(creditsDetail);
    }

    public void givePoints(User user, User target, Double points) {
        UserCredits userCredits=user.getUserCredits();
        Double total=userCredits.getTotal();
        userCredits.setTotal(total-points);
        userCredits.setLastUpdateDate(new Date());
        userCredits.setLastType(CreditsType.Grant.ordinal());
        userCreditsDao.saveOrUpdate(userCredits);

        CreditsDetail creditsDetail=new CreditsDetail();
        creditsDetail.setCredits(userCredits);
        creditsDetail.setTotal(points);
        creditsDetail.setLastUpdateDate(new Date());
        creditsDetail.setCreateDate(new Date());
        creditsDetail.setCreator(user);
        creditsDetail.setLastUpdater(user);
        creditsDetail.setType(CreditsType.Grant.ordinal());
        creditsDetailDao.save(creditsDetail);

        UserCredits targetCredits=target.getUserCredits();
        targetCredits.setTotal(targetCredits.getTotal() + points);
        targetCredits.setLastType(CreditsType.Incept.ordinal());
        targetCredits.setLastUpdateDate(new Date());
        userCreditsDao.saveOrUpdate(targetCredits);

        CreditsDetail targetDetail=new CreditsDetail();
        targetDetail.setCredits(targetCredits);
        targetDetail.setTotal(points);
        targetDetail.setLastUpdateDate(new Date());
        targetDetail.setCreateDate(new Date());
        targetDetail.setCreator(user);
        targetDetail.setLastUpdater(user);
        targetDetail.setType(CreditsType.Incept.ordinal());
        targetDetail.setDeal(user);
        creditsDetailDao.save(targetDetail);

    }
}
