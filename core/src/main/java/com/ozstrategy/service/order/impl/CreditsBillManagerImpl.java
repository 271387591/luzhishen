package com.ozstrategy.service.order.impl;

import com.ozstrategy.alipay.util.AlipayNotify;
import com.ozstrategy.dao.credits.CreditsDetailDao;
import com.ozstrategy.dao.credits.UserCreditsDao;
import com.ozstrategy.dao.money.UserMoneyDao;
import com.ozstrategy.dao.money.UserMoneyDetailDao;
import com.ozstrategy.dao.order.CreditsBillDao;
import com.ozstrategy.dao.order.CreditsOrderDao;
import com.ozstrategy.dao.system.ApplicationConfigDao;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.model.money.UserMoneyDetail;
import com.ozstrategy.model.money.UserMoneyType;
import com.ozstrategy.model.order.CreditsBill;
import com.ozstrategy.model.order.CreditsBillStatus;
import com.ozstrategy.model.order.CreditsOrder;
import com.ozstrategy.model.order.CreditsOrderStatus;
import com.ozstrategy.model.system.ApplicationConfig;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.OrderNoInstance;
import com.ozstrategy.service.order.CreditsBillManager;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lihao1 on 5/26/15.
 */
@Service("creditsBillManager")
public class CreditsBillManagerImpl extends GenericManagerImpl<CreditsBill,Long> implements CreditsBillManager {
    @Autowired
    UserCreditsDao userCreditsDao;
    @Autowired
    CreditsBillDao creditsBillDao;
    @Autowired
    CreditsDetailDao creditsDetailDao;
    @Autowired
    CreditsOrderDao creditsOrderDao;
    @Autowired
    OrderNoInstance orderNoInstance;
    @Autowired
    ApplicationConfigDao applicationConfigDao;
    @Autowired
    UserMoneyDao userMoneyDao;
    @Autowired
    UserMoneyDetailDao userMoneyDetailDao;




    public void createBill(User user,CreditsBill bill) {
        UserCredits userCredits=user.getUserCredits();
        Double total=userCredits.getTotal();
        Double num=bill.getCreditsSum();
        userCredits.setTotal(total-num);
        userCreditsDao.saveOrUpdate(userCredits);
        bill.setUserCredits(userCredits);
        bill.setStatus(CreditsBillStatus.Unlock.ordinal());
        creditsBillDao.saveOrUpdate(bill);
    }



    public void cancelBills(CreditsBill bill) {
        UserCredits userCredits=bill.getUserCredits();
        Double exist=userCredits.getTotal();
        Double billSum=bill.getCreditsSum();
        Double newCredits=exist+billSum;
        userCredits.setTotal(newCredits);
        userCreditsDao.saveOrUpdate(userCredits);
        creditsBillDao.remove(bill);
    }


}
