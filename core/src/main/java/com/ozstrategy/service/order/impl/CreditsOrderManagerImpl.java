package com.ozstrategy.service.order.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozstrategy.alipay.util.AlipayNotify;
import com.ozstrategy.dao.credits.CreditsDetailDao;
import com.ozstrategy.dao.credits.UserCreditsDao;
import com.ozstrategy.dao.money.UserMoneyDao;
import com.ozstrategy.dao.money.UserMoneyDetailDao;
import com.ozstrategy.dao.order.CreditsBillDao;
import com.ozstrategy.dao.order.CreditsOrderDao;
import com.ozstrategy.dao.system.ApplicationConfigDao;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.model.credits.CreditsType;
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
import com.ozstrategy.service.order.CreditsOrderManager;
import com.ozstrategy.util.Base64Utils;
import com.ozstrategy.util.RSAUtils;
import com.ozstrategy.util.ThreeDESUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lihao1 on 5/29/15.
 */
@Service("creditsOrderManager")
public class CreditsOrderManagerImpl extends GenericManagerImpl<CreditsOrder,Long> implements CreditsOrderManager {
    @Autowired
    CreditsOrderDao creditsOrderDao;
    @Autowired
    OrderNoInstance orderNoInstance;
    @Autowired
    ApplicationConfigDao applicationConfigDao;
    @Autowired
    UserCreditsDao userCreditsDao;
    @Autowired
    CreditsBillDao creditsBillDao;
    @Autowired
    CreditsDetailDao creditsDetailDao;
    @Autowired
    UserMoneyDao userMoneyDao;
    @Autowired
    UserMoneyDetailDao userMoneyDetailDao;



