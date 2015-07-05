package com.ozstrategy.service.money.impl;

import com.ozstrategy.alipay.config.AlipayConfig;
import com.ozstrategy.alipay.util.AlipayNotify;
import com.ozstrategy.dao.money.SystemRmbDao;
import com.ozstrategy.dao.money.UserApplyDao;
import com.ozstrategy.dao.money.UserMoneyDao;
import com.ozstrategy.dao.money.UserMoneyDetailDao;
import com.ozstrategy.dao.system.ApplicationConfigDao;
import com.ozstrategy.model.money.*;
import com.ozstrategy.model.system.ApplicationConfig;
import com.ozstrategy.service.ApplyNoInstance;
import com.ozstrategy.service.BachNoInstance;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.money.UserApplyManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by lihao1 on 5/30/15.
 */
@Service("userApplyManager")
public class UserApplyManagerImpl extends GenericManagerImpl<UserApply,Long> implements UserApplyManager {
    @Autowired
    private ApplicationConfigDao applicationConfigDao;
    @Autowired
    private BachNoInstance bachNoInstance;
    @Autowired
    private SystemRmbDao systemRmbDao;
    @Autowired
    private UserApplyDao userApplyDao;
    @Autowired
    private ApplyNoInstance applyNoInstance;
    @Autowired
    private UserMoneyDao userMoneyDao;
    @Autowired
    private UserMoneyDetailDao userMoneyDetailDao;







    public void bachPayNoticeSuccess(Map<String, String> params) throws Exception {
        String batch_no=ObjectUtils.toString(params.get("batch_no"));
        Map<String,Object> rmbParams=new HashMap<String, Object>();
        rmbParams.put("Q_bachNo_EQ_S",batch_no);
        SystemRmb systemRmb=systemRmbDao.getByParams(rmbParams);
        if(systemRmb!=null && systemRmb.getStatus().intValue()!=SystemRmbStatus.Success.ordinal()){
            String success_details=params.get("success_details");
            systemRmb.setStatus(SystemRmbStatus.Success.ordinal());
            systemRmb.setSuccessDetails(success_details);
            systemRmbDao.saveOrUpdate(systemRmb);
            Set<UserApply> userApplies=systemRmb.getUserApplies();
            if(userApplies!=null && userApplies.size()>0){
                for(UserApply userApply:userApplies){
                    userApply.setStatus(UserApplyStatus.Success.ordinal());
                    userApply.setSuccessDetail(getSuccessDetail(success_details,userApply.getApplyNo()));

                    Double applyTotal=userApply.getTotal();

                    UserMoney userMoney=userApply.getUserMoney();
//                    userMoney.setTotal(userMoney.getTotal()-applyTotal);
//                    userMoneyDao.saveOrUpdate(userMoney);

                    UserMoneyDetail detail=new UserMoneyDetail();
                    detail.setCreateDate(new Date());
                    detail.setLastUpdateDate(new Date());
                    detail.setType(UserMoneyType.Reduce.ordinal());
                    detail.setMoney(userMoney);
                    detail.setTotal(applyTotal);
                    detail.setCreator(userMoney.getUser());
                    detail.setLastUpdater(userMoney.getUser());
                    userMoneyDetailDao.saveOrUpdate(detail);

                }
            }
        }

    }
    private String getSuccessDetail(String success_details,String applyNo){
        String[] success_detailses=success_details.split("|");
        if(success_detailses!=null && success_detailses.length>0){
            for(String success:success_detailses){
                String[] successes=success.split("^");
                for(String suc:successes){
                    if(suc.equals(applyNo)){
                        return success;
                    }
                }
            }

        }
        return null;
    }

