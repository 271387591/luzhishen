package com.ozstrategy.service.order;

import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.order.CreditsBill;
import com.ozstrategy.model.order.CreditsOrder;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/29/15.
 */
public interface CreditsOrderManager extends GenericManager<CreditsOrder,Long> {
    String createOrder(User user,CreditsOrder order,CreditsBill bill);
    void cancelOrders();
    List<Map<String,Object>> getOrderChart();
    void mobileNoticeSuccess(Map<String,String> map);
    void mobileNoticeFail(Map<String,String> map);

}
