package com.ozstrategy.service.aps.impl;

import com.ozstrategy.dao.aps.ApsDao;
import com.ozstrategy.model.aps.Aps;
import com.ozstrategy.service.GenericManager;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.aps.ApsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lihao1 on 5/25/15.
 */
@Service("apsManager")
public class ApsManagerImpl extends GenericManagerImpl<Aps,Long> implements ApsManager {
    @Autowired
    private ApsDao apsDao;
    public List<Aps> getAp(Long user_id, String SSID, String BSSID) {
        return apsDao.getAp(user_id,SSID,BSSID);
    }
}