    public void bachPayNoticeFail(Map<String, String> params) throws Exception {
        String batch_no=ObjectUtils.toString(params.get("batch_no"));
        Map<String,Object> rmbParams=new HashMap<String, Object>();
        rmbParams.put("Q_bachNo_EQ_S",batch_no);
        SystemRmb systemRmb=systemRmbDao.getByParams(rmbParams);
        if(systemRmb!=null && systemRmb.getStatus().intValue()!=SystemRmbStatus.Fail.ordinal()){
            String fail_details=params.get("fail_details");
            systemRmb.setStatus(SystemRmbStatus.Fail.ordinal());
            systemRmb.setFailDetails(fail_details);
            systemRmb.setLastUpdateDate(new Date());
            systemRmbDao.saveOrUpdate(systemRmb);
            Set<UserApply> userApplies=systemRmb.getUserApplies();
            if(userApplies!=null && userApplies.size()>0){
                for(UserApply userApply:userApplies){
                    userApply.setStatus(UserApplyStatus.Fail.ordinal());
                    userApply.setFailDetail(getSuccessDetail(fail_details, userApply.getApplyNo()));
                    userApplyDao.saveOrUpdate(userApply);
                    Double applyTotal=userApply.getTotal();
                    UserMoney userMoney=userApply.getUserMoney();
                    userMoney.setTotal(applyTotal+userApply.getTotal());
                    userMoneyDao.saveOrUpdate(userMoney);
                }
            }
        }
    }

    public Map<String, String> createBachPayRequest(SystemRmb rmb,List<UserApply> userApplies) {
        systemRmbDao.save(rmb);
        String partner=AlipayConfig.partner;
        String notify_url=applicationConfigDao.getConfig("batch_notify_url").getSystemValue();
        String email=applicationConfigDao.getConfig("seller_email").getSystemValue();
        String account_name=applicationConfigDao.getConfig("account_name").getSystemValue();
        String pay_date= DateFormatUtils.format(new Date(),"yyyyMMdd");
        String batch_no=bachNoInstance.bachNo(rmb);
        rmb.setBachNo(batch_no);
        String batch_num=userApplies.size()+"";
        StringBuilder detail_dataBuilder=new StringBuilder();
        Double batch_fee=new Double(0);

        for(UserApply apply:userApplies){
            StringBuilder builder=new StringBuilder();
            String applyNo=apply.getApplyNo();
            String aliMail=apply.getUserAliInfo().getEmail();
            String name=apply.getUserAliInfo().getName();
            Double applyRmb=apply.getRmb();
            builder.append(applyNo).append("^").append(aliMail).append("^").append(name).append("^").append(applyRmb).append("^").append("\u7533\u8bf7\u63d0\u73b0");
            detail_dataBuilder.append("|");
            detail_dataBuilder.append(builder);
            batch_fee+=applyRmb;
            apply.setSystemRmb(rmb);
            userApplyDao.saveOrUpdate(apply);
        }
        String detail_data=detail_dataBuilder.substring(1);
        DecimalFormat format=new DecimalFormat(".00");
        rmb.setTotal(batch_fee);
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "batch_trans_notify");
        sParaTemp.put("partner", partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("email", email);
        sParaTemp.put("account_name", account_name);
        sParaTemp.put("pay_date", pay_date);
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("batch_fee", format.format(batch_fee));
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
        systemRmbDao.saveOrUpdate(rmb);
        return sParaTemp;
    }

    public UserApply saveUserApply(UserApply apply,UserMoney userMoney) {
        userApplyDao.save(apply);
        String applyNo=applyNoInstance.applyNo(apply);
        apply.setApplyNo(applyNo);
        userApplyDao.saveOrUpdate(apply);

        userMoney.setTotal(userMoney.getTotal()-apply.getTotal());
        userMoneyDao.saveOrUpdate(userMoney);
        return apply;
    }

    public void bachCancel(List<UserApply> userApplies) {
        for(UserApply userApply:userApplies){
            userApply.setStatus(UserApplyStatus.Cancel.ordinal());
            UserMoney userMoney=userApply.getUserMoney();
            userMoney.setTotal(userApply.getTotal()+userMoney.getTotal());
            userMoneyDao.saveOrUpdate(userMoney);
            userApplyDao.saveOrUpdate(userApply);
        }
    }
}
