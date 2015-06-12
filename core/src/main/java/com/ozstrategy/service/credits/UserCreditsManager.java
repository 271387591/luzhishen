package com.ozstrategy.service.credits;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.model.credits.UserCredits;
import com.ozstrategy.model.userrole.User;
import com.ozstrategy.service.GenericManager;

/**
 * Created by lihao1 on 5/24/15.
 */
public interface UserCreditsManager extends GenericManager<UserCredits,Long> {
    void updateCredits(Long id,Double credits,User deal);
    void reportPoints(User user,String SSID,String BSSID,String points);
    void givePoints(User user,User target,Double points);
}
