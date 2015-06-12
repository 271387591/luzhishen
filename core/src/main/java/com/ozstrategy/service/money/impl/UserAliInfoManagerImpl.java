package com.ozstrategy.service.money.impl;

import com.ozstrategy.model.money.UserAliInfo;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.money.UserAliInfoManager;
import org.springframework.stereotype.Service;

/**
 * Created by lihao1 on 5/30/15.
 */
@Service("userAliInfoManager")
public class UserAliInfoManagerImpl extends GenericManagerImpl<UserAliInfo,Long> implements UserAliInfoManager {
}
