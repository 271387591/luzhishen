package com.ozstrategy.service.money.impl;

import com.ozstrategy.model.money.UserMoneyDetail;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.money.UserMoneyDetailManager;
import org.springframework.stereotype.Service;

/**
 * Created by lihao1 on 5/30/15.
 */
@Service("userMoneyDetailManager")
public class UserMoneyDetailManagerImpl extends GenericManagerImpl<UserMoneyDetail,Long> implements UserMoneyDetailManager {
}
