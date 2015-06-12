package com.ozstrategy.service.order;

import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.order.CreditsBill;
import com.ozstrategy.model.order.CreditsOrder;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManager;

import java.util.Map;

/**
 * Created by lihao1 on 5/26/15.
 */
public interface CreditsBillManager extends GenericManager<CreditsBill,Long> {
    void createBill(User user,CreditsBill bill);
    void cancelBills(CreditsBill bill);

}