    public String createOrder(User user, CreditsOrder order,CreditsBill bill) {
        Map<String,Object> map=new HashMap<String, Object>();
        Calendar ca=Calendar.getInstance();
        ca.setTime(new Date());
        Integer loseMin=30;
        ApplicationConfig config=applicationConfigDao.getConfig("orderLoseTime");
        if(config!=null){
            loseMin=Integer.parseInt(config.getSystemValue());
        }
        ca.add(Calendar.MINUTE,loseMin);
        Date loseDate = ca.getTime();
        order.setLoseDate(loseDate);
        order.setStatus(CreditsOrderStatus.NoPayment.ordinal());
        Double money=order.getPrice()*order.getCredits();
        order.setMoney(money);
        order.setBill(bill);
        creditsOrderDao.save(order);
        String orderNo=orderNoInstance.orderNo(order);
        order.setOrderNo(orderNo);
        creditsOrderDao.saveOrUpdate(order);
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("Q_creator.id_EQ_L",user.getId());
        bill.setStatus(CreditsBillStatus.Lock.ordinal());
        creditsBillDao.saveOrUpdate(bill);
        try{
            String publicKey="",privateKey="",notify_url="",pid="",secret="";
            config=applicationConfigDao.getConfig("publicKey");
            if(config!=null){
                publicKey=config.getSystemValue();
            }
            config=applicationConfigDao.getConfig("privateKey");
            if(config!=null){
                privateKey=config.getSystemValue();
            }
            config=applicationConfigDao.getConfig("notify_url");
            if(config!=null){
                notify_url=config.getSystemValue();
            }
            config=applicationConfigDao.getConfig("pid");
            if(config!=null){
                pid=config.getSystemValue();
            }
            config=applicationConfigDao.getConfig("secret");
            if(config!=null){
                secret=config.getSystemValue();
            }



            map.put("publicKey",publicKey);
            Map<String,Object> data=new HashMap<String, Object>();
            data.put("id",order.getId());
            data.put("orderNo",order.getOrderNo());
            data.put("money",order.getMoney());
            data.put("notify_url",notify_url);
            data.put("secret",secret);
            data.put("pid",pid);
            String dataStr=new ObjectMapper().writeValueAsString(data);
            byte[] dataStrData = dataStr.getBytes();
            byte[] encodedData = RSAUtils.encryptByPrivateKey(dataStrData, privateKey);
            map.put("bills", Base64Utils.encode(encodedData));

            byte[] retByte=new ObjectMapper().writeValueAsBytes(map);

            String retStr=Base64Utils.encode(ThreeDESUtils.encrypt(retByte));
            return retStr;

        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    public void cancelOrders() {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Q_loseDate_LE_D", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        List<CreditsOrder> orders=creditsOrderDao.listPage(map,0,50);
        if(orders!=null && orders.size()>0){
            for(CreditsOrder order:orders){
                CreditsBill bill=order.getBill();
                if(bill!=null){
                    bill.setStatus(CreditsBillStatus.Unlock.ordinal());
                    creditsBillDao.saveOrUpdate(bill);
                }
                creditsOrderDao.remove(order);
            }
        }
    }

    public List<Map<String, Object>> getOrderChart() {
        return creditsOrderDao.getOrderChart();
    }
    public void mobileNotice(Map<String, Object> requestParams, String out_trade_no, String trade_no, String trade_status) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            try{
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);

            }catch (Exception e){
            }
        }
        if(AlipayNotify.verify(params)) {//验证成功
            Map<String,Object> orderMap=new HashMap<String, Object>();
            orderMap.put("Q_orderNo_EQ_S",out_trade_no);
            CreditsOrder order=creditsOrderDao.getByParams(orderMap);
            if(order!=null && order.getStatus()!=CreditsOrderStatus.Paid.ordinal()){
                order.setTradeNo(trade_no);
                order.setTradeStatus(trade_status);
                order.setStatus(CreditsOrderStatus.Paid.ordinal());
                creditsOrderDao.saveOrUpdate(order);

                UserCredits userCredits=order.getCreator().getUserCredits();
                userCredits.setTotal(order.getCredits()+userCredits.getTotal());
                userCredits.setLastType(CreditsType.Buy.ordinal());
                userCreditsDao.saveOrUpdate(userCredits);

                CreditsBill bill=order.getBill();

                CreditsDetail userCreditsDetail=new CreditsDetail();
                userCreditsDetail.setTotal(order.getCredits());
                userCreditsDetail.setType(CreditsType.Buy.ordinal());
                userCreditsDetail.setCredits(userCredits);
                userCreditsDetail.setCreator(userCredits.getUser());
                userCreditsDetail.setLastUpdater(userCredits.getUser());
                userCreditsDetail.setDeal(bill.getCreator());
                userCreditsDetail.setCreateDate(new Date());
                userCreditsDetail.setLastUpdateDate(new Date());
                creditsDetailDao.saveOrUpdate(userCreditsDetail);



                UserCredits billUserCredits=bill.getUserCredits();
                if(bill.getStatus()==CreditsBillStatus.Lock.ordinal()){
                    Double exist=billUserCredits.getTotal();
                    Double billSum=bill.getCreditsSum();
                    Double newCredits=exist-billSum;
                    billUserCredits.setTotal(newCredits);
                    userCreditsDao.saveOrUpdate(billUserCredits);
                }
                CreditsDetail detail=new CreditsDetail();
                detail.setTotal(bill.getCreditsSum());
                detail.setType(CreditsType.Sale.ordinal());
                detail.setCredits(billUserCredits);
                detail.setCreator(billUserCredits.getUser());
                detail.setLastUpdater(billUserCredits.getUser());
                detail.setDeal(order.getCreator());
                detail.setCreateDate(new Date());
                detail.setLastUpdateDate(new Date());
                creditsDetailDao.saveOrUpdate(detail);


                UserMoney userMoney=bill.getCreator().getUserMoney();
                userMoney.setTotal(order.getMoney());
                userMoney.setLastType(UserMoneyType.Plus.ordinal());
                userMoneyDao.saveOrUpdate(userMoney);
                UserMoneyDetail userMoneyDetail=new UserMoneyDetail();
                userMoneyDetail.setDeal(bill.getCreator());
                userMoneyDetail.setMoney(userMoney);
                userMoneyDetail.setType(UserMoneyType.Plus.ordinal());
                userMoneyDetail.setTotal(order.getMoney());
                userMoneyDetail.setCreator(userMoney.getUser());
                userMoneyDetail.setLastUpdater(userMoney.getUser());
                userMoneyDetail.setCreateDate(new Date());
                userMoneyDetail.setLastUpdateDate(new Date());
                userMoneyDetail.setCredits(order.getCredits());
                userMoneyDetail.setPrice(order.getPrice());
                userMoneyDetailDao.saveOrUpdate(userMoneyDetail);
                creditsBillDao.remove(bill);
            }

        }else{
            Map<String,Object> orderMap=new HashMap<String, Object>();
            orderMap.put("Q_orderNo_EQ_S",out_trade_no);
            CreditsOrder order=creditsOrderDao.getByParams(orderMap);
            order.setTradeNo(trade_no);
            order.setTradeStatus(trade_status);
            order.setStatus(CreditsOrderStatus.Fail.ordinal());
            creditsOrderDao.saveOrUpdate(order);
        }
    }
}
