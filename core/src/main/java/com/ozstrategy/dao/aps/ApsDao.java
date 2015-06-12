package com.ozstrategy.dao.aps;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.model.aps.Aps;

import java.util.List;

/**
 * Created by lihao1 on 5/25/15.
 */
public interface ApsDao extends GenericDao<Aps,Long> {
    List<Aps> getAp(Long user_id, String SSID, String BSSID);
}
