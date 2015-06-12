package com.ozstrategy.service.money;

import com.ozstrategy.model.money.SystemRmb;
import com.ozstrategy.model.money.UserApply;
import com.ozstrategy.model.money.UserMoney;
import com.ozstrategy.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/30/15.
 */
public interface UserApplyManager extends GenericManager<UserApply,Long> {
    void bachPayNoticeSuccess(Map<String,String> params) throws Exception;
    void bachPayNoticeFail(Map<String,String> params) throws Exception;
    Map<String,String> createBachPayRequest(SystemRmb rmb,List<UserApply> userApplies);
    UserApply saveUserApply(UserApply apply,UserMoney userMoney);
    void bachCancel(List<UserApply> userApplies);
}
