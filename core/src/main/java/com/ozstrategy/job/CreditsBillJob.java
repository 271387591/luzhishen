package com.ozstrategy.job;

import com.ozstrategy.service.order.CreditsBillManager;
import com.ozstrategy.service.order.CreditsOrderManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lihao1 on 5/28/15.
 */
public class CreditsBillJob {

    @Autowired
    private CreditsOrderManager creditsOrderManager;

    public void cancelOrders(){
        creditsOrderManager.cancelOrders();
    }

}
